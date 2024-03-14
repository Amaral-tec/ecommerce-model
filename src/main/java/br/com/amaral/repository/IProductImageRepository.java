package br.com.amaral.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import br.com.amaral.model.ProductImage;

@Transactional
public interface IProductImageRepository extends JpaRepository<ProductImage, Long> {
	
	@Transactional
	@Modifying(flushAutomatically = true)
	@Query(nativeQuery = true, value = "UPDATE FROM product_images SET is_deleted = True WHERE product_id = ?1")
	void deleteProductImage(Long productId);
	
	@Query("SELECT a FROM ProductImage a WHERE a.product.id = ?1")
	List<ProductImage> findProductImageByProduct(Long productId);

}
