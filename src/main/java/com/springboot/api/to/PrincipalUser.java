package com.springboot.api.to;

import java.util.Collection;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;

/**
 * 
 * @author anilt
 *
 */
public class PrincipalUser extends User {

	private static final long serialVersionUID = -3531439484732724601L;

	private final Long userId;
	
	public PrincipalUser(String username, String password, Collection<SimpleGrantedAuthority> authorities,
			Long userId) {
		super(username, password, authorities);
		this.userId = userId;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public Long getUserId() {
		return userId;
	}

}
