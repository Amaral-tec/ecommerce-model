package br.com.amaral.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import br.com.amaral.model.Product;

@Transactional
public interface IProductRepository extends JpaRepository<Product, Long> {

	@Query(nativeQuery = true, value = "SELECT COUNT(1) > 0 FROM products WHERE UPPER(TRIM(name)) = UPPER(TRIM(?1))")
	public boolean isProductRegistered(String name);

	@Query("SELECT a FROM Product a WHERE UPPER(TRIM(a.name)) LIKE %?1%")
	List<Product> findProductByName(String name);

}
