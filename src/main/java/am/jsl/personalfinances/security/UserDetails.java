package am.jsl.personalfinances.security;

import am.jsl.personalfinances.ex.AccessDeniedException;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * The user details contains user information which will be stored in session.
 * @author hamlet
 */
@Component("userDetails")
@Scope("session")
public class UserDetails implements Serializable {
	private long id = 0;
	private String login = null;
	private String role = null;
	private String fullName = null;
	private Map<String, String> permissions = new HashMap<String, String>(0);

	public UserDetails(long id, String login) {
		super();
		this.id = id;
		this.login = login;
	}

	public UserDetails() {
		super();
	}

	public boolean hasPermission(String permissionName) {
		return permissions.containsKey(permissionName);
	}

	public boolean hasRole(String roleStr) {
		return roleStr.equals(role);
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public Map<String, String> getPermissions() {
		return permissions;
	}

	public void setPermissions(Map<String, String> permissions) {
		this.permissions = permissions;
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}


	public void check(String permission) {
		if (!hasPermission(permission)) {
			throw new AccessDeniedException();
		}
	}
}
