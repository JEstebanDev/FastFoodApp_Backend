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
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import JEstebanC.FastFoodApp.model.CategoryAdditional;
import JEstebanC.FastFoodApp.model.Response;
import JEstebanC.FastFoodApp.service.CategoryAdditionalServiceImp;
import lombok.RequiredArgsConstructor;

/**
 * @author Juan Esteban Castaño Holguin castanoesteban9@gmail.com 2022-01-24
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/category-additional")
public class CategoryAdditionalController {

	@Autowired
	private final CategoryAdditionalServiceImp serviceImp;

//	CREATE
	@PostMapping()
	public ResponseEntity<Response> saveCategoryAdditional(@RequestBody @Valid CategoryAdditional categoryAdditional) {
		return ResponseEntity.ok(Response.builder().timeStamp(Instant.now())
				.data(Map.of("categoryAdditional", serviceImp.create(categoryAdditional)))
				.message("Create category additional").status(HttpStatus.OK).statusCode(HttpStatus.OK.value()).build());
	}

//  READ
	@PostAuthorize("hasRole('ROLE_CLIENT')")
	@GetMapping(value = "/list")
	public ResponseEntity<Response> getCategoryAdditional() {
		return ResponseEntity.ok(Response.builder().timeStamp(Instant.now())
				.data(Map.of("categoryAdditional", serviceImp.list())).message("List categories additionals")
				.status(HttpStatus.OK).statusCode(HttpStatus.OK.value()).build());
	}

//	UPDATE
	@PutMapping(value = "/{id}")
	public ResponseEntity<Response> updateCategoryAdditional(@PathVariable("id") Long id,
			@RequestBody @Valid CategoryAdditional categoryAdditional) {
		if (serviceImp.exist(id)) {
			return ResponseEntity.ok(Response.builder().timeStamp(Instant.now())
					.data(Map.of("categoryAdditional", serviceImp.update(categoryAdditional)))
					.message("Update category additional with id:" + id).status(HttpStatus.OK)
					.statusCode(HttpStatus.OK.value()).build());
		} else {
			return ResponseEntity.ok(Response.builder().timeStamp(Instant.now())
					.message("The category additional with id:" + id + " does not exist").status(HttpStatus.BAD_REQUEST)
					.statusCode(HttpStatus.BAD_REQUEST.value()).build());
		}
	}

//	DELETE
	@DeleteMapping(value = "/{id}")
	public ResponseEntity<Response> deleteCategoryAdditional(@PathVariable("id") Long id) {
		if (serviceImp.exist(id)) {
			return ResponseEntity.ok(Response.builder().timeStamp(Instant.now())
					.data(Map.of("categoryAdditional", serviceImp.delete(id)))
					.message("Delete category additional with id: " + id).status(HttpStatus.OK)
					.statusCode(HttpStatus.OK.value()).build());
		} else {
			return ResponseEntity.ok(Response.builder().timeStamp(Instant.now())
					.message("The category additional " + id + " does not exist").status(HttpStatus.BAD_REQUEST)
					.statusCode(HttpStatus.BAD_REQUEST.value()).build());
		}
	}

//	SEARCH BY NAME
	@GetMapping(value = "/{name}")
	public ResponseEntity<Response> getCategoryAdditionalByName(@PathVariable("name") String name) {
		if (serviceImp.findByName(name) != null) {
			return ResponseEntity.ok(Response.builder().timeStamp(Instant.now())
					.data(Map.of("categoryAdditional", serviceImp.findByName(name)))
					.message("Get category additional by name: " + name).status(HttpStatus.OK)
					.statusCode(HttpStatus.OK.value()).build());
		} else {
			return ResponseEntity.ok(Response.builder().timeStamp(Instant.now())
					.message("The category additional called" + name + " does not exist").status(HttpStatus.BAD_REQUEST)
					.statusCode(HttpStatus.BAD_REQUEST.value()).build());
		}

	}
}
