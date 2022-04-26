/**
 * 
 */
package JEstebanC.FastFoodApp.service;

import java.util.ArrayList;
import java.util.Collection;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import JEstebanC.FastFoodApp.dto.OrdersDTO;
import JEstebanC.FastFoodApp.enumeration.StatusOrder;
import JEstebanC.FastFoodApp.model.Additional;
import JEstebanC.FastFoodApp.model.Orders;
import JEstebanC.FastFoodApp.model.Product;
import JEstebanC.FastFoodApp.repository.IAdditionalRepository;
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
	private final IProductRepository productRepository;
	@Autowired
	private final IAdditionalRepository additionalRepository;

	@Override
	public OrdersDTO create(Orders orders) {
		log.info("Saving new order: " + orders.getIdOrder());
		orders.setStatusOrder(StatusOrder.NEW);
		return convertirOrderToDTO(ordersRepository.save(orders));
	}

	@Override
	public OrdersDTO update(Long id, Orders orders) {
		log.info("Updating orders with id: " + id);
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
	public Boolean exist(Long idOrders) {
		log.info("Searching order by id: " + idOrders);
		return ordersRepository.existsById(idOrders);
	}

	public Orders findByIdOrder(Long idOrders) {
		log.info("Searching order by id: " + idOrders);
		return ordersRepository.findById(idOrders).get();
	}

	private OrdersDTO convertirOrderToDTO(Orders orders) {
		int totalAdditional = 0;
		if (orders.getProduct().getIdProduct() != null) {
			Product product = productRepository.findByIdProduct(orders.getProduct().getIdProduct());
			if (orders.getAdditional() != null) {
				Collection<Additional> additionals = orders.getAdditional();

				for (Additional additional : additionals) {
					Additional additionalRequest = additionalRepository
							.findByIdAdditional(additional.getIdAdditional());
					totalAdditional += additionalRequest.getPrice();
				}
			}
			orders.setTotal((product.getPrice() * orders.getAmount()) + (totalAdditional * orders.getAmount()));
		} else {
			return null;
		}

		OrdersDTO Order = new OrdersDTO();
		Order.setIdOrder(orders.getIdOrder());
		Order.setStatusOrder(orders.getStatusOrder());
		Order.setAmount(orders.getAmount());
		Order.setTotal(orders.getTotal());

		Collection<Product> product = new ArrayList<Product>();
		product.add(orders.getProduct());
		Order.setProduct(product);

		Collection<Additional> additional = new ArrayList<Additional>();
		additional.addAll(orders.getAdditional());
		Order.setAdditional(additional);

		return Order;
	}

}
