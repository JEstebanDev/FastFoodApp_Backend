/**
 * 
 */
package JEstebanC.FastFoodApp.service.interfaces;


import JEstebanC.FastFoodApp.dto.OrdersDTO;
import JEstebanC.FastFoodApp.model.Orders;

/**
 * @author Juan Esteban Castaño Holguin castanoesteban9@gmail.com 2022-01-27
 */
public interface IOrdersService {

	OrdersDTO create(Orders orders);

	OrdersDTO update(Long id, Orders orders);

	Boolean delete(Long idOrders);

	Boolean exist(Long idOrders);

}
