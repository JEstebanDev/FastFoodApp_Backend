/**
 * 
 */
package JEstebanC.FastFoodApp.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import JEstebanC.FastFoodApp.dto.BillUserDTO;
import JEstebanC.FastFoodApp.dto.OrdersDTO;
import JEstebanC.FastFoodApp.dto.UserBillOrdersDTO;
import JEstebanC.FastFoodApp.dto.UserForBillDTO;
import JEstebanC.FastFoodApp.enumeration.StatusBill;
import JEstebanC.FastFoodApp.model.Additional;
import JEstebanC.FastFoodApp.model.Bill;
import JEstebanC.FastFoodApp.model.Orders;
import JEstebanC.FastFoodApp.model.PayMode;
import JEstebanC.FastFoodApp.model.Product;
import JEstebanC.FastFoodApp.repository.IBillRepository;
import JEstebanC.FastFoodApp.repository.IOrdersRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * @author Juan Esteban Castaño Holguin castanoesteban9@gmail.com 2022-01-26
 */
@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class BillServiceImp implements IBillService {

	@Autowired
	private IBillRepository billRepository;
	@Autowired
	private final IOrdersRepository ordersRepository;

	@Override
	public BillUserDTO create(Bill bill) {
		log.info("Saving new bill with id: " + bill.getIdBill());
		return convertirBillToDTO(billRepository.save(bill));
	}

	@Override
	public BillUserDTO update(Long idBill, Bill bill) {
		log.info("Updating bill with id: " + bill.getIdBill());
		Bill billOld = billRepository.findByIdBill(idBill);
		billOld.setStatusBill(bill.getStatusBill());
		return convertirBillToDTO(billRepository.save(billOld));
	}

	@Override
	public Boolean delete(Long idBill) {
		log.info("Deleting the bill id: " + idBill);
		if (billRepository.existsById(idBill)) {
			billRepository.deleteById(idBill);
			return true;
		} else {
			return false;
		}
	}

	@Override
	public Boolean exist(Long idBill) {
		log.info("Searching bills by id: " + idBill);
		return billRepository.existsById(idBill);
	}

	private BillUserDTO convertirBillToDTO(Bill bill) {
		BillUserDTO billUser = new BillUserDTO();
		billUser.setIdBill(bill.getIdBill());

		UserForBillDTO userForBill = new UserForBillDTO();
		userForBill.setIdUser(bill.getUser().getIdUser());
		userForBill.setUrlImage(bill.getUser().getUrlImage());
		userForBill.setUsername(bill.getUser().getUsername());
		userForBill.setName(bill.getUser().getName());

		billUser.setUserForBill(userForBill);

		PayMode payMode = new PayMode();
		payMode.setIdPayMode(bill.getPayMode().getIdPayMode());
		payMode.setName(bill.getPayMode().getName());
		payMode.setStatus(bill.getPayMode().getStatus());

		billUser.setPayMode(payMode);

		billUser.setDate(bill.getDate());
		billUser.setStatusBill(bill.getStatusBill());

		return billUser;
	}

	// ----------------------------------------

	private UserBillOrdersDTO convertirBillOrderToDTO(Bill bill) {
		UserBillOrdersDTO billOrder = new UserBillOrdersDTO();

		// -----------------------------
		BillUserDTO billUser = new BillUserDTO();
		billUser.setIdBill(bill.getIdBill());

		UserForBillDTO userForBill = new UserForBillDTO();
		userForBill.setIdUser(bill.getUser().getIdUser());
		userForBill.setUrlImage(bill.getUser().getUrlImage());
		userForBill.setUsername(bill.getUser().getUsername());
		userForBill.setName(bill.getUser().getName());

		billUser.setUserForBill(userForBill);

		PayMode payMode = new PayMode();
		payMode.setIdPayMode(bill.getPayMode().getIdPayMode());
		payMode.setName(bill.getPayMode().getName());
		payMode.setStatus(bill.getPayMode().getStatus());

		billUser.setPayMode(payMode);

		billUser.setDate(bill.getDate());
		billUser.setStatusBill(bill.getStatusBill());

		// -------------------------------

		Collection<OrdersDTO> orders = ordersRepository.findByIdBill(bill.getIdBill()).stream()
				.map(this::convertirOrderToDTO).collect(Collectors.toList());

		billOrder.setBillUserDTO(billUser);
		billOrder.setOrdersDTO(orders);
		return billOrder;
	}

	private OrdersDTO convertirOrderToDTO(Orders orders) {

		OrdersDTO billOrder = new OrdersDTO();
		billOrder.setIdOrder(orders.getIdOrder());
		billOrder.setAmount(orders.getAmount());
		billOrder.setNoTable(orders.getNoTable());
		billOrder.setTotal(orders.getTotal());

		Collection<Product> product = new ArrayList<Product>();
		product.add(orders.getProduct());
		billOrder.setProduct(product);

		Collection<Additional> additional = new ArrayList<Additional>();
		additional.addAll(orders.getAdditional());
		billOrder.setAdditional(additional);

		return billOrder;
	}

	@Override
	public UserBillOrdersDTO findByIdBill(Long idBill) {
		return convertirBillOrderToDTO(billRepository.findByIdBill(idBill));
	}

	@Override
	public Collection<UserBillOrdersDTO> findByNewIdUser(Long idUser, StatusBill statusBill, String startDate,
			String endDate) {

		if (idUser != null && statusBill != null && startDate != null && endDate != null) {
			try {
				return billRepository
						.findByIdUserAndStatusBillAndDateBetween(idUser, statusBill.ordinal(),
								new SimpleDateFormat("yyyy-MM-dd").parse(startDate),
								new SimpleDateFormat("yyyy-MM-dd").parse(endDate))
						.stream().map(this::convertirBillOrderToDTO).collect(Collectors.toList());
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
		if (idUser == null && statusBill == null && startDate == null && endDate == null) {

			return billRepository.findAll().stream().map(this::convertirBillOrderToDTO).collect(Collectors.toList());

		}
		if (idUser != null && statusBill == null && startDate == null && endDate == null) {

			return billRepository.findByIdUser(idUser).stream().map(this::convertirBillOrderToDTO)
					.collect(Collectors.toList());

		}
		if (idUser == null && statusBill != null && startDate == null && endDate == null) {

			return billRepository.findByStatusBill(statusBill).stream().map(this::convertirBillOrderToDTO)
					.collect(Collectors.toList());

		}
		if (idUser != null && statusBill != null && startDate == null && endDate == null) {
			return billRepository.findByIdUserAndStatusBill(idUser, statusBill.ordinal()).stream()
					.map(this::convertirBillOrderToDTO).collect(Collectors.toList());
		}
		if (idUser == null && statusBill == null && startDate != null && endDate != null) {

			try {
				return billRepository
						.findByDateBetween(new SimpleDateFormat("yyyy-MM-dd").parse(startDate),
								new SimpleDateFormat("yyyy-MM-dd").parse(endDate))
						.stream().map(this::convertirBillOrderToDTO).collect(Collectors.toList());
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
		if (idUser != null && statusBill == null && startDate != null && endDate != null) {

			try {
				return billRepository
						.findByDateBetweenAndIdUser(new SimpleDateFormat("yyyy-MM-dd").parse(startDate),
								new SimpleDateFormat("yyyy-MM-dd").parse(endDate), idUser)
						.stream().map(this::convertirBillOrderToDTO).collect(Collectors.toList());
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
		if (idUser == null && statusBill != null && startDate != null && endDate != null) {

			try {
				return billRepository
						.findByDateBetweenAndStatusBill(new SimpleDateFormat("yyyy-MM-dd").parse(startDate),
								new SimpleDateFormat("yyyy-MM-dd").parse(endDate), statusBill)
						.stream().map(this::convertirBillOrderToDTO).collect(Collectors.toList());
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
		return null;
	}

}