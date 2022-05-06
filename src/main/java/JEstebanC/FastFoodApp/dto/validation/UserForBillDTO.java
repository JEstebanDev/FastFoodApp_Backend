/**
 * 
 */
package JEstebanC.FastFoodApp.dto.validation;

import lombok.Data;

/**
 * @author Juan Esteban Castaño Holguin
 * castanoesteban9@gmail.com
 * 2022-02-08
 */
@Data
public class UserForBillDTO {
	private Long idUser;
	private String urlImage;
	private String username;
	private String name;
}
