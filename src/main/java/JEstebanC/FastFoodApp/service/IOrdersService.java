/**
 * 
 */
package JEstebanC.FastFoodApp.service;

import java.util.Collection;

import JEstebanC.FastFoodApp.model.Orders;

/**
 * @author Juan Esteban Castaño Holguin
 * castanoesteban9@gmail.com
 * 2022-01-27
 */
public interface IOrdersService {

	Orders create(Orders orders);

	Orders update(Orders orders);

	Boolean delete(Long idOrders);

	Collection<Orders> list();

    Boolean exist(Long idOrders);

}
