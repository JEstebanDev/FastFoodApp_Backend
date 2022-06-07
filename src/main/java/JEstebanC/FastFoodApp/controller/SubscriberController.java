/**
 * 
 */
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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import JEstebanC.FastFoodApp.model.Response;
import JEstebanC.FastFoodApp.model.Subscriber;
import JEstebanC.FastFoodApp.service.SubscriberServiceImp;
import lombok.RequiredArgsConstructor;

/**
 * @author Juan Esteban Castaño Holguin castanoesteban9@gmail.com 2022-03-11
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/subscriber")
public class SubscriberController {

	@Autowired
	private final SubscriberServiceImp serviceImp;

//	CREATE 
	@PostMapping()
	public ResponseEntity<Response> saveSubscriber(@RequestBody @Valid Subscriber subscriber) {
		if (!serviceImp.existByEmail(subscriber.getEmail())) {
			return ResponseEntity.ok(Response.builder().timeStamp(Instant.now())
					.data(Map.of("subscriber", serviceImp.create(subscriber))).message("Create subscriber")
					.status(HttpStatus.OK).statusCode(HttpStatus.OK.value()).build());
		} else {
			return ResponseEntity.ok(Response.builder().timeStamp(Instant.now())
					.message("The subscriber: " + subscriber.getEmail() + " already exist")
					.status(HttpStatus.BAD_REQUEST).statusCode(HttpStatus.BAD_REQUEST.value()).build());
		}

	}

	@PreAuthorize("hasRole('ROLE_ADMIN') OR hasRole('ROLE_EMPLOYEE')")
//  READ
	@GetMapping(value = "/list/{page}")
	public ResponseEntity<Response> getSubscriber(@PathVariable("page") Long page) {
		return ResponseEntity.ok(Response.builder().timeStamp(Instant.now()).data(Map.of("user", serviceImp.list(page)))
				.message("List users").status(HttpStatus.OK).statusCode(HttpStatus.OK.value()).build());
	}
	
	@PreAuthorize("hasRole('ROLE_ADMIN') OR hasRole('ROLE_EMPLOYEE')")
//  READ
	@GetMapping(value = "/list/email/{email}")
	public ResponseEntity<Response> getSubscriberByEmail(@PathVariable("email") String email) {
		return ResponseEntity.ok(Response.builder().timeStamp(Instant.now()).data(Map.of("user", serviceImp.searchByEmail(email)))
				.message("List users").status(HttpStatus.OK).statusCode(HttpStatus.OK.value()).build());
	}
	
//	DELETE
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@DeleteMapping(value = "/{id}")
	public ResponseEntity<Response> deleteSubscriber(@PathVariable("id") Long id) {
		if (serviceImp.exist(id)) {
			return ResponseEntity.ok(Response.builder().timeStamp(Instant.now())
					.data(Map.of("user", serviceImp.delete(id))).message("Delete subscriber with id: " + id)
					.status(HttpStatus.OK).statusCode(HttpStatus.OK.value()).build());
		} else {
			return ResponseEntity
					.ok(Response.builder().timeStamp(Instant.now()).message("The subscriber " + id + " does not exist")
							.status(HttpStatus.BAD_REQUEST).statusCode(HttpStatus.BAD_REQUEST.value()).build());
		}
	}
}
