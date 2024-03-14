package br.com.amaral.service;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import br.com.amaral.model.Sale;

@Service
public class SaleService {
	
	@PersistenceContext
	private EntityManager entityManager;
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	
	public void activateSalesRecord(Long id) {
		String value = " BEGIN;"
				+ " UPDATE sales_invoice SET is_deleted = false WHERE sale_id = " + id + "; "
				+ " UPDATE product_sold SET is_deleted = false WHERE sale_id = " + id + "; "
				+ " UPDATE sales SET is_deleted = false WHERE id = " + id + "; " 
				+ " COMMIT; ";

		jdbcTemplate.execute(value);
	}
	
	public void deleteSaleDAO(Long id) {

		String value = " BEGIN;"
				+ " UPDATE sales_invoice SET sale_id = NULL WHERE sale_id =" + id + "; "
				+ " DELETE FROM sales_invoice WHERE sale_id = " + id + "; "
				+ " DELETE FROM product_sold WHERE sale_id = " + id + "; "
				+ " DELETE FROM sales WHERE id = " + id + "; " 
				+ " COMMIT; ";

		jdbcTemplate.execute(value);
	}

	public void deleteSale(Long id) {

		String value = " BEGIN;"
				+ " UPDATE sales_invoice SET is_deleted = true WHERE sale_id = " + id + "; "
				+ " UPDATE product_sold SET is_deleted = true WHERE sale_id = " + id + "; "
				+ " UPDATE sales SET is_deleted = true WHERE id = " + id + "; " 
				+ " COMMIT; ";

		jdbcTemplate.execute(value);
	}
	
	@SuppressWarnings("unchecked")
	public List<Sale> saleByPeriodQuery(String startDate, String endDate) {
		
		String sql = "SELECT DISTINCT(i.sale) FROM product_sold i "
				+ " WHERE i.sale.is_deleted = false "
				+ " AND i.sale.saleDate >= '" + startDate + "'"
				+ " AND i.sale.saleDate <= '" + endDate + "'";
		
		return entityManager.createQuery(sql).getResultList();
		
	}
}
