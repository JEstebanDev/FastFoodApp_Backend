/**
 * 
 */
package JEstebanC.FastFoodApp.dto;

import java.util.Collection;

import lombok.Data;

/**
 * @author Juan Esteban Castaño Holguin castanoesteban9@gmail.com 2022-03-12
 */
@Data
public class UserBillOrdersDTO {

	private BillUserDTO billUserDTO;

	private Collection<OrdersDTO> ordersDTO;

}
