/**
 * 
 */
package JEstebanC.FastFoodApp.service;

import java.util.Collection;

import JEstebanC.FastFoodApp.dto.BillUserDTO;
import JEstebanC.FastFoodApp.dto.UserBillOrdersDTO;
import JEstebanC.FastFoodApp.enumeration.StatusBill;
import JEstebanC.FastFoodApp.model.Bill;

/**
 * @author Juan Esteban Castaño Holguin castanoesteban9@gmail.com 2022-01-26
 */
public interface IBillService {

	BillUserDTO create(Bill bill);

	UserBillOrdersDTO findByIdBill(Long idBill);

	Collection<UserBillOrdersDTO> findByNewIdUser(Long idUser, StatusBill statusBill, String startDate, String endDate);

	BillUserDTO update(Long idBill, Bill bill);

	Boolean delete(Long idBill);

	Boolean exist(Long idBill);
}
