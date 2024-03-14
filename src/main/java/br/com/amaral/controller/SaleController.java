package br.com.amaral.controller;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import br.com.amaral.ExceptionProject;
import br.com.amaral.model.ProductSold;
import br.com.amaral.model.Sale;
import br.com.amaral.model.dto.ProductSoldDTO;
import br.com.amaral.model.dto.SaleDTO;
import br.com.amaral.repository.ISaleRepository;
import br.com.amaral.service.SaleService;
import br.com.amaral.service.ServiceSendEmail;

@RestController
public class SaleController {

	@Autowired
	private ISaleRepository saleRepository;

	@Autowired
	private SaleService saleService;

	@Autowired
	private ServiceSendEmail serviceSendEmail;

	@ResponseBody
	@PostMapping(value = "**/create-sale")
	public ResponseEntity<Sale> createSale(@RequestBody @Valid Sale sale) {

		for (int i = 0; i < sale.getProductsSold().size(); i++) {
			sale.getProductsSold().get(i).setSale(sale);
		}

		saleRepository.saveAndFlush(sale);
		issueInvoice(sale);
		sendOrderConfirmation(sale);
		notifySupplierAboutOrder(sale);

		return new ResponseEntity<>(sale, HttpStatus.OK);
	}

	@ResponseBody
	@PutMapping(value = "**/activate-sales-record/{id}")
	public ResponseEntity<String> activateSalesRecord(@PathVariable(value = "id") Long id) throws ExceptionProject {

		existsInDAO(id);

		saleService.activateSalesRecord(id);
		return new ResponseEntity<>("OK: Activation completed successfully.", HttpStatus.OK);
	}

	@ResponseBody
	@DeleteMapping(value = "**/delete-sale-dao/{id}")
	public ResponseEntity<String> deleteSaleDAO(@PathVariable(value = "id") Long id) throws ExceptionProject {

		existsInDAO(id);

		saleService.deleteSaleDAO(id);
		return new ResponseEntity<>("OK: Deletion completed successfully.", HttpStatus.OK);
	}

	@ResponseBody
	@DeleteMapping(value = "**/delete-sale/{id}")
	public ResponseEntity<String> deleteSale(@PathVariable(value = "id") Long id) throws ExceptionProject {

		isActive(id);

		saleService.deleteSale(id);
		return new ResponseEntity<>("OK: Deletion completed successfully.", HttpStatus.OK);
	}

	@ResponseBody
	@GetMapping(value = "**/get-sale/{id}")
	public ResponseEntity<SaleDTO> getSale(@PathVariable("id") Long id) throws ExceptionProject {

		Sale sale = isActive(id);

		SaleDTO saleDTO = new SaleDTO();
		saleDTO.setTotalAmount(sale.getTotalAmount());
		saleDTO.setIndividual(sale.getIndividual());
		saleDTO.setDiscount(sale.getDiscount());
		saleDTO.setId(sale.getId());

		for (ProductSold productSold : sale.getProductsSold()) {

			ProductSoldDTO productDTO = new ProductSoldDTO();
			productDTO.setQuantity(productSold.getQuantity());
			productDTO.setProduct(productSold.getProduct());

			saleDTO.getProductsSoldDTO().add(productDTO);
		}

		return new ResponseEntity<>(saleDTO, HttpStatus.OK);
	}

	private void issueInvoice(Sale sale) {

	}

	@ResponseBody
	@GetMapping(value = "**/dynamic-sales-query/{value}/{queryType}")
	public ResponseEntity<List<SaleDTO>> dynamicSalesQuery(@PathVariable("value") String value,
			@PathVariable("queryType") String queryType) {

		List<Sale> sale = null;

		if (queryType.equalsIgnoreCase("ByProduct")) {
			sale = saleRepository.findSaleByProduct(Long.parseLong(value));
		} else if (queryType.equalsIgnoreCase("ByPorductName")) {
			sale = saleRepository.findSaleByProductName(value.toUpperCase().trim());
		} else if (queryType.equalsIgnoreCase("ByCustomer")) {
			sale = saleRepository.findSaleByPersonId(Long.parseLong(value));
		} else if (queryType.equalsIgnoreCase("ByCustomerName")) {
			sale = saleRepository.findSaleByPersonName(value.toUpperCase().trim());
		} else if (queryType.equalsIgnoreCase("ByCpf")) {
			sale = saleRepository.findSaleByCpf(value.toUpperCase().trim());
		}

		if (sale == null) {
			sale = new ArrayList<>();
		}

		List<SaleDTO> saleDTOList = new ArrayList<>();

		for (Sale vcl : sale) {

			SaleDTO saleDTO = new SaleDTO();
			saleDTO.setTotalAmount(vcl.getTotalAmount());
			saleDTO.setIndividual(vcl.getIndividual());
			saleDTO.setDiscount(vcl.getDiscount());
			saleDTO.setId(vcl.getId());

			for (ProductSold productSold : vcl.getProductsSold()) {

				ProductSoldDTO productDTO = new ProductSoldDTO();
				productDTO.setQuantity(productSold.getQuantity());
				productDTO.setProduct(productSold.getProduct());

				saleDTO.getProductsSoldDTO().add(productDTO);
			}
		}

		return new ResponseEntity<>(saleDTOList, HttpStatus.OK);
	}

