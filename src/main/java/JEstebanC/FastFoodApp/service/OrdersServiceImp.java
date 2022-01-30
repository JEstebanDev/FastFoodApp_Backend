/**
 * 
 */
package JEstebanC.FastFoodApp.service;

import java.util.Collection;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import JEstebanC.FastFoodApp.enumeration.Status;
import JEstebanC.FastFoodApp.model.Orders;
import JEstebanC.FastFoodApp.repository.IBillRepository;
import JEstebanC.FastFoodApp.repository.IOrdersRepository;
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
	IOrdersRepository ordersRepository;
	@Autowired
	IBillRepository billRepository;

	@Override
	public Orders create(Orders orders) {
		log.info("Saving new order: " + orders.getIdOrder());
		return ordersRepository.save(orders);
	}

	@Override
	public Orders update(Orders orders) {
		log.info("Updating orders with id: " + orders.getIdOrder());
		return ordersRepository.save(orders);
	}

	@Override
	public Boolean delete(Long idOrders) {
		log.info("Deleting the order with id: " + idOrders);
		if (ordersRepository.existsById(idOrders)) {

			Optional<Orders> orders = ordersRepository.findById(idOrders);
			orders.get().setStatus(Status.INACTIVO);
			return true;
		} else {
			return false;
		}
	}

	@Override
	public Collection<Orders> list() {
		log.info("List all orders");
		return ordersRepository.findAll();
	}

	public Collection<Orders> findByIdBill(Long idBill) {
		log.info("List all orders with id bill: " + idBill);
		if (billRepository.existsById(idBill)) {
			return !ordersRepository.findByIdBill(idBill).isEmpty() ? ordersRepository.findByIdBill(idBill) : null;
		} else {
			return null;
		}

	}

	@Override
	public Boolean exist(Long idOrders) {
		log.info("Searching order by id: " + idOrders);
		return ordersRepository.existsById(idOrders);
	}

}
