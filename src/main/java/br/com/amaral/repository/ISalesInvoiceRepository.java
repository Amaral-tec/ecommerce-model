package br.com.amaral.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import br.com.amaral.model.SalesInvoice;

@Transactional
public interface ISalesInvoiceRepository extends JpaRepository<SalesInvoice, Long> {
	
	@Query(value = "SELECT a FROM SalesInvoice a WHERE a.sale.id = ?1")
	SalesInvoice getSalesInvoicebySale(Long saleId);
	
	@Query(value = "SELECT a FROM SalesInvoice a WHERE a.sale.id = ?1")
	List<SalesInvoice> findSalesInvoicebySale(Long saleId);
}
