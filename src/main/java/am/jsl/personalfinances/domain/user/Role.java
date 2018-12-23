package am.jsl.personalfinances.domain.user;

import org.springframework.security.core.GrantedAuthority;

import java.io.Serializable;

/**
 * Role enum containing possible roles.
 *
 * @author hamlet
 */
public enum Role implements Serializable, GrantedAuthority {

	/**
	 * The user role
	 */
	USER,

	/**
	 * The admin role
	 */
	ADMIN;

	@Override
	public String toString(){
		return name();
	}

	@Override
	public String getAuthority() {
		return name();
	}
}
