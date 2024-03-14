package br.com.amaral.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import br.com.amaral.ExceptionProject;
import br.com.amaral.model.PurchaseInvoice;
import br.com.amaral.model.dto.ProductStockAlertReportDTO;
import br.com.amaral.model.dto.ProductPurchaseInvoiceReportDTO;
import br.com.amaral.repository.IPurchaseInvoiceRepository;
import br.com.amaral.service.PurchaseInvoiceService;

@RestController
public class PurchaseInvoiceController {

	@Autowired
	private IPurchaseInvoiceRepository purchaseInvoiceRepository;
	
	@Autowired
	private PurchaseInvoiceService purchaseInvoiceService;
	
	@Autowired
    private LogController<PurchaseInvoice> logController;

	@ResponseBody
	@PostMapping(value = "**/create-purchase-invoice")
	public ResponseEntity<PurchaseInvoice> createPurchaseInvoice(
			@RequestBody @Valid PurchaseInvoice entity) throws ExceptionProject {

		if (entity.getAccountPayable() == null || entity.getAccountPayable().getId() <= 0) {
			throw new ExceptionProject("Account Payable field must be entered.");
		}

		purchaseInvoiceRepository.save(entity);
		logController.logEntity(entity);
		return new ResponseEntity<>(entity, HttpStatus.OK);
	}

	@ResponseBody
	@PostMapping(value = "**/delete-purchase-invoice")
	public ResponseEntity<String> deletePurchaseInvoice(@RequestBody PurchaseInvoice entity) {

		if (!purchaseInvoiceRepository.findById(entity.getId()).isPresent()) {
			return new ResponseEntity<>("Operation not performed: Not included in the ID database: " + entity.getId(), HttpStatus.NOT_FOUND);
		}
		
		entity.setIsDeleted(true);
		purchaseInvoiceRepository.save(entity);
		logController.logEntity(entity);
		return new ResponseEntity<>("OK: Deletion completed successfully.", HttpStatus.OK);
	}
	
	@ResponseBody
	@DeleteMapping(value = "**/delete-purchase-invoice-by-id/{id}")
	public ResponseEntity<String> deletePurchaseInvoiceById(@PathVariable("id") Long id) throws ExceptionProject {

		PurchaseInvoice entity = isExist(id);
		
		entity.setIsDeleted(true);
		purchaseInvoiceRepository.save(entity);
		logController.logEntity(entity);
		return new ResponseEntity<>("OK: Deletion completed successfully.", HttpStatus.OK);
	}

	@ResponseBody
	@GetMapping(value = "**/get-purchase-invoice/{id}")
	public ResponseEntity<PurchaseInvoice> getPurchaseInvoice(@PathVariable("id") Long id)
			throws ExceptionProject {

		PurchaseInvoice entity = isExist(id);

		logController.logEntity(entity);
		return new ResponseEntity<>(entity, HttpStatus.OK);
	}

	@ResponseBody
	@GetMapping(value = "**/find-purchase-invoice-by-person/{individualId}")
	public ResponseEntity<List<PurchaseInvoice>> findPurchaseInvoiceByPerson(
			@PathVariable("individualId") Long individualId) {

		List<PurchaseInvoice> list = purchaseInvoiceRepository.findPurchaseInvoiceByPerson(individualId);

		logController.logEntityList(list);
		return new ResponseEntity<>(list, HttpStatus.OK);
	}

	@ResponseBody
	@GetMapping(value = "**/find-purchase-invoice-by-legal-entity/{legalEntityId}")
	public ResponseEntity<List<PurchaseInvoice>> findPurchaseInvoiceByLegalEntity(
			@PathVariable("legalEntityId") Long legalEntityId) {

		List<PurchaseInvoice> list = purchaseInvoiceRepository.findPurchaseInvoiceByLegalEntity(legalEntityId);

		logController.logEntityList(list);
		return new ResponseEntity<>(list, HttpStatus.OK);
	}

	@ResponseBody
	@GetMapping(value = "**/find-purchase-invoice-by-account-payable/{accountPayableId}")
	public ResponseEntity<List<PurchaseInvoice>> findPurchaseInvoiceByAccountPayable(
			@PathVariable("accountPayableId") Long accountPayableId) {

		List<PurchaseInvoice> list = purchaseInvoiceRepository.findPurchaseInvoiceByAccountPayable(accountPayableId);

		logController.logEntityList(list);
		return new ResponseEntity<>(list, HttpStatus.OK);
	}

	@ResponseBody
	@GetMapping(value = "**/find-purchase-invoice-by-number/{number}")
	public ResponseEntity<List<PurchaseInvoice>> findPurchaseInvoiceByNumber(
			@PathVariable("number") String number) {

		List<PurchaseInvoice> list = purchaseInvoiceRepository.findPurchaseInvoiceByNumber(number);

		logController.logEntityList(list);
		return new ResponseEntity<>(list, HttpStatus.OK);
	}
	
	@ResponseBody
	@PostMapping(value = "**/generate-product-purchase-invoice-report")
	public ResponseEntity<List<ProductPurchaseInvoiceReportDTO>> generateProductPurchaseInvoiceReport
	(@Valid @RequestBody ProductPurchaseInvoiceReportDTO productPurchaseInvoiceReportDTO){
		
		List<ProductPurchaseInvoiceReportDTO> result = purchaseInvoiceService.generateProductPurchaseInvoiceReport(productPurchaseInvoiceReportDTO);
		
		return new ResponseEntity<>(result, HttpStatus.OK);
	}
	
	@ResponseBody
	@PostMapping(value = "**/generate-product-stock-alert-report")
	public ResponseEntity<List<ProductStockAlertReportDTO>> generateProductStockAlertReport
	(@Valid @RequestBody ProductStockAlertReportDTO productStockAlertReportDTO){
		
		List<ProductStockAlertReportDTO> result = purchaseInvoiceService.generateProductStockAlertReport(productStockAlertReportDTO);
		
		return new ResponseEntity<>(result, HttpStatus.OK);
	}
	
	private PurchaseInvoice isExist(Long id) throws ExceptionProject {

		PurchaseInvoice entity = purchaseInvoiceRepository.findById(id).orElse(null);
		if (entity == null) {
			throw new ExceptionProject("Operation not performed: Not included in the ID database: " + id);
		}

		return entity;
	}
}