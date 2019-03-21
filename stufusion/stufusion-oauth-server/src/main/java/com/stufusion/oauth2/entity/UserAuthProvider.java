package com.stufusion.oauth2.entity;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;


import com.stufusion.oauth2.enums.AuthProviderType;

@Entity
@Table(name = "user_auth_provider")
public class UserAuthProvider extends BaseEntity implements Serializable {

	private static final long serialVersionUID = 6874667425302308430L;

	@Column(name = "AUTHPROVIDERTYPE")
	@Enumerated(EnumType.STRING)
	private AuthProviderType authProviderType;

	@Column(name = "AUTHPROVIDER_ID")
	private String authProviderId;

	@JoinColumn(name = "USER_ID", referencedColumnName = "id")
	@ManyToOne(optional = false)
	private User user;

	public UserAuthProvider() {
		super();
	}

	public UserAuthProvider(AuthProviderType authProviderType, String authProviderId, User userId) {
		super();
		this.authProviderType = authProviderType;
		this.authProviderId = authProviderId;
		this.user = userId;
	}

	/**
	 * @return the userId
	 */
	public User getUser() {
		return user;
	}

	/**
	 * @param user
	 *            the userId to set
	 */
	public void setUser(User user) {
		this.user = user;
	}

	/**
	 * @return the authProviderType
	 */
	public AuthProviderType getAuthProviderType() {
		return authProviderType;
	}

	/**
	 * @param authProviderType
	 *            the authProviderType to set
	 */
	public void setAuthProviderType(AuthProviderType authProviderType) {
		this.authProviderType = authProviderType;
	}

	/**
	 * @return the authProviderId
	 */
	public String getAuthProviderId() {
		return authProviderId;
	}

	/**
	 * @param authProviderId
	 *            the authProviderId to set
	 */
	public void setAuthProviderId(String authProviderId) {
		this.authProviderId = authProviderId;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((authProviderId == null) ? 0 : authProviderId.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		UserAuthProvider other = (UserAuthProvider) obj;
		if (authProviderId == null) {
			if (other.authProviderId != null)
				return false;
		} else if (!authProviderId.equals(other.authProviderId))
			return false;
		return true;
	}
}
