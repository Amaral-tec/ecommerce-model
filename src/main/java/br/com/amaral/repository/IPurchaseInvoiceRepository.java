package br.com.amaral.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import br.com.amaral.model.PurchaseInvoice;

@Transactional
public interface IPurchaseInvoiceRepository extends JpaRepository<PurchaseInvoice, Long> {
	
	@Transactional
	@Modifying(flushAutomatically = true, clearAutomatically = true)
	@Query(nativeQuery = true, value = "DELETE FROM invoiced_products WHERE purchase_invoice_id = ?1")
	void deleteInvoiceProduct(Long purchaseInvoiceId);
	
	@Query("SELECT a FROM PurchaseInvoice a WHERE a.individual.id = ?1")
	List<PurchaseInvoice> findPurchaseInvoiceByPerson(Long individualId);
	
	@Query("SELECT a FROM PurchaseInvoice a WHERE a.legalEntity.id = ?1")
	List<PurchaseInvoice> findPurchaseInvoiceByLegalEntity(Long legalEntityId);
	
	@Query("SELECT a FROM PurchaseInvoice a WHERE a.accountPayable.id = ?1")
	List<PurchaseInvoice> findPurchaseInvoiceByAccountPayable(Long accountPayableId);
	
	@Query("SELECT a FROM PurchaseInvoice a WHERE UPPER(TRIM(a.number)) LIKE %?1%")
	List<PurchaseInvoice> findPurchaseInvoiceByNumber(String number);

}
