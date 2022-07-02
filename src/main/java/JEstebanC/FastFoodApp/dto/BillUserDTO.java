/**
 * 
 */
package JEstebanC.FastFoodApp.dto;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import JEstebanC.FastFoodApp.dto.validation.UserForBillDTO;
import JEstebanC.FastFoodApp.enumeration.StatusBill;
import JEstebanC.FastFoodApp.model.PayMode;
import lombok.Data;

/**
 * @author Juan Esteban Castaño Holguin castanoesteban9@gmail.com 2022-02-08
 */
@Data
public class BillUserDTO {
	private Long idBill;
	private String referenceTransaction;
	@DateTimeFormat(iso = DateTimeFormat.ISO.DATE, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSX")
	private Date date;
	private int noTable;
	private int totalPrice;
	private StatusBill statusBill;
	private PayMode payMode;
	private UserForBillDTO userForBill;
	
}
