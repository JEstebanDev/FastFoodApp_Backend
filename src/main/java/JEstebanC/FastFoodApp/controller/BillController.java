package JEstebanC.FastFoodApp.controller;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;

import java.time.Instant;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
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

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;

import JEstebanC.FastFoodApp.dto.UserBillOrdersDTO;
import JEstebanC.FastFoodApp.enumeration.StatusBill;
import JEstebanC.FastFoodApp.model.Bill;
import JEstebanC.FastFoodApp.model.Response;
import JEstebanC.FastFoodApp.model.User;
import JEstebanC.FastFoodApp.security.OperationUtil;
import JEstebanC.FastFoodApp.service.BillServiceImp;
import JEstebanC.FastFoodApp.service.UserServiceImp;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * @author Juan Esteban Castaño Holguin castanoesteban9@gmail.com 2022-01-26
 */
@RestController
@RequiredArgsConstructor
@PreAuthorize("authenticated")
@RequestMapping("/bill")
@Slf4j
public class BillController {

	@Autowired
	private final BillServiceImp serviceImp;
	@Autowired
	private final UserServiceImp serviceImpUser;

//	CREATE
	@PostMapping()
	public ResponseEntity<Response> saveBill(@RequestBody @Valid Bill bill) {
		return ResponseEntity
				.ok(Response.builder().timeStamp(Instant.now()).data(Map.of("bill", serviceImp.create(bill)))
						.message("Create bill").status(HttpStatus.OK).statusCode(HttpStatus.OK.value()).build());
	}

//  READ SEARCH BY PARAMS
	@PreAuthorize("hasRole('ROLE_ADMIN') OR hasRole('ROLE_EMPLOYEE') OR hasRole('ROLE_CLIENT')")
	@GetMapping(value = "/list")
	public ResponseEntity<Response> listByParams(@Param(value = "idBill") Long idBill,
			@Param(value = "idUser") Long idUser, @Param(value = "statusBill") StatusBill statusBill,
			@Param(value = "startDate") String startDate, @Param(value = "endDate") String endDate,
			HttpServletRequest request) {
		if (request.isUserInRole("ROLE_CLIENT")) {
			log.info("ENTRA CLIENTE");
			String authorizationHeader = request.getHeader(AUTHORIZATION);
			if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
				try {
					String token = authorizationHeader.substring("Bearer ".length());
					Algorithm algorithm = Algorithm.HMAC256(OperationUtil.keyValue().getBytes());
					JWTVerifier verifier = JWT.require(algorithm).build();
					DecodedJWT decodeJWT = verifier.verify(token);
					

					if (idBill != null) {
						log.info("ENTRA CLIENTE BILL");
						UserBillOrdersDTO userBillOrdersCLient = serviceImp.findByIdBill(idBill);
						if (userBillOrdersCLient.getBillUserDTO().getUserForBill().getUsername()
								.equals(decodeJWT.getSubject().toString())) {
							return ResponseEntity.ok(Response.builder().timeStamp(Instant.now())
									.data(Map.of("bill", serviceImp.findByIdBill(idBill))).message("bill")
									.status(HttpStatus.OK).statusCode(HttpStatus.OK.value()).build());
						}else {
							return ResponseEntity.ok(Response.builder().timeStamp(Instant.now())
									.message(("Error seeing the bill, you have not the permissions"))
									.status(HttpStatus.BAD_REQUEST).statusCode(HttpStatus.BAD_REQUEST.value()).build());
						}
					}
					if (idUser!=null) {
						User userOld = serviceImpUser.findById(idUser);
						if (userOld.getUsername().equals(decodeJWT.getSubject().toString())) {
							log.info("ENTRA CLIENTE SIN BILL");
							return ResponseEntity.ok(Response.builder().timeStamp(Instant.now())
									.data(Map.of("bill",
											serviceImp.findByNewIdUser(userOld.getIdUser(), statusBill, startDate,
													endDate)))
									.message("bill").status(HttpStatus.OK).statusCode(HttpStatus.OK.value()).build());
						} else {
							log.info("ENTRA CLIENTE ERROR");
							return ResponseEntity.ok(Response.builder().timeStamp(Instant.now())
									.message(("Error seeing the bill, you have not the permissions"))
									.status(HttpStatus.BAD_REQUEST).statusCode(HttpStatus.BAD_REQUEST.value()).build());
						}
					}else {
						return ResponseEntity.ok(Response.builder().timeStamp(Instant.now())
								.message(("Error seeing the bill, you have not the permissions"))
								.status(HttpStatus.BAD_REQUEST).statusCode(HttpStatus.BAD_REQUEST.value()).build());
					}
					

				} catch (Exception e) {
					return ResponseEntity.ok(Response.builder().timeStamp(Instant.now())
							.message(("Error validating bill by client: " + e.getMessage()))
							.status(HttpStatus.BAD_REQUEST).statusCode(HttpStatus.BAD_REQUEST.value()).build());
				}
			}

		}
		if (idBill != null) {
			return ResponseEntity.ok(
					Response.builder().timeStamp(Instant.now()).data(Map.of("bill", serviceImp.findByIdBill(idBill)))
							.message("bill").status(HttpStatus.OK).statusCode(HttpStatus.OK.value()).build());

		}
		return ResponseEntity.ok(Response.builder().timeStamp(Instant.now())
				.data(Map.of("bill", serviceImp.findByNewIdUser(idUser, statusBill, startDate, endDate)))
				.message("bill").status(HttpStatus.OK).statusCode(HttpStatus.OK.value()).build());

	}

//	UPDATE
	@PreAuthorize("hasRole('ROLE_ADMIN') OR hasRole('ROLE_EMPLOYEE')")
	@PutMapping(value = "/{id}")
	public ResponseEntity<Response> updateBill(@PathVariable("id") Long id, @RequestBody @Valid Bill bill) {
		if (serviceImp.exist(id)) {
			return ResponseEntity.ok(Response.builder().timeStamp(Instant.now())
					.data(Map.of("bill", serviceImp.update(id, bill))).message("Update bill with id:" + id)
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

}
