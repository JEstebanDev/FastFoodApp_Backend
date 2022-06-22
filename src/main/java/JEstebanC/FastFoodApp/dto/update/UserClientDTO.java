/**
 * 
 */
package JEstebanC.FastFoodApp.dto.update;

import JEstebanC.FastFoodApp.enumeration.Status;
import lombok.Data;

/**
 * @author Juan Esteban Castaño Holguin
 * castanoesteban9@gmail.com
 * 2022-04-18
 */
@Data
public class UserClientDTO {
	private String name;
	private String username;
	private String urlImage;
	private Long phone;
	private String email;
	private String password;
	private Status status;
}
