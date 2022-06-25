/**
 * 
 */
package JEstebanC.FastFoodApp.dto;

import lombok.Data;

import java.math.BigInteger;

/**
 * @author Juan Esteban Castaño Holguin castanoesteban9@gmail.com 2022-03-19
 */
@Data
public class ReportClientDTO {

	private Long idUser;
	private String username;
	private String urlImage;
	private BigInteger total;
}
