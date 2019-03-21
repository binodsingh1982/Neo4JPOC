package com.stufusion.oauth2.entity;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

@Entity
@Table(name = "users")
public class User extends BaseEntity implements UserDetails {

	private static final long serialVersionUID = 6311364761937265306L;

	@Column(name = "EMAIL")
	private String email;

	@Column(name = "USERTYPE")
	private String userType;

	@Column(name = "USERID")
	private String userId;

	@Column(name = "PASSWORD")
	private String password;

	@Column(name = "ENABLED")
	private boolean enabled;

	@Column(name = "FIRST_NAME")
	private String firstName;

	@Column(name = "LAST_NAME")
	private String lastName;

	@OneToMany(cascade = CascadeType.ALL)
	@JoinTable(name = "user_roles", joinColumns = {
			@JoinColumn(name = "USER_ID", referencedColumnName = "ID") }, inverseJoinColumns = {
					@JoinColumn(name = "ROLE_ID", referencedColumnName = "ID") })
	private Set<Role> roles;

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "id")
	private Set<UserAuthProvider> userAuthProviders;

	@OneToOne(mappedBy = "user")
	private VerificationToken verificationToken;

	public User() {

	}

	// create userId here and pass to core while creating user on core
	// password(optional)
	// confrimPassword will be removed
	// maintain status (ACTIVE, INACTIVE,VERFY_PENDING,LOCKED) (ENUM)
	// verificationToken (no expiry of link)
	// authentication
	// AuthProvider

	public String getPassword() {
		return password;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public boolean getEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	public Set<Role> getRoles() {
		return roles;
	}

	public void setRoles(Set<Role> roles) {
		this.roles = roles;
	}

	@Transient
	public Set<Permission> getPermissions() {
		Set<Permission> perms = new HashSet<Permission>();
		for (Role role : roles) {
			perms.addAll(role.getPermissions());
		}
		return perms;
	}

	@Override
	@Transient
	public Collection<GrantedAuthority> getAuthorities() {
		Set<GrantedAuthority> authorities = new HashSet<GrantedAuthority>();
		authorities.addAll(getRoles());
		authorities.addAll(getPermissions());
		return authorities;
	}

	@Override
	public String getUsername() {
		return email;
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
		return this.getEnabled();
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getUserType() {
		return userType;
	}

	public void setUserType(String userType) {
		this.userType = userType;
	}

	@Transient
	public void addRole(Role role) {
		if (roles == null) {
			roles = new HashSet<>();
		}
		roles.add(role);
	}

	public VerificationToken getVerificationToken() {
		return verificationToken;
	}

	public void setVerificationToken(VerificationToken verificationToken) {
		this.verificationToken = verificationToken;
	}

	@Transient
	public void addUserAuthProvider(UserAuthProvider userAuthProvider) {
		if (userAuthProviders == null) {
			userAuthProviders = new HashSet<>();
		}
		userAuthProviders.add(userAuthProvider);
	}

	/**
	 * @return the userAuthProviders
	 */
	public Set<UserAuthProvider> getUserAuthProviders() {
		return userAuthProviders;
	}

	/**
	 * @param userAuthProviders
	 *            the userAuthProviders to set
	 */
	public void setUserAuthProviders(Set<UserAuthProvider> userAuthProviders) {
		this.userAuthProviders = userAuthProviders;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((email == null) ? 0 : email.hashCode());
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
		User other = (User) obj;
		if (email == null) {
			if (other.email != null)
				return false;
		} else if (!email.equals(other.email))
			return false;
		return true;
	}

}
