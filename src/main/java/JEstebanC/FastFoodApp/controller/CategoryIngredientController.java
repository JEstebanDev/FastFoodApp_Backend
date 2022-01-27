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

import JEstebanC.FastFoodApp.model.CategoryIngredient;
import JEstebanC.FastFoodApp.model.Response;
import JEstebanC.FastFoodApp.service.CategoryIngredientServiceImp;
import lombok.RequiredArgsConstructor;

/**
 * @author Juan Esteban Castaño Holguin castanoesteban9@gmail.com 2022-01-24
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/category-ingredient")
public class CategoryIngredientController {

	@Autowired
	private final CategoryIngredientServiceImp serviceImp;

//	CREATE
	@PostMapping()
	public ResponseEntity<Response> saveCategoryIngredient(@RequestBody @Valid CategoryIngredient categoryIngredient) {
		return ResponseEntity.ok(Response.builder().timeStamp(Instant.now())
				.data(Map.of("categoryIngredient", serviceImp.create(categoryIngredient)))
				.message("Create category ingredient").status(HttpStatus.OK).statusCode(HttpStatus.OK.value()).build());
	}

//  READ
	@GetMapping(value = "/list")
	public ResponseEntity<Response> getCategoryIngredient() {
		return ResponseEntity.ok(Response.builder().timeStamp(Instant.now())
				.data(Map.of("categoryIngredient", serviceImp.list())).message("List categories ingredients")
				.status(HttpStatus.OK).statusCode(HttpStatus.OK.value()).build());
	}

//	UPDATE
	@PutMapping(value = "/{id}")
	public ResponseEntity<Response> updateCategoryIngredient(@PathVariable("id") Long id,
			@RequestBody @Valid CategoryIngredient categoryIngredient) {
		if (serviceImp.exist(id)) {
			return ResponseEntity.ok(Response.builder().timeStamp(Instant.now())
					.data(Map.of("categoryIngredient", serviceImp.update(categoryIngredient)))
					.message("Update category ingredient with id:" + id).status(HttpStatus.OK)
					.statusCode(HttpStatus.OK.value()).build());
		} else {
			return ResponseEntity.ok(Response.builder().timeStamp(Instant.now())
					.message("The category ingredient with id:" + id + " does not exist").status(HttpStatus.BAD_REQUEST)
					.statusCode(HttpStatus.BAD_REQUEST.value()).build());
		}
	}

//	DELETE
	@DeleteMapping(value = "/{id}")
	public ResponseEntity<Response> deleteCategoryIngredient(@PathVariable("id") Long id) {
		if (serviceImp.exist(id)) {
			return ResponseEntity.ok(Response.builder().timeStamp(Instant.now())
					.data(Map.of("categoryIngredient", serviceImp.delete(id)))
					.message("Delete category ingredient with id: " + id).status(HttpStatus.OK)
					.statusCode(HttpStatus.OK.value()).build());
		} else {
			return ResponseEntity.ok(Response.builder().timeStamp(Instant.now())
					.message("The category ingredient " + id + " does not exist").status(HttpStatus.BAD_REQUEST)
					.statusCode(HttpStatus.BAD_REQUEST.value()).build());
		}
	}

//	SEARCH BY NAME
	@GetMapping(value = "/{name}")
	public ResponseEntity<Response> getCategoryIngredientByName(@PathVariable("name") String name) {
		if (serviceImp.findByName(name) != null) {
			return ResponseEntity.ok(Response.builder().timeStamp(Instant.now())
					.data(Map.of("categoryIngredient", serviceImp.findByName(name)))
					.message("Get category ingredient by name: " + name).status(HttpStatus.OK)
					.statusCode(HttpStatus.OK.value()).build());
		} else {
			return ResponseEntity.ok(Response.builder().timeStamp(Instant.now())
					.message("The category ingredient called" + name + " does not exist").status(HttpStatus.BAD_REQUEST)
					.statusCode(HttpStatus.BAD_REQUEST.value()).build());
		}

	}
}
