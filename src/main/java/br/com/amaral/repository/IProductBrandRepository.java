package br.com.amaral.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import br.com.amaral.model.ProductBrand;

@Transactional
public interface IProductBrandRepository extends JpaRepository<ProductBrand, Long> {

	@Query(nativeQuery = true, value = "SELECT COUNT(1) > 0 FROM product_brands WHERE UPPER(TRIM(description)) = UPPER(TRIM(?1))")
	public boolean isProductBrandRegistered(String description);
	
	@Query("SELECT a FROM ProductBrand a WHERE a.isDeleted = false AND UPPER(TRIM(a.description)) LIKE %?1%")
	List<ProductBrand> findProductBrandByName(String description);
}
