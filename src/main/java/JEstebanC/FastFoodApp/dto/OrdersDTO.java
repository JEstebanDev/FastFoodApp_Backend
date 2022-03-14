/**
 * 
 */
package JEstebanC.FastFoodApp.dto;

import java.util.Collection;

import JEstebanC.FastFoodApp.model.Additional;
import JEstebanC.FastFoodApp.model.Product;
import lombok.Data;

/**
 * @author Juan Esteban Castaño Holguin
 * castanoesteban9@gmail.com
 * 2022-03-12
 */
@Data
public class OrdersDTO {

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