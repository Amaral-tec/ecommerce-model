package br.com.amaral.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import br.com.amaral.model.LegalEntity;

@Transactional
public interface ILegalEntityRepository extends JpaRepository<LegalEntity, Long> {
	
	@Query(nativeQuery = true, value = "SELECT COUNT(1) > 0 FROM legal_entities WHERE UPPER(TRIM(cnpj)) = UPPER(TRIM(?1))")
	public boolean isCnpjRegistered(String cnpj);
	
	@Query(value = "SELECT a FROM LegalEntity a WHERE a.cnpj = ?1")
	LegalEntity getLegalEntityByCnpj(String cnpj);

	@Query(value = "SELECT a FROM LegalEntity a WHERE UPPER(TRIM(a.name)) LIKE %?1%")
	public List<LegalEntity> findLegalEntityByName(String name);
	
	

}
