/**
 * 
 */
package JEstebanC.FastFoodApp.dto;

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
	private int phone;
	private String email;
	private int discountPoint;
	private Status status;
}
