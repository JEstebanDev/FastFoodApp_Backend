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
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import JEstebanC.FastFoodApp.model.Additional;
import JEstebanC.FastFoodApp.model.Response;
import JEstebanC.FastFoodApp.service.AdditionalServiceImp;
import lombok.RequiredArgsConstructor;

/**
 * @author Juan Esteban Castaño Holguin castanoesteban9@gmail.com 2022-01-24
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/additional")
public class AdditionalController {

	@Autowired
	private final AdditionalServiceImp serviceImp;

	
//	CREATE
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@PostMapping()
	public ResponseEntity<Response> saveAdditional(@RequestBody @Valid Additional addiotional) {
		return ResponseEntity
				.ok(Response.builder().timeStamp(Instant.now()).data(Map.of("additional", serviceImp.create(addiotional)))
						.message("Create additional").status(HttpStatus.OK).statusCode(HttpStatus.OK.value()).build());
	}

//  READ
	@GetMapping(value = "/list")
	public ResponseEntity<Response> getadditional() {
		return ResponseEntity.ok(Response.builder().timeStamp(Instant.now()).data(Map.of("additional", serviceImp.list()))
				.message("List additionals").status(HttpStatus.OK).statusCode(HttpStatus.OK.value()).build());
	}

//	UPDATE
	@PreAuthorize("hasRole('ROLE_ADMIN') OR hasRole('ROLE_EMPLOYEE')")
	@PutMapping(value = "/{id}")
	public ResponseEntity<Response> updateAdditional(@PathVariable("id") Long id, @RequestBody @Valid Additional addiotional) {
		if (serviceImp.exist(id)) {
			return ResponseEntity.ok(Response.builder().timeStamp(Instant.now())
					.data(Map.of("additional", serviceImp.update(id,addiotional))).message("Update additional with id:" + id)
					.status(HttpStatus.OK).statusCode(HttpStatus.OK.value()).build());
		} else {
			return ResponseEntity.ok(Response.builder().timeStamp(Instant.now())
					.message("The additional with id:" + id + " does not exist").status(HttpStatus.BAD_REQUEST)
					.statusCode(HttpStatus.BAD_REQUEST.value()).build());
		}
	}

//	DELETE
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@DeleteMapping(value = "/{id}")
	public ResponseEntity<Response> deleteAdditional(@PathVariable("id") Long id) {
		if (serviceImp.exist(id)) {
			return ResponseEntity.ok(Response.builder().timeStamp(Instant.now())
					.data(Map.of("additional", serviceImp.delete(id))).message("Delete additional with id: " + id)
					.status(HttpStatus.OK).statusCode(HttpStatus.OK.value()).build());
		} else {
			return ResponseEntity
					.ok(Response.builder().timeStamp(Instant.now()).message("The additional " + id + " does not exist")
							.status(HttpStatus.BAD_REQUEST).statusCode(HttpStatus.BAD_REQUEST.value()).build());
		}
	}
//	SEARCH BY NAME
	@GetMapping(value = "/{name}")
	public ResponseEntity<Response> getAdditionalByName(@PathVariable("name") String name) {

		if (serviceImp.findByName(name) != null) {
			return ResponseEntity.ok(Response.builder().timeStamp(Instant.now())
					.data(Map.of("additional", serviceImp.findByName(name))).message("Get additional by name: " + name)
					.status(HttpStatus.OK).statusCode(HttpStatus.OK.value()).build());

		} else {
			return ResponseEntity.ok(
					Response.builder().timeStamp(Instant.now()).message("The additional called" + name + " does not exist")
							.status(HttpStatus.BAD_REQUEST).statusCode(HttpStatus.BAD_REQUEST.value()).build());
		}

	}
}
