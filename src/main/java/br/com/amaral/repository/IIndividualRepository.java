package br.com.amaral.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import br.com.amaral.model.Individual;

@Transactional
public interface IIndividualRepository extends JpaRepository<Individual, Long> {
	
	@Query(nativeQuery = true, value = "SELECT COUNT(1) > 0 FROM individual WHERE UPPER(TRIM(cpf)) = UPPER(TRIM(?1))")
	public boolean isCpfRegistered(String cpf);
	
	@Query(value = "SELECT a FROM Individual a WHERE a.cpf = ?1")
	Individual getIndividualByCpf(String cpf);

	@Query(value = "SELECT a FROM Individual a WHERE UPPER(TRIM(a.name)) LIKE %?1%")
	public List<Individual> findIndividualByName(String name);

}
