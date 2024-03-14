package br.com.amaral.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import br.com.amaral.model.AccountPayable;

@Transactional
public interface IAccountPayableRepository extends JpaRepository<AccountPayable, Long> {

	@Query("SELECT a FROM AccountPayable a WHERE UPPER(TRIM(a.description)) LIKE %?1%")
	List<AccountPayable> findAccountPayableByDescription(String description);

	@Query("SELECT a FROM AccountPayable a WHERE a.individual.id = ?1")
	List<AccountPayable> findAccountPayableByPerson(Long individualId);

	@Query("SELECT a FROM AccountPayable a WHERE a.supplier.id = ?1")
	List<AccountPayable> findAccountPayableBySupplier(Long supplierId);

	@Query("SELECT a FROM AccountPayable a WHERE a.legalEntity.id = ?1")
	List<AccountPayable> findAccountPayableByLegalEntity(Long legalEntityId);

}
