package br.com.amaral.model;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "juno_access_token")
@SequenceGenerator(name = "seq_juno_access_token", sequenceName = "seq_juno_access_token", allocationSize = 1, initialValue = 1)
public class JunoAccessToken implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_juno_access_token")
	private Long id;

	@Column(columnDefinition = "text")
	private String access_token;

	private String token_type;

	private String expires_in;

	private String scope;

	private String user_name;

	private String jti;

	private String token_access_base64;

	@Column(name = "created_at", updatable = false)
	@Temporal(TemporalType.TIMESTAMP)
	private Date createdAt = Calendar.getInstance().getTime();	
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getAccess_token() {
		return access_token;
	}

	public void setAccess_token(String access_token) {
		this.access_token = access_token;
	}

	public String getToken_type() {
		return token_type;
	}

	public void setToken_type(String token_type) {
		this.token_type = token_type;
	}

	public String getExpires_in() {
		return expires_in;
	}

	public void setExpires_in(String expires_in) {
		this.expires_in = expires_in;
	}

	public String getScope() {
		return scope;
	}

	public void setScope(String scope) {
		this.scope = scope;
	}

	public String getUser_name() {
		return user_name;
	}

	public void setUser_name(String user_name) {
		this.user_name = user_name;
	}

	public String getJti() {
		return jti;
	}

	public void setJti(String jti) {
		this.jti = jti;
	}

	public String getToken_access_base64() {
		return token_access_base64;
	}

	public void setToken_access_base64(String token_access_base64) {
		this.token_access_base64 = token_access_base64;
	}

	public Date getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}

	public boolean isExpired() {
		
		Date currentDate = Calendar.getInstance().getTime();	
		Long time = currentDate.getTime() - this.createdAt.getTime();	
		Long minutes = (time / 1000) / 60;
		
		if (minutes.intValue() > 50) {
			return true;
		}
		
		return false;
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
		JunoAccessToken other = (JunoAccessToken) obj;
		return Objects.equals(id, other.id);
	}

}
