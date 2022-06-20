/**
 * 
 */
package JEstebanC.FastFoodApp.dto;

import JEstebanC.FastFoodApp.enumeration.AppUserRole;
import JEstebanC.FastFoodApp.enumeration.Status;
import lombok.Data;

/**
 * @author Juan Esteban Castaño Holguin
 * castanoesteban9@gmail.com
 * 2022-02-08
 */
@Data
public class UserDTO {
	private Long idUser;
	private String name;
	private String username;
	private String urlImage;
	private Long phone;
	private String email;
	private int discountPoint;
	private AppUserRole userRoles;
	private Status status;
}
