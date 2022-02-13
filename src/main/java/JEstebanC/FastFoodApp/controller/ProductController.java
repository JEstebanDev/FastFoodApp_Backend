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
import JEstebanC.FastFoodApp.model.Product;
import JEstebanC.FastFoodApp.model.Response;
import JEstebanC.FastFoodApp.service.ProductServiceImp;
import lombok.RequiredArgsConstructor;

/**
 * @author Juan Esteban Castaño Holguin castanoesteban9@gmail.com 2022-01-23
 */

@RestController
@RequiredArgsConstructor
@RequestMapping("/product")
public class ProductController {

	@Autowired
	private final ProductServiceImp serviceImp;

//	CREATE
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@PostMapping()
	public ResponseEntity<Response> saveProduct(@RequestBody @Valid Product product) {
		if (serviceImp.create(product) != null) {
			return ResponseEntity
					.ok(Response.builder().timeStamp(Instant.now()).data(Map.of("product", serviceImp.create(product)))
							.message("Create product").status(HttpStatus.OK).statusCode(HttpStatus.OK.value()).build());
		} else {
			return ResponseEntity.ok(Response.builder().timeStamp(Instant.now())
					.developerMessage("Please check if exist any category").message("Cannot create the product")
					.status(HttpStatus.OK).statusCode(HttpStatus.OK.value()).build());
		}
	}

//	CREATE ADDITIONAL
	@PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_EMPLOYEE')")
	@PostMapping(value = "/additional/{idProduct}")
	public ResponseEntity<Response> saveAdditional(@PathVariable("idProduct") Long idProduct,
			@RequestBody @Valid Additional additional) {
		return ResponseEntity.ok(Response.builder().timeStamp(Instant.now())
				.data(Map.of("product", serviceImp.addAdditionalToProduct(idProduct, additional)))
				.message("Added additional to product with id: " + idProduct).status(HttpStatus.OK)
				.statusCode(HttpStatus.OK.value()).build());

	}
//	READ
	@GetMapping(value = "/list/{page}")
	public ResponseEntity<Response> getProduct(@PathVariable(name = "page") Long page) {
		return ResponseEntity.ok(Response.builder().timeStamp(Instant.now()).data(Map.of("products", serviceImp.list(page)))
				.message("List products").status(HttpStatus.OK).statusCode(HttpStatus.OK.value()).build());
	}

//	UPDATE
	@PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_EMPLOYEE')")
	@PutMapping(value = "/{id}")
	public ResponseEntity<Response> updateProduct(@PathVariable("id") Long id, @RequestBody @Valid Product product) {
		if (serviceImp.exist(id)) {
			return ResponseEntity.ok(Response.builder().timeStamp(Instant.now())
					.data(Map.of("product", serviceImp.update(id,product))).message("Update product with id:" + id)
					.status(HttpStatus.OK).statusCode(HttpStatus.OK.value()).build());
		} else {
			return ResponseEntity.ok(
					Response.builder().timeStamp(Instant.now()).message("The product with id:" + id + " does not exist")
							.status(HttpStatus.BAD_REQUEST).statusCode(HttpStatus.BAD_REQUEST.value()).build());
		}
	}

//	DELETE
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@DeleteMapping(value = "/{id}")
	public ResponseEntity<Response> deleteProduct(@PathVariable("id") Long id) {
		if (serviceImp.exist(id)) {
			return ResponseEntity.ok(Response.builder().timeStamp(Instant.now())
					.data(Map.of("product", serviceImp.delete(id))).message("Delete product with id: " + id)
					.status(HttpStatus.OK).statusCode(HttpStatus.OK.value()).build());
		} else {
			return ResponseEntity
					.ok(Response.builder().timeStamp(Instant.now()).message("The product " + id + " does not exist")
							.status(HttpStatus.BAD_REQUEST).statusCode(HttpStatus.BAD_REQUEST.value()).build());
		}
	}

//	SEARCH BY NAME
	@GetMapping(value = "/{name}")
	public ResponseEntity<Response> getProductByName(@PathVariable("name") String name) {

		if (serviceImp.findByName(name) != null) {
			return ResponseEntity.ok(Response.builder().timeStamp(Instant.now())
					.data(Map.of("products", serviceImp.findByName(name))).message("Get products by name: " + name)
					.status(HttpStatus.OK).statusCode(HttpStatus.OK.value()).build());

		} else {
			return ResponseEntity.ok(Response.builder().timeStamp(Instant.now())
					.message("The product called " + name + " does not exist").status(HttpStatus.BAD_REQUEST)
					.statusCode(HttpStatus.BAD_REQUEST.value()).build());
		}

	}

//	SEARCH BY CATEGORY
	@GetMapping(value = "/category/{name}")
	public ResponseEntity<Response> getProductByCategoryName(@PathVariable("name") String name) {

		if (serviceImp.findByNameCategory(name) != null) {
			return ResponseEntity.ok(Response.builder().timeStamp(Instant.now())
					.data(Map.of("products", serviceImp.findByNameCategory(name)))
					.message("Get product with category called: " + name).status(HttpStatus.OK)
					.statusCode(HttpStatus.OK.value()).build());

		} else {
			return ResponseEntity.ok(Response.builder().timeStamp(Instant.now())
					.message("The product with category called " + name + " does not exist")
					.status(HttpStatus.BAD_REQUEST).statusCode(HttpStatus.BAD_REQUEST.value()).build());
		}

	}
}
