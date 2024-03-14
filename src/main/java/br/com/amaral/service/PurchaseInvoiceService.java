package br.com.amaral.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import br.com.amaral.model.dto.ProductStockAlertReportDTO;
import br.com.amaral.model.dto.ProductPurchaseInvoiceReportDTO;

@Service
public class PurchaseInvoiceService {

	@Autowired
	private JdbcTemplate jdbcTemplate;

	/**
	 * List the products purchased for resell and the invoices referred to
	 * @param ProductPurchaseInvoiceReportDTO
	 * @param startDate and endDate are mandatory
	 * @return List<ProductPurchaseInvoiceReportDTO>
	 */
	public List<ProductPurchaseInvoiceReportDTO> generateProductPurchaseInvoiceReport(
			ProductPurchaseInvoiceReportDTO productPurchaseInvoiceReportDTO) {

		List<ProductPurchaseInvoiceReportDTO> result = new ArrayList<>();

		String sql = "SELECT p.id AS productId, p.name AS productName, p.purchase_price AS purchasePrice, "
				+ " ip.quantity AS quantityPurchased, l.id AS supplierId, l.name AS supplierName, "
				+ " pi.invoice_date AS invoiceDate " 
				+ " FROM purchase_invoices AS pi "
				+ " INNER JOIN invoiced_products AS ip ON  pi.id = purchase_invoice_id "
				+ " INNER JOIN products AS p ON p.id = ip.product_id "
				+ " INNER JOIN legal_entities AS l ON l.id = pi.legal_entity_id WHERE ";

		sql += " pi.invoice_date >='" + productPurchaseInvoiceReportDTO.getStartDate() + "' AND ";
		sql += " pi.invoice_date <= '" + productPurchaseInvoiceReportDTO.getEndDate() + "' ";

		if (!productPurchaseInvoiceReportDTO.getPurchaseInvoideId().isEmpty()) {
			sql += " AND pi.id = " + productPurchaseInvoiceReportDTO.getPurchaseInvoideId() + " ";
		}

		if (!productPurchaseInvoiceReportDTO.getProductId().isEmpty()) {
			sql += " AND p.id = " + productPurchaseInvoiceReportDTO.getProductId() + " ";
		}

		if (!productPurchaseInvoiceReportDTO.getProductName().isEmpty()) {
			sql += "AND p.name LIKE ('%" + productPurchaseInvoiceReportDTO.getProductName() + "')";
		}

		if (!productPurchaseInvoiceReportDTO.getSupplierName().isEmpty()) {
			sql += "AND UPPER(pj.nome) LIKE UPPER('%" + productPurchaseInvoiceReportDTO.getSupplierName()
					+ "')";
		}

		result = jdbcTemplate.query(sql, new BeanPropertyRowMapper<ProductPurchaseInvoiceReportDTO>(
				ProductPurchaseInvoiceReportDTO.class));

		return result;
	}
	
	/**
	 * Lists products that are in stock less than/equal to the quantity defined in the lowStockAlert field.
	 * @param ProductStockAlertReportDTO
	 * @param startDate and endDate are mandatory
	 * @return  List<ProductStockAlertReportDTO>
	 */
	public List<ProductStockAlertReportDTO> 
					generateProductStockAlertReport(ProductStockAlertReportDTO productStockAlertReportDTO ){
		
		List<ProductStockAlertReportDTO> result = new ArrayList<>();

		String sql = "SELECT p.id AS productId, p.name AS productName, "
				+ " p.purchase_price AS purchasePrice, ip.quantity AS quantityPurchased, "
				+ " l.id AS supplierId, l.name AS supplierName, pi.invoice_date AS invoiceDate, "
				+ " p.quantity, p.low_stock_alert AS lowStockAlert "
				+ " FROM purchase_invoices AS pi "
				+ " INNER JOIN invoiced_products AS ip ON  pi.id = purchase_invoice_id "
				+ " INNER JOIN products AS p ON p.id = ip.product_id "
				+ " INNER JOIN legal_entities AS l ON l.id = pi.legal_entity_id WHERE ";
		
		sql += " pi.invoice_date >='" + productStockAlertReportDTO.getStartDate() + "' AND ";
		sql += " pi.invoice_date <= '" + productStockAlertReportDTO.getEndDate() + "' ";
		sql += " AND p.is_alerted = true AND p.quantity <= p.low_stock_alert "; 
		
		if (!productStockAlertReportDTO.getPurchaseInvoideId().isEmpty()) {
		 sql += " AND pi.id = " + productStockAlertReportDTO.getPurchaseInvoideId() + " ";
		}
		
		if (!productStockAlertReportDTO.getProductId().isEmpty()) {
			sql += " AND p.id = " + productStockAlertReportDTO.getProductId() + " ";
		}
		
		if (!productStockAlertReportDTO.getProductName().isEmpty()) {
			sql += "AND  UPPER(p.name) LIKE UPPER('%"+productStockAlertReportDTO.getProductName()+"')";
		}
		
		if (!productStockAlertReportDTO.getSupplierName().isEmpty()) {
			sql += "AND UPPER(l.name) LIKE UPPER('%"+productStockAlertReportDTO.getSupplierName()+"')";
		}
		
		result = jdbcTemplate.query(sql, new BeanPropertyRowMapper<ProductStockAlertReportDTO>(
				ProductStockAlertReportDTO.class));

		return result;
	}
}
