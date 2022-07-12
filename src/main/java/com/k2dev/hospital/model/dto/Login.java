package com.k2dev.hospital.model.dto;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Transient;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class Login implements UserDetails {
	
	@Transient
	private static final long serialVersionUID = -9074315824329891786L;

	@Id
	@Column(unique = true)
	@Size(max= 50, message = "Username can't be larger than 50 chars")
	private String username;
	
	@JsonProperty(access = Access.WRITE_ONLY)
	@Column(nullable = false)
	private String password;
	
	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(
			name = "login_role",
			joinColumns = @JoinColumn(name= "username"),
			inverseJoinColumns = @JoinColumn(name= "role_id")
	)
	
	@JsonIgnore
	private Set<Role> roles= new HashSet<>();

	
	
	@Column(columnDefinition = "boolean default false")
	private boolean enabled;

	@Column(columnDefinition = "boolean default false")
	private boolean credentialsNonExpired;

	@Column(columnDefinition = "boolean default false")
	private boolean accountNonLocked;

	@Column(columnDefinition = "boolean default false")
	private boolean accountNonExpired;

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		Collection<SimpleGrantedAuthority> authorities= new HashSet<>();
		roles.stream().forEach(role->{
			authorities.add(new SimpleGrantedAuthority(role.getRoleName()));
		});
		return authorities;
		
	}

	@Override
	public String getPassword() {
		return password;
	}

	@Override
	public String getUsername() {
		return username;
	}

	@Override
	public boolean isAccountNonExpired() {
		return accountNonExpired;
	}

	@Override
	public boolean isAccountNonLocked() {
		return accountNonLocked;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return credentialsNonExpired;
	}

	@Override
	public boolean isEnabled() {
		return enabled;
	}

	public Set<Role> getRoles() {
		return roles;
	}

	public void setRoles(Set<Role> roles) {
		this.roles = roles;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	public void setCredentialsNonExpired(boolean credentialsNonExpired) {
		this.credentialsNonExpired = credentialsNonExpired;
	}

	public void setAccountNonLocked(boolean accountNonLocked) {
		this.accountNonLocked = accountNonLocked;
	}

	public void setAccountNonExpired(boolean accountNonExpired) {
		this.accountNonExpired = accountNonExpired;
	}

//	public static UserDetails build(Login login) {
//		List<GrantedAuthority> authorities= login.getRoles().stream()
//				.map(role -> new SimpleGrantedAuthority(role.getRoleName()))
//				.collect(Collectors.toList());
//		return new Login(login.getUsername(),  login.getPassword(), authorities);
//		
//	}
}
