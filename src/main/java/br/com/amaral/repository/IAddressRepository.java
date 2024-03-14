package br.com.amaral.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import br.com.amaral.model.Address;

@Transactional
public interface IAddressRepository extends JpaRepository<Address, Long>{

}
