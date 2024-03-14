package br.com.amaral.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import br.com.amaral.model.ProductCategory;

@Transactional
public interface IProductCategoryRepository extends JpaRepository<ProductCategory, Long> {

	@Query(nativeQuery = true, value = "SELECT COUNT(1) > 0 FROM product_categories WHERE UPPER(TRIM(description)) = UPPER(TRIM(?1))")
	public boolean isProductCategoryRegistered(String description);

	@Query("SELECT a FROM ProductCategory a WHERE UPPER(TRIM(a.description)) LIKE %?1%")
	public List<ProductCategory> findCategoryProductByDescription(String description);

}
