package br.com.amaral.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import br.com.amaral.model.InvoiceProduct;

@Transactional
public interface IInvoiceProductRepository extends JpaRepository<InvoiceProduct, Long> {
	
	@Transactional
	@Modifying
	@Query(nativeQuery = true, value = "DELETE FROM invoiced_products where id = ?1")
	void deleteInvoiceProductById(Long id);
	
	@Query("SELECT a FROM InvoiceProduct a WHERE a.product.id = ?1")
	List<InvoiceProduct> findInvoiceProductByPorduct(Long productId);
	
	@Query("SELECT a FROM InvoiceProduct a WHERE a.purchaseInvoice.id = ?1")
	List<InvoiceProduct> findInvoiceProductByPurchaseInvoice(Long idNota);
	
	@Query("SELECT a FROM InvoiceProduct a WHERE a.legalEntity.id = ?1")
	List<InvoiceProduct> findInvoiceProductByLegalEntity(Long legalEntityId);
	
	@Query("SELECT a FROM InvoiceProduct a WHERE a.purchaseInvoice.id = ?1 AND a.product.id = ?2")
	List<InvoiceProduct> findInvoiceProductByPurchaseInvoiceProduct(Long purchaseInvoiceId, Long productId);

}
