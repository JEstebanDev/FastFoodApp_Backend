/**
 * 
 */
package JEstebanC.FastFoodApp.service.interfaces;

import java.util.Collection;

import JEstebanC.FastFoodApp.dto.BillOrdersDTO;
import JEstebanC.FastFoodApp.model.Orders;

/**
 * @author Juan Esteban Castaño Holguin castanoesteban9@gmail.com 2022-01-27
 */
public interface IOrdersService {

	BillOrdersDTO create(Orders orders);

	BillOrdersDTO update(Long id, Orders orders);

	Boolean delete(Long idOrders);

	Collection<BillOrdersDTO> list();

	Boolean exist(Long idOrders);

}