	@ResponseBody
	@GetMapping(value = "**/dynamic-sale-by-period-query/{startDate}/{endDate}")
	public ResponseEntity<List<SaleDTO>> dynamicSaleByPeriodQuery(@PathVariable("startDate") String startDate,
			@PathVariable("endDate") String endDate) {

		List<Sale> sale = null;

		saleService.saleByPeriodQuery(startDate, endDate);

		sale = new ArrayList<>();

		List<SaleDTO> saleDTOList = new ArrayList<>();

		for (Sale vcl : sale) {

			SaleDTO saleDTO = new SaleDTO();
			saleDTO.setTotalAmount(vcl.getTotalAmount());
			saleDTO.setIndividual(vcl.getIndividual());
			saleDTO.setDiscount(vcl.getDiscount());
			saleDTO.setId(vcl.getId());

			for (ProductSold productSold : vcl.getProductsSold()) {

				ProductSoldDTO productDTO = new ProductSoldDTO();
				productDTO.setQuantity(productSold.getQuantity());
				productDTO.setProduct(productSold.getProduct());

				saleDTO.getProductsSoldDTO().add(productDTO);
			}
		}

		return new ResponseEntity<>(saleDTOList, HttpStatus.OK);
	}

	private Sale existsInDAO(Long id) throws ExceptionProject {

		Sale sale = saleRepository.findById(id).orElse(null);

		if (sale == null) {
			throw new ExceptionProject("Operation not performed: Not included in the ID database: " + id);
		}

		return sale;
	}

	private Sale isActive(Long id) throws ExceptionProject {

		Sale sale = saleRepository.getActiveSaleById(id);

		if (sale == null) {
			throw new ExceptionProject(
					"Operation not carried out: It is not included in the Active ID database: " + id);
		}

		return sale;
	}

	private void sendOrderConfirmation(Sale sale) {

		StringBuilder message = new StringBuilder();

		message.append("<html><body>");
		message.append("<p>Hello, ").append(sale.getIndividual().getName()).append(",</p>");
		message.append("<p>Thank you for choosing us! Your order has been successfully placed.</p>");
		message.append("<p>Order Details:</p>");
		message.append("<ul>");

		for (ProductSold product : sale.getProductsSold()) {
			message.append("<li>").append(product.getProduct().getName()).append(" - Quantity: ")
					.append(product.getQuantity()).append("</li>");
		}

		message.append("</ul>");
		message.append("<p>Total Amount: $").append(sale.getTotalAmount()).append("</p>");
		message.append("<p>Shipping Address:</p>");
		message.append("<p>").append(sale.getIndividual().shippingAddress()).append("</p>");
		message.append(
				"<p>We will notify you once your order is shipped. If you have any questions, feel free to contact us.</p>");
		message.append("<p>Thank you for shopping with us!</p>");
		message.append("</body></html>");

		try {
			serviceSendEmail.sendEmailHtml("Order Confirmation", message.toString(), sale.getIndividual().getEmail());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void notifySupplierAboutOrder(Sale sale) {

		StringBuilder message = new StringBuilder();

		message.append("<html><body>");
		message.append("<p>Hello, ").append(sale.getLegalEntity().getName()).append(",</p>");
		message.append("<p>New Order Received!</p>");
		message.append("<p>Order Details:</p>");
		message.append("<ul>");

		for (ProductSold product : sale.getProductsSold()) {
			message.append("<li>").append(product.getProduct().getName()).append(" - Quantity: ")
					.append(product.getQuantity()).append("</li>");
		}

		message.append("</ul>");
		message.append("<p>Total Amount: $").append(sale.getTotalAmount()).append("</p>");
		message.append("<p>Customer Information:</p>");
		message.append("<p>Name: ").append(sale.getIndividual().getName()).append("</p>");
		message.append("<p>Email: ").append(sale.getIndividual().getEmail()).append("</p>");
		message.append("<p>Shipping Address:</p>");
		message.append("<p>").append(sale.getIndividual().shippingAddress()).append("</p>");
		message.append("<p>Please fulfill this order as soon as possible. Contact the customer if needed.</p>");
		message.append("<p>Thank you for your prompt attention!</p>");
		message.append("</body></html>");

		try {
			serviceSendEmail.sendEmailHtml("New Order Received", message.toString(), sale.getLegalEntity().getEmail());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
