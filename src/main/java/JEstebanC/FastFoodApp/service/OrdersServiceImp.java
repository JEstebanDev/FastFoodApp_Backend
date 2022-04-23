/**
 * 
 */
package JEstebanC.FastFoodApp.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import JEstebanC.FastFoodApp.dto.BillOrdersDTO;
import JEstebanC.FastFoodApp.dto.validation.UserForBillDTO;
import JEstebanC.FastFoodApp.enumeration.StatusOrder;
import JEstebanC.FastFoodApp.model.Additional;
import JEstebanC.FastFoodApp.model.Orders;
import JEstebanC.FastFoodApp.model.PayMode;
import JEstebanC.FastFoodApp.model.Product;
import JEstebanC.FastFoodApp.repository.IBillRepository;
import JEstebanC.FastFoodApp.repository.IOrdersRepository;
import JEstebanC.FastFoodApp.repository.IProductRepository;
import JEstebanC.FastFoodApp.service.interfaces.IOrdersService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * @author Juan Esteban Castaño Holguin castanoesteban9@gmail.com 2022-01-27
 */
@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class OrdersServiceImp implements IOrdersService {

	@Autowired
	private final IOrdersRepository ordersRepository;
	@Autowired
	private final IBillRepository billRepository;
	@Autowired
	private final IProductRepository productRepository;

	@Override
	public BillOrdersDTO create(Orders orders) {
		log.info("Saving new order: " + orders.getIdOrder());
		Product product = productRepository.findByIdProduct(orders.getProduct().getIdProduct());
		Collection<Additional> additionals = orders.getAdditional();
		int totalAdditional = 0;
		for (Additional additional : additionals) {
			totalAdditional += additional.getPrice();
		}
		orders.setStatusOrder(StatusOrder.NEW);
		orders.setTotal((product.getPrice() * orders.getAmount()) +( totalAdditional*orders.getAmount()));

		return convertirOrderToDTO(ordersRepository.save(orders));
		
	}

	@Override
	public BillOrdersDTO update(Long id, Orders orders) {
		log.info("Updating orders with id: " + id);
		Product product = productRepository.findByIdProduct(orders.getProduct().getIdProduct());
		Collection<Additional> additionals = orders.getAdditional();
		int totalAdditional = 0;
		for (Additional additional : additionals) {
			totalAdditional += additional.getPrice();
		}
		orders.setTotal((product.getPrice() * orders.getAmount()) +( totalAdditional*orders.getAmount()));

		return convertirOrderToDTO(ordersRepository.save(orders));
	}

	@Override
	public Boolean delete(Long idOrders) {
		log.info("Deleting the order with id: " + idOrders);
		if (ordersRepository.existsById(idOrders)) {
			ordersRepository.deleteById(idOrders);
			return true;
		} else {
			return false;
		}
	}

	@Override
	public Collection<BillOrdersDTO> list() {
		log.info("List all orders");
		return ordersRepository.findAll().stream().map(this::convertirOrderToDTO).collect(Collectors.toList());

	}

	public Collection<BillOrdersDTO> findByIdBill(Long idBill) {
		log.info("List all orders with id bill: " + idBill);
		if (billRepository.existsById(idBill)) {
			return !ordersRepository.findByIdBill(idBill).isEmpty() ? ordersRepository.findByIdBill(idBill).stream()
					.map(this::convertirOrderToDTO).collect(Collectors.toList()) : null;
		} else {
			return null;
		}

	}

	@Override
	public Boolean exist(Long idOrders) {
		log.info("Searching order by id: " + idOrders);
		return ordersRepository.existsById(idOrders);
	}

	private BillOrdersDTO convertirOrderToDTO(Orders orders) {

		BillOrdersDTO billOrder = new BillOrdersDTO();
		billOrder.setIdOrder(orders.getIdOrder());
		billOrder.setStatusOrder(orders.getStatusOrder());
		billOrder.setAmount(orders.getAmount());
		billOrder.setNoTable(orders.getNoTable());
		billOrder.setTotal(orders.getTotal());

		Collection<Product> product = new ArrayList<Product>();
		product.add(orders.getProduct());
		billOrder.setProduct(product);

		Collection<Additional> additional = new ArrayList<Additional>();
		additional.addAll(orders.getAdditional());
		billOrder.setAdditional(additional);

		billOrder.setIdBill(orders.getBill().getIdBill());
		billOrder.setDate(orders.getBill().getDate());
		billOrder.setStatusBill(orders.getBill().getStatusBill());

		UserForBillDTO userForBill = new UserForBillDTO();
		userForBill.setIdUser(orders.getBill().getUser().getIdUser());
		userForBill.setUrlImage(orders.getBill().getUser().getUrlImage());
		userForBill.setUsername(orders.getBill().getUser().getUsername());
		userForBill.setName(orders.getBill().getUser().getName());

		billOrder.setUserForBill(userForBill);

		PayMode payMode = new PayMode();
		payMode.setIdPayMode(orders.getBill().getPayMode().getIdPayMode());
		payMode.setName(orders.getBill().getPayMode().getName());
		payMode.setStatus(orders.getBill().getPayMode().getStatus());

		billOrder.setPayMode(payMode);

		return billOrder;
	}

}
