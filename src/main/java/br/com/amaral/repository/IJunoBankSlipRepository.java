package br.com.amaral.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import br.com.amaral.model.BankSlip;

@Transactional
public interface IJunoBankSlipRepository extends JpaRepository<BankSlip, Long> {

}
