/**
 * 
 */
package JEstebanC.FastFoodApp.service;

import java.util.Collection;

import JEstebanC.FastFoodApp.model.Bill;

/**
 * @author Juan Esteban Castaño Holguin
 * castanoesteban9@gmail.com
 * 2022-01-26
 */
public interface IBillService {

	Bill create(Bill bill);

	Bill update(Bill bill);

	Boolean delete(Long idBill);

	Collection<Bill> list();

	Boolean exist(Long idBill);
}
