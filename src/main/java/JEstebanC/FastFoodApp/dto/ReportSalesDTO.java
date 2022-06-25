/**
 * 
 */
package JEstebanC.FastFoodApp.dto;

import lombok.Data;

import java.math.BigInteger;

/**
 * @author Juan Esteban Castaño Holguin castanoesteban9@gmail.com 2022-03-21
 */
@Data
public class ReportSalesDTO {

	private Long idBill;

	private BigInteger total;
}
