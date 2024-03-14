package br.com.amaral.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import br.com.amaral.model.PaymentMethod;

@Transactional
public interface IPaymentMethodRepository extends JpaRepository<PaymentMethod, Long> {

	@Query(nativeQuery = true, value = "SELECT COUNT(1) > 0 FROM payment_methods WHERE UPPER(TRIM(description)) = UPPER(TRIM(?1))")
	public boolean isPaymentMethodRegistered(String description);
	
	@Query("SELECT a FROM PaymentMethod a WHERE a.isDeleted = false AND UPPER(TRIM(a.description)) LIKE %?1%")
	public List<PaymentMethod> findPaymentMethodByName(String description);

}
