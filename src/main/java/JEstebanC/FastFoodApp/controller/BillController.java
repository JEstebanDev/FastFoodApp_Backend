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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import JEstebanC.FastFoodApp.enumeration.StatusBill;
import JEstebanC.FastFoodApp.model.Bill;
import JEstebanC.FastFoodApp.model.Response;
import JEstebanC.FastFoodApp.service.BillServiceImp;
import lombok.RequiredArgsConstructor;

/**
 * @author Juan Esteban Castaño Holguin castanoesteban9@gmail.com 2022-01-26
 */
@RestController
@RequiredArgsConstructor
@PreAuthorize("authenticated")
@RequestMapping("/bill")
public class BillController {

	@Autowired
	private final BillServiceImp serviceImp;

//	CREATE
	@PostMapping()
	public ResponseEntity<Response> saveBill(@RequestBody @Valid Bill bill) {
		return ResponseEntity
				.ok(Response.builder().timeStamp(Instant.now()).data(Map.of("bill", serviceImp.create(bill)))
						.message("Create bill").status(HttpStatus.OK).statusCode(HttpStatus.OK.value()).build());
	}

//  READ
	@GetMapping(value = "/list")
	public ResponseEntity<Response> getBill(@RequestParam(name = "startDate") String startDate,
			@RequestParam(name = "endDate") String endDate, @RequestParam(name = "statusBill") StatusBill statusBill) {

		return ResponseEntity.ok(
				Response.builder().timeStamp(Instant.now()).data(Map.of("bill", serviceImp.list(startDate, endDate,statusBill)))
						.message("List bills").status(HttpStatus.OK).statusCode(HttpStatus.OK.value()).build());
	}

//	UPDATE
	@PreAuthorize("hasRole('ROLE_ADMIN') OR hasRole('ROLE_EMPLOYEE')")
	@PutMapping(value = "/{id}")
	public ResponseEntity<Response> updateBill(@PathVariable("id") Long id, @RequestBody @Valid Bill bill) {
		if (serviceImp.exist(id)) {
			return ResponseEntity.ok(Response.builder().timeStamp(Instant.now())
					.data(Map.of("bill", serviceImp.update(id,bill))).message("Update bill with id:" + id)
					.status(HttpStatus.OK).statusCode(HttpStatus.OK.value()).build());
		} else {
			return ResponseEntity.ok(
					Response.builder().timeStamp(Instant.now()).message("The bill with id:" + id + " does not exist")
							.status(HttpStatus.BAD_REQUEST).statusCode(HttpStatus.BAD_REQUEST.value()).build());
		}
	}

//	DELETE
	@PreAuthorize("hasRole('ROLE_ADMIN') OR hasRole('ROLE_EMPLOYEE')")
	@DeleteMapping(value = "/{id}")
	public ResponseEntity<Response> deleteBill(@PathVariable("id") Long id) {
		if (serviceImp.exist(id)) {
			return ResponseEntity.ok(Response.builder().timeStamp(Instant.now())
					.data(Map.of("bill", serviceImp.delete(id))).message("Delete bill with id: " + id)
					.status(HttpStatus.OK).statusCode(HttpStatus.OK.value()).build());
		} else {
			return ResponseEntity
					.ok(Response.builder().timeStamp(Instant.now()).message("The bill " + id + " does not exist")
							.status(HttpStatus.BAD_REQUEST).statusCode(HttpStatus.BAD_REQUEST.value()).build());
		}
	}

//  SEARCH BY CLIENT
	@PreAuthorize("hasRole('ROLE_ADMIN') OR hasRole('ROLE_EMPLOYEE')")
	@GetMapping(value = "/user/{id}")
	public ResponseEntity<Response> getBill(@PathVariable("id") Long id) {

		if (serviceImp.findByIdUser(id) != null) {
			return ResponseEntity
					.ok(Response.builder().timeStamp(Instant.now()).data(Map.of("bill", serviceImp.findByIdUser(id)))
							.message("bill").status(HttpStatus.OK).statusCode(HttpStatus.OK.value()).build());
		} else {
			return ResponseEntity.ok(Response.builder().timeStamp(Instant.now())
					.message("The bill with id user: " + id + " does not exist").status(HttpStatus.BAD_REQUEST)
					.statusCode(HttpStatus.BAD_REQUEST.value()).build());
		}

	}
}
