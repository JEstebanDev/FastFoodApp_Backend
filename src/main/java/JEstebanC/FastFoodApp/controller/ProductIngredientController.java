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

import JEstebanC.FastFoodApp.model.Product_Ingredient;
import JEstebanC.FastFoodApp.model.Response;
import JEstebanC.FastFoodApp.service.ProductIngredientServiceImp;
import lombok.RequiredArgsConstructor;

/**
 * @author Juan Esteban Castaño Holguin castanoesteban9@gmail.com 2022-01-25
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/product-ingredient")
public class ProductIngredientController {

	@Autowired
	private final ProductIngredientServiceImp serviceImp;

//	CREATE
	@PostMapping()
	public ResponseEntity<Response> saveProductIngredient(@RequestBody @Valid Product_Ingredient productIngredient) {
		return ResponseEntity.ok(Response.builder().timeStamp(Instant.now())
				.data(Map.of("productIngredient", serviceImp.create(productIngredient)))
				.message("Create product ingredient").status(HttpStatus.OK).statusCode(HttpStatus.OK.value()).build());
	}

//	READ
	@GetMapping(value = "/list")
	public ResponseEntity<Response> getProductIngredient() {
		return ResponseEntity.ok(Response.builder().timeStamp(Instant.now())
				.data(Map.of("productIngredient", serviceImp.list())).message("List product ingredient")
				.status(HttpStatus.OK).statusCode(HttpStatus.OK.value()).build());
	}

//	UPDATE
	@PutMapping(value = "/{id}")
	public ResponseEntity<Response> updateProduct(@PathVariable("id") Long id,
			@RequestBody @Valid Product_Ingredient productIngredient) {
		if (serviceImp.exist(id)) {
			return ResponseEntity.ok(Response.builder().timeStamp(Instant.now())
					.data(Map.of("productIngredient", serviceImp.update(productIngredient)))
					.message("Update product ingredient with id:" + id).status(HttpStatus.OK)
					.statusCode(HttpStatus.OK.value()).build());
		} else {
			return ResponseEntity.ok(Response.builder().timeStamp(Instant.now())
					.message("The product ingredient with id:" + id + " does not exist").status(HttpStatus.BAD_REQUEST)
					.statusCode(HttpStatus.BAD_REQUEST.value()).build());
		}
	}

//	DELETE
	@DeleteMapping(value = "/{id}")
	public ResponseEntity<Response> deleteProduct(@PathVariable("id") Long id) {
		if (serviceImp.exist(id)) {
			return ResponseEntity.ok(
					Response.builder().timeStamp(Instant.now()).data(Map.of("productIngredient", serviceImp.delete(id)))
							.message("Delete product ingredient with id: " + id).status(HttpStatus.OK)
							.statusCode(HttpStatus.OK.value()).build());
		} else {
			return ResponseEntity.ok(Response.builder().timeStamp(Instant.now())
					.message("The product ingredient " + id + " does not exist").status(HttpStatus.BAD_REQUEST)
					.statusCode(HttpStatus.BAD_REQUEST.value()).build());
		}
	}
}
