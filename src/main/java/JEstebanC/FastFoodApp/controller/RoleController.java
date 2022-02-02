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
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import JEstebanC.FastFoodApp.model.Response;
import JEstebanC.FastFoodApp.model.Role;
import JEstebanC.FastFoodApp.service.RoleServiceImp;
import lombok.RequiredArgsConstructor;

/**
 * @author Juan Esteban Castaño Holguin castanoesteban9@gmail.com 2022-02-01
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/role")
public class RoleController {

	@Autowired
	private final RoleServiceImp serviceImp;

//	CREATE
	@PostMapping()
	public ResponseEntity<Response> saveCategory(@RequestBody @Valid Role role) {
		return ResponseEntity
				.ok(Response.builder().timeStamp(Instant.now()).data(Map.of("role", serviceImp.create(role)))
						.message("Create role").status(HttpStatus.OK).statusCode(HttpStatus.OK.value()).build());
	}

//  READ
	@GetMapping(value = "/list")
	public ResponseEntity<Response> getCategory() {
		return ResponseEntity.ok(Response.builder().timeStamp(Instant.now()).data(Map.of("role", serviceImp.list()))
				.message("List role").status(HttpStatus.OK).statusCode(HttpStatus.OK.value()).build());
	}

//	UPDATE
	@PutMapping(value = "/{id}")
	public ResponseEntity<Response> updateCategory(@PathVariable("id") Long id, @RequestBody @Valid Role role) {
		if (serviceImp.exist(id)) {
			return ResponseEntity.ok(Response.builder().timeStamp(Instant.now())
					.data(Map.of("role", serviceImp.update(role))).message("Update role with id:" + id)
					.status(HttpStatus.OK).statusCode(HttpStatus.OK.value()).build());
		} else {
			return ResponseEntity.ok(
					Response.builder().timeStamp(Instant.now()).message("The role with id:" + id + " does not exist")
							.status(HttpStatus.BAD_REQUEST).statusCode(HttpStatus.BAD_REQUEST.value()).build());
		}
	}

//	DELETE
	@DeleteMapping(value = "/{id}")
	public ResponseEntity<Response> deleteCategory(@PathVariable("id") Long id) {
		if (serviceImp.exist(id)) {
			return ResponseEntity.ok(Response.builder().timeStamp(Instant.now())
					.data(Map.of("role", serviceImp.delete(id))).message("Delete role with id: " + id)
					.status(HttpStatus.OK).statusCode(HttpStatus.OK.value()).build());
		} else {
			return ResponseEntity
					.ok(Response.builder().timeStamp(Instant.now()).message("The role " + id + " does not exist")
							.status(HttpStatus.BAD_REQUEST).statusCode(HttpStatus.BAD_REQUEST.value()).build());
		}
	}

//	SEARCH BY NAME
	@GetMapping(value = "/{description}")
	public ResponseEntity<Response> getCategoryByName(@PathVariable("description") String description) {

		if (serviceImp.findByDescription(description) != null) {
			return ResponseEntity.ok(Response.builder().timeStamp(Instant.now())
					.data(Map.of("category", serviceImp.findByDescription(description)))
					.message("Get category by description: " + description).status(HttpStatus.OK)
					.statusCode(HttpStatus.OK.value()).build());

		} else {
			return ResponseEntity.ok(Response.builder().timeStamp(Instant.now())
					.message("The role called" + description + " does not exist").status(HttpStatus.BAD_REQUEST)
					.statusCode(HttpStatus.BAD_REQUEST.value()).build());
		}

	}
}
