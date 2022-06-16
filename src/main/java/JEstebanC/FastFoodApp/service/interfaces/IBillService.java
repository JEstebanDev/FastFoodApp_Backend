/**
 * 
 */
package JEstebanC.FastFoodApp.service.interfaces;

import JEstebanC.FastFoodApp.dto.BillUserDTO;
import JEstebanC.FastFoodApp.dto.UserBillOrdersDTO;
import JEstebanC.FastFoodApp.enumeration.StatusBill;
import JEstebanC.FastFoodApp.enumeration.StatusOrder;
import JEstebanC.FastFoodApp.model.Bill;

import java.text.ParseException;
import java.util.Collection;
import java.util.Date;

/**
 * @author Juan Esteban Castaño Holguin castanoesteban9@gmail.com 2022-01-26
 */
public interface IBillService {

	BillUserDTO create(Bill bill);

	UserBillOrdersDTO findByIdBill(Long idBill);

	Collection<UserBillOrdersDTO> findByNewIdUser(String username, StatusBill statusBill, String startDate, String endDate);

	Collection<UserBillOrdersDTO> findByOrder(StatusOrder statusOrder, String startDate, String endDate);
	
	BillUserDTO update(Long idBill, Bill bill);

	BillUserDTO updateStatusBill(Long idBill, StatusBill statusBill);

	Boolean delete(Long idBill);

	Boolean exist(Long idBill);
}
