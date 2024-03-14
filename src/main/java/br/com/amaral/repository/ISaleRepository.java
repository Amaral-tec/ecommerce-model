package br.com.amaral.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import br.com.amaral.model.Sale;

@Transactional
public interface ISaleRepository extends JpaRepository<Sale, Long> {

	@Query(value="SELECT a FROM Sale a WHERE a.id = ?1 AND a.isDeleted = FALSE")
	Sale getActiveSaleById(Long id);
	
	@Query(value="SELECT i.sale FROM ProductSold i WHERE "
			+ " i.sale.isDeleted = false AND i.product.id = ?1")
	List<Sale> findSaleByProduct(Long productId);

	@Query(value="SELECT DISTINCT(i.sale) FROM ProductSold i "
			+ " WHERE i.sale.isDeleted = false AND UPPER(TRIM(i.product.name)) LIKE %?1%")
	List<Sale> findSaleByProductName(String productName);
	
	@Query(value="SELECT DISTINCT(i.sale) FROM ProductSold i "
			+ " WHERE i.sale.isDeleted = false AND i.sale.individual.id = ?1")
	List<Sale> findSaleByPersonId(Long individualId);

	@Query(value="SELECT DISTINCT(i.sale) FROM ProductSold i "
			+ " WHERE i.sale.isDeleted = false AND UPPER(TRIM(i.sale.individual.name)) LIKE %?1%")
	List<Sale> findSaleByPersonName(String individualName);
	
	@Query(value="SELECT DISTINCT(i.sale) FROM ProductSold i "
			+ " WHERE i.sale.isDeleted = false AND UPPER(TRIM(i.sale.individual.cpf)) = ?1")
	List<Sale> findSaleByCpf(String cpf);

	@Modifying(flushAutomatically = true)
	@Query(nativeQuery = true, value = "UPDATE Sales SET tracking_number = ?1 WHERE id = ?2")
	void updateTrackingNumber(String trackingNumber, Long saleId);

	@Modifying(flushAutomatically = true)
	@Query(nativeQuery = true, value = "UPDATE Sales SET label_url = ?1 WHERE id = ?2")
	void updateLabelURL(String labelURL, Long id);
}
