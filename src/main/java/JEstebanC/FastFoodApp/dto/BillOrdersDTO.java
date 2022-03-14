/**
 * 
 */
package JEstebanC.FastFoodApp.dto;

import java.util.Collection;
import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import JEstebanC.FastFoodApp.enumeration.StatusBill;
import JEstebanC.FastFoodApp.model.PayMode;
import JEstebanC.FastFoodApp.model.Additional;
import JEstebanC.FastFoodApp.model.Product;
import lombok.Data;

/**
 * @author Juan Esteban Castaño Holguin castanoesteban9@gmail.com 2022-03-14
 */
@Data
public class BillOrdersDTO {
	
//  Bill
	private Long idBill;
	@DateTimeFormat(iso = DateTimeFormat.ISO.DATE, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSX")
	private Date date;

	private UserForBillDTO userForBill;

	private PayMode payMode;

	private StatusBill statusBill;

//	Order
	private Long idOrder;
	private int amount;
	private int noTable;
	private int total;

//	Product
	private Collection<Product> product;

//	Additional
	private Collection<Additional> Additional;


}
