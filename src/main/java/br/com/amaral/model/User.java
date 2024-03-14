package br.com.amaral.model;

import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.ConstraintMode;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

@Entity
@Table(name = "users")
@SequenceGenerator(name = "seq_user", sequenceName = "seq_user", allocationSize = 1, initialValue = 1)
public class User implements UserDetails {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_user")
	private Long id;

	@NotBlank
	@Column(nullable = false)
	private String login;

	@NotBlank
	@Column(nullable = false)
	private String password;

	@NotNull
	@Column(name = "password_created_at", nullable = false)
	@Temporal(TemporalType.DATE)
	private Date passwordCreatedAt;

	@Column(name = "is_deleted")
	private Boolean isDeleted = Boolean.FALSE;

	@ManyToOne(targetEntity = Individual.class)
	@JoinColumn(name = "individual_id", nullable = false, 
	foreignKey = @ForeignKey(value = ConstraintMode.CONSTRAINT, name = "individual_fk"))
	private Individual individual;

	@OneToMany(fetch = FetchType.EAGER)
	@JoinTable(name = "users_accesses", 
		uniqueConstraints = @UniqueConstraint(columnNames = { "user_id", "access_id" }, name = "unique_access_user"), 
		joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id", 
		table = "user", unique = false, 
		foreignKey = @ForeignKey(name = "user_fk", value = ConstraintMode.CONSTRAINT)), 
		inverseJoinColumns = @JoinColumn(name = "access_id", unique = false, referencedColumnName = "id", 
		table = "access", foreignKey = @ForeignKey(name = "access_fk", value = ConstraintMode.CONSTRAINT)))
	private List<Access> accesses;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Date getPasswordCreatedAt() {
		return passwordCreatedAt;
	}

	public void setPasswordCreatedAt(Date passwordCreatedAt) {
		this.passwordCreatedAt = passwordCreatedAt;
	}

	public Boolean getIsDeleted() {
		return isDeleted;
	}

	public void setIsDeleted(Boolean isDeleted) {
		this.isDeleted = isDeleted;
	}

	public Individual getIndividual() {
		return individual;
	}

	public void setIndividual(Individual individual) {
		this.individual = individual;
	}

	public List<Access> getAccesses() {
		return accesses;
	}

	public void setAccesses(List<Access> accesses) {
		this.accesses = accesses;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {

		return this.accesses;
	}

	@Override
	public String getUsername() {
		return this.login;
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}

	@Override
	public int hashCode() {
		return Objects.hash(id);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		User other = (User) obj;
		return Objects.equals(id, other.id);
	}

}
