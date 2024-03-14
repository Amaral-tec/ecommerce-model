package br.com.amaral.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import br.com.amaral.model.DiscountCoupon;

@Transactional
public interface IDiscountCouponRepository extends JpaRepository<DiscountCoupon, Long> {
	
	@Query(nativeQuery = true, value = "SELECT COUNT(1) > 0 FROM discount_coupons WHERE UPPER(TRIM(code)) = UPPER(TRIM(?1))")
	public boolean isDiscountCouponRegistered(String code);
	
	@Query(value = "SELECT a FROM DiscountCoupon a WHERE a.isDeleted = false AND a.legalEntity.id = ?1")
	public List<DiscountCoupon> findDiscountCouponByLegalEntity(Long legalEntityId);

	@Query(value = "SELECT a FROM DiscountCoupon a WHERE a.isDeleted = false AND UPPER(TRIM(a.code)) LIKE %?1%")
	public List<DiscountCoupon> findDiscountCouponByCode(String code);

}
