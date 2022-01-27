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

import JEstebanC.FastFoodApp.model.Ingredient;
import JEstebanC.FastFoodApp.model.Response;
import JEstebanC.FastFoodApp.service.IngredientServiceImp;
import lombok.RequiredArgsConstructor;

/**
 * @author Juan Esteban Castaño Holguin castanoesteban9@gmail.com 2022-01-24
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/ingredient")
public class IngredientController {

	@Autowired
	private final IngredientServiceImp serviceImp;

//	CREATE
	@PostMapping()
	public ResponseEntity<Response> saveIngredient(@RequestBody @Valid Ingredient ingredient) {
		return ResponseEntity
				.ok(Response.builder().timeStamp(Instant.now()).data(Map.of("ingredient", serviceImp.create(ingredient)))
						.message("Create ingredient").status(HttpStatus.OK).statusCode(HttpStatus.OK.value()).build());
	}

//  READ
	@GetMapping(value = "/list")
	public ResponseEntity<Response> getIngredient() {
		return ResponseEntity.ok(Response.builder().timeStamp(Instant.now()).data(Map.of("ingredient", serviceImp.list()))
				.message("List ingredients").status(HttpStatus.OK).statusCode(HttpStatus.OK.value()).build());
	}

//	UPDATE
	@PutMapping(value = "/{id}")
	public ResponseEntity<Response> updateIngredient(@PathVariable("id") Long id, @RequestBody @Valid Ingredient ingredient) {
		if (serviceImp.exist(id)) {
			return ResponseEntity.ok(Response.builder().timeStamp(Instant.now())
					.data(Map.of("ingredient", serviceImp.update(ingredient))).message("Update ingredient with id:" + id)
					.status(HttpStatus.OK).statusCode(HttpStatus.OK.value()).build());
		} else {
			return ResponseEntity.ok(Response.builder().timeStamp(Instant.now())
					.message("The ingredient with id:" + id + " does not exist").status(HttpStatus.BAD_REQUEST)
					.statusCode(HttpStatus.BAD_REQUEST.value()).build());
		}
	}

//	DELETE
	@DeleteMapping(value = "/{id}")
	public ResponseEntity<Response> deleteIngredient(@PathVariable("id") Long id) {
		if (serviceImp.exist(id)) {
			return ResponseEntity.ok(Response.builder().timeStamp(Instant.now())
					.data(Map.of("ingredient", serviceImp.delete(id))).message("Delete ingredient with id: " + id)
					.status(HttpStatus.OK).statusCode(HttpStatus.OK.value()).build());
		} else {
			return ResponseEntity
					.ok(Response.builder().timeStamp(Instant.now()).message("The ingredient " + id + " does not exist")
							.status(HttpStatus.BAD_REQUEST).statusCode(HttpStatus.BAD_REQUEST.value()).build());
		}
	}
//	SEARCH BY NAME
	@GetMapping(value = "/{name}")
	public ResponseEntity<Response> getIngredientByName(@PathVariable("name") String name) {

		if (serviceImp.findByName(name) != null) {
			return ResponseEntity.ok(Response.builder().timeStamp(Instant.now())
					.data(Map.of("ingredient", serviceImp.findByName(name))).message("Get ingredient by name: " + name)
					.status(HttpStatus.OK).statusCode(HttpStatus.OK.value()).build());

		} else {
			return ResponseEntity.ok(
					Response.builder().timeStamp(Instant.now()).message("The ingredient called" + name + " does not exist")
							.status(HttpStatus.BAD_REQUEST).statusCode(HttpStatus.BAD_REQUEST.value()).build());
		}

	}
}
