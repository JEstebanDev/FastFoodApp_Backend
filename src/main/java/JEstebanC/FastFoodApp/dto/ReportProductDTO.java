/**
 * 
 */
package JEstebanC.FastFoodApp.dto;
import lombok.Data;

import java.math.BigInteger;

/**
 * @author Juan Esteban Castaño Holguin castanoesteban9@gmail.com 2022-03-16
 */
@Data
public class ReportProductDTO {

	private Long idProduct;
	private String name;
	private int amount;
	private BigInteger total;
}
