package JEstebanC.FastFoodApp.controller;

import java.time.Instant;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import JEstebanC.FastFoodApp.model.Additional;
import JEstebanC.FastFoodApp.model.Orders;
import JEstebanC.FastFoodApp.model.Response;
import JEstebanC.FastFoodApp.service.OrdersServiceImp;
import lombok.RequiredArgsConstructor;

/**
 * @author Juan Esteban Castaño Holguin castanoesteban9@gmail.com 2022-01-28
 */

@RestController
@RequiredArgsConstructor
@PreAuthorize("authenticated")
@RequestMapping("/orders")
public class OrdersController {

	@Autowired
	private final OrdersServiceImp serviceImp;

//	CREATE
	@PostMapping()
	public ResponseEntity<Response> saveOrder(@RequestBody @Valid Orders order) {
		return ResponseEntity
				.ok(Response.builder().timeStamp(Instant.now()).data(Map.of("order", serviceImp.create(order)))
						.message("Create order").status(HttpStatus.OK).statusCode(HttpStatus.OK.value()).build());
	}

//	CREATE ADDITIONAL
	@PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_EMPLOYEE')")
	@PostMapping(value = "/additional/{idOrder}")
	public ResponseEntity<Response> saveAdditional(@PathVariable("idOrder") Long idOrder,
			@RequestBody @Valid Additional additional) {
		return ResponseEntity.ok(Response.builder().timeStamp(Instant.now())
				.data(Map.of("order", serviceImp.addAdditionalToOrder(idOrder, additional)))
				.message("Added additional to product with id: " + idOrder).status(HttpStatus.OK)
				.statusCode(HttpStatus.OK.value()).build());

	}

//  READ
	@PreAuthorize("hasRole('ROLE_ADMIN') OR hasRole('ROLE_EMPLOYEE')")
	@GetMapping(value = "/list")
	public ResponseEntity<Response> getOrder() {
		return ResponseEntity.ok(Response.builder().timeStamp(Instant.now()).data(Map.of("order", serviceImp.list()))
				.message("List orders").status(HttpStatus.OK).statusCode(HttpStatus.OK.value()).build());
	}

//	UPDATE
	@PreAuthorize("hasRole('ROLE_ADMIN') OR hasRole('ROLE_EMPLOYEE')")
	@PutMapping(value = "/{id}")
	public ResponseEntity<Response> updateOrder(@PathVariable("id") Long id, @RequestBody @Valid Orders order) {
		if (serviceImp.exist(id)) {
			return ResponseEntity.ok(Response.builder().timeStamp(Instant.now())
					.data(Map.of("order", serviceImp.update(id, order))).message("Update order with id:" + id)
					.status(HttpStatus.OK).statusCode(HttpStatus.OK.value()).build());
		}
		return ResponseEntity
				.ok(Response.builder().timeStamp(Instant.now()).message("The order with id:" + id + " does not exist")
						.status(HttpStatus.BAD_REQUEST).statusCode(HttpStatus.BAD_REQUEST.value()).build());

	}

	@PreAuthorize("hasRole('ROLE_ADMIN') OR hasRole('ROLE_EMPLOYEE')")
//	DELETE
	@DeleteMapping(value = "/{id}")
	public ResponseEntity<Response> deleteOrder(@PathVariable("id") Long id) {
		if (serviceImp.exist(id)) {
			return ResponseEntity.ok(Response.builder().timeStamp(Instant.now())
					.data(Map.of("order", serviceImp.delete(id))).message("order bill with id: " + id)
					.status(HttpStatus.OK).statusCode(HttpStatus.OK.value()).build());
		} else {
			return ResponseEntity
					.ok(Response.builder().timeStamp(Instant.now()).message("The bill " + id + " does not exist")
							.status(HttpStatus.BAD_REQUEST).statusCode(HttpStatus.BAD_REQUEST.value()).build());
		}
	}

	@PreAuthorize("hasRole('ROLE_ADMIN') OR hasRole('ROLE_EMPLOYEE')")
// 	SEARCH  ORDER BY ID CLIENT
	@GetMapping(value = "/bill/{idBill}")
	public ResponseEntity<Response> getOrderByIdClient(@PathVariable("idBill") Long idBill) {
		return ResponseEntity
				.ok(Response.builder().timeStamp(Instant.now()).data(Map.of("order", serviceImp.findByIdBill(idBill)))
						.message("List orders").status(HttpStatus.OK).statusCode(HttpStatus.OK.value()).build());
	}
}
