/**
 * 
 */
package JEstebanC.FastFoodApp.controller;

import java.text.ParseException;
import java.time.Instant;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import JEstebanC.FastFoodApp.model.Response;
import JEstebanC.FastFoodApp.service.ReportServiceImp;
import lombok.RequiredArgsConstructor;

/**
 * @author Juan Esteban Castaño Holguin castanoesteban9@gmail.com 2022-03-16
 */
@RestController
@RequiredArgsConstructor
@PreAuthorize("hasRole('ROLE_ADMIN')")
@RequestMapping("/report")
public class ReportController {

	@Autowired
	private final ReportServiceImp serviceImp;

	@GetMapping(value = "/product")
	public ResponseEntity<Response> listByParams(@Param(value = "idProduct") Long idProduct,
			@Param(value = "limit") Integer limit, @Param(value = "startDate") String startDate,
			@Param(value = "endDate") String endDate) {
		return ResponseEntity.ok(Response.builder().timeStamp(Instant.now())
				.data(Map.of("report", serviceImp.getRankProducts(idProduct, limit, startDate, endDate)))
				.message("report").status(HttpStatus.OK).statusCode(HttpStatus.OK.value()).build());
	}  

	@GetMapping(value = "/client")
	public ResponseEntity<Response> listBestClientsByParams(@Param(value = "username") String username,@Param(value = "startDate") String startDate,
			@Param(value = "endDate") String endDate) throws ParseException {
		return ResponseEntity.ok(Response.builder().timeStamp(Instant.now())
				.data(Map.of("report", serviceImp.getRankClient(username,startDate, endDate))).message("report")
				.status(HttpStatus.OK).statusCode(HttpStatus.OK.value()).build());
	}

	@GetMapping(value = "/sales")
	public ResponseEntity<Response> listSalesByParams(@Param(value = "startDate") String startDate,
			@Param(value = "endDate") String endDate) {
		if (startDate == null && endDate == null) {
			return ResponseEntity.ok(Response.builder().timeStamp(Instant.now()).message("The dates can not be null")
					.status(HttpStatus.BAD_REQUEST).statusCode(HttpStatus.BAD_REQUEST.value()).build());
		} else {
			return ResponseEntity.ok(Response.builder().timeStamp(Instant.now())
					.data(Map.of("report", serviceImp.getSalesByDate(startDate, endDate))).message("report")
					.status(HttpStatus.OK).statusCode(HttpStatus.OK.value()).build());
		}

	}

	@GetMapping(value = "/paymode")
	public ResponseEntity<Response> listPayModeByParams(@Param(value = "startDate") String startDate,
			@Param(value = "endDate") String endDate) {
		if (startDate == null && endDate == null) {
			return ResponseEntity.ok(Response.builder().timeStamp(Instant.now()).message("The dates can not be null")
					.status(HttpStatus.BAD_REQUEST).statusCode(HttpStatus.BAD_REQUEST.value()).build());
		} else {
			return ResponseEntity.ok(Response.builder().timeStamp(Instant.now())
					.data(Map.of("report", serviceImp.getSalesPayModeByDate(startDate, endDate))).message("report")
					.status(HttpStatus.OK).statusCode(HttpStatus.OK.value()).build());
		}
	}
}
