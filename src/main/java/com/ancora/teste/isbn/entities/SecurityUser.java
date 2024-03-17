package com.ancora.teste.isbn.entities;

import java.util.Arrays;
import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class SecurityUser implements UserDetails{

	private User user;
	
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {

		return Arrays.stream(user
				.getRoles()
				.split(","))
				.map(SimpleGrantedAuthority::new)
				.toList();
	}

	@Override
	public String getPassword() {

		return this.user.getPassword();
	}

	@Override
	public String getUsername() {

		return this.user.getUserName();
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

}
