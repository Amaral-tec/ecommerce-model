package br.com.amaral.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import br.com.amaral.model.AccountReceivable;

@Transactional
public interface IAccountReceivableRepository extends JpaRepository<AccountReceivable, Long> {

	@Query("SELECT a FROM AccountReceivable a WHERE UPPER(TRIM(a.description)) LIKE %?1%")
	List<AccountReceivable> findAccountReceivableByDescription(String description);

	@Query("SELECT a FROM AccountReceivable a WHERE a.individual.id = ?1")
	List<AccountReceivable> findAccountReceivableByPerson(Long individualId);

	@Query("SELECT a FROM AccountReceivable a WHERE a.legalEntity.id = ?1")
	List<AccountReceivable> findAccountReceivableByLegalEntity(Long legalEntityId);

}
