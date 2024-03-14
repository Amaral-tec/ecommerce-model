package br.com.amaral.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import br.com.amaral.model.ProductReview;

@Transactional
public interface IProductReviewRepository extends JpaRepository<ProductReview, Long> {
	
	@Query(value = "SELECT a FROM ProductReview a WHERE a.product.id = ?1")
	List<ProductReview> findProductReviewByProduct(Long productId);
	
	@Query(value = "SELECT a FROM ProductReview a WHERE a.individual.id = ?1")
	List<ProductReview> findProductReviewByPerson(Long individualId);
	
	@Query(value = "SELECT a FROM ProductReview a WHERE a.product.id = ?1 AND a.individual.id = ?2")
	List<ProductReview> findProductReviewByProductPerson(Long productId, Long individualId);
}
