/**
 * 
 */
package JEstebanC.FastFoodApp.enumeration;

/**
 * @author Juan Esteban Castaño Holguin
 * castanoesteban9@gmail.com
 * 2022-02-06
 */
import org.springframework.security.core.GrantedAuthority;

public enum AppUserRole implements GrantedAuthority {
	ROLE_ADMIN, ROLE_EMPLOYEE, ROLE_CLIENT;

	public String getAuthority() {
		return name();
	}

}