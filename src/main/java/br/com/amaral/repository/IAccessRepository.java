package br.com.amaral.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import br.com.amaral.model.Access;

@Transactional
public interface IAccessRepository extends JpaRepository<Access, Long> {

	@Query("SELECT a FROM Access a WHERE a.isDeleted = false AND UPPER(TRIM(a.description)) LIKE %?1%")
	List<Access> findAccessByName(String description);
}
