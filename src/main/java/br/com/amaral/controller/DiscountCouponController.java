package br.com.amaral.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import br.com.amaral.ExceptionProject;
import br.com.amaral.model.DiscountCoupon;
import br.com.amaral.repository.IDiscountCouponRepository;

@RestController
public class DiscountCouponController {

	@Autowired
	private IDiscountCouponRepository discountCouponRepository;
	
	@Autowired
    private LogController<DiscountCoupon> logController;

	@ResponseBody
	@PostMapping(value = "**/create-discount-coupon")
	public ResponseEntity<DiscountCoupon> createDiscountCoupon(@RequestBody @Valid DiscountCoupon entity)
			throws ExceptionProject {

		boolean result = discountCouponRepository.isDiscountCouponRegistered(entity.getCode());
		if (result) {
			throw new ExceptionProject(
					"Operation not carried out: Already registered with the name " + entity.getCode());
		}

		discountCouponRepository.saveAndFlush(entity);
		logController.logEntity(entity);
		return new ResponseEntity<>(entity, HttpStatus.OK);
	}
	
	@ResponseBody
	@PostMapping(value = "**/delete-discount-coupon")
	public ResponseEntity<String> deleteDiscountCoupon(@RequestBody DiscountCoupon entity) {

		if (!discountCouponRepository.findById(entity.getId()).isPresent()) {
			return new ResponseEntity<>("Operation not performed: Not included in the ID database: " + entity.getId(), HttpStatus.NOT_FOUND);
		}
		
		entity.setIsDeleted(true);
		discountCouponRepository.save(entity);
		logController.logEntity(entity);
		return new ResponseEntity<>("OK: Deletion completed successfully.", HttpStatus.OK);
	}

	@ResponseBody
	@DeleteMapping(value = "**/delete-discount-coupon-by-id/{id}")
	public ResponseEntity<String> deleteDiscountCouponById(@PathVariable("id") Long id) throws ExceptionProject {
		
		DiscountCoupon entity = isExist(id);
		
		entity.setIsDeleted(true);
		discountCouponRepository.save(entity);
		logController.logEntity(entity);
		return new ResponseEntity<>("OK: Deletion completed successfully.", HttpStatus.OK);
	}

	@ResponseBody
	@GetMapping(value = "**/get-discount-coupon/{id}")
	public ResponseEntity<DiscountCoupon> getDiscountCoupon(@PathVariable("id") Long id) throws ExceptionProject {

		DiscountCoupon entity = isExist(id);

		logController.logEntity(entity);
		return new ResponseEntity<>(entity, HttpStatus.OK);
	}
	
	@ResponseBody
	@GetMapping(value = "**/find-all-discount-coupon")
	public ResponseEntity<List<DiscountCoupon>> findAllDiscountCoupon() {

		List<DiscountCoupon> list = discountCouponRepository.findAll();

		logController.logEntityList(list);
		return new ResponseEntity<>(list, HttpStatus.OK);
	}

	@ResponseBody
	@GetMapping(value = "**/find-discount-coupon-by-name/{code}")
	public ResponseEntity<List<DiscountCoupon>> findDiscountCouponByName(@PathVariable("code") String code) {

		List<DiscountCoupon> list = discountCouponRepository.findDiscountCouponByCode(code.toUpperCase());

		logController.logEntityList(list);
		return new ResponseEntity<>(list, HttpStatus.OK);
	}

	private DiscountCoupon isExist(Long id) throws ExceptionProject {

		DiscountCoupon entity = discountCouponRepository.findById(id).orElse(null);
		if (entity == null) {
			throw new ExceptionProject("Operation not performed: Not included in the ID database: " + id);
		}

		return entity;
	}
}
