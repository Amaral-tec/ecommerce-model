package br.com.amaral.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import br.com.amaral.model.User;

@Transactional
public interface IUserRepository extends JpaRepository<User, Long> {
	
	@Query(nativeQuery = true, value = "SELECT COUNT(1) > 0 FROM users WHERE UPPER(TRIM(login)) = UPPER(TRIM(?1))")
	public boolean isUserRegistered(String login);
	
	@Query(value = "SELECT a FROM User a WHERE a.login = ?1")
	User getUserByLogin(String login);
	
	@Query(value = "SELECT a FROM User a WHERE a.individual.id = ?1 OR a.login =?2")
	User findUserByPerson(Long id, String email);
	
	@Query(value = "SELECT a FROM User a WHERE a.passwordCreatedAt <= current_date - 90")
	List<User> userPasswordExpired();

	@Query(nativeQuery = true, value = "SELECT constraint_name FROM information_schema.constraint_column_usage WHERE table_name = 'users_accesses' AND column_name = 'access_id' AND constraint_name <> 'unique_access_user';")
	String getConstraintAccess();

	@Transactional
	@Modifying
	@Query(nativeQuery = true, value = "INSERT INTO users_accesses(user_id, access_id) VALUES (?1, (SELECT id FROM accesses WHERE description = ?2 LIMIT 1))")
	void insertUserAccess(Long userId, String access);
}