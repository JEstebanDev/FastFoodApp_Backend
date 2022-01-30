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

import JEstebanC.FastFoodApp.model.Product_Additional;
import JEstebanC.FastFoodApp.model.Response;
import JEstebanC.FastFoodApp.service.ProductAdditionalServiceImp;
import lombok.RequiredArgsConstructor;

/**
 * @author Juan Esteban Castaño Holguin castanoesteban9@gmail.com 2022-01-25
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/product-additional")
public class ProductAdditionalController {

	@Autowired
	private final ProductAdditionalServiceImp serviceImp;

//	CREATE
	@PostMapping()
	public ResponseEntity<Response> saveproductAdditional(@RequestBody @Valid Product_Additional productAdditional) {
		if (serviceImp.create(productAdditional)!=null) {
			return ResponseEntity.ok(Response.builder().timeStamp(Instant.now())
					.data(Map.of("productAdditional", serviceImp.create(productAdditional)))
					.message("Create product additional").status(HttpStatus.OK).statusCode(HttpStatus.OK.value()).build());
		}else {
			return ResponseEntity.ok(Response.builder().timeStamp(Instant.now()).developerMessage("Please check if exist any additional or product")
					.message("Cannot create the additional").status(HttpStatus.OK).statusCode(HttpStatus.OK.value()).build());
		}
		
	}

//	READ
	@GetMapping(value = "/list")
	public ResponseEntity<Response> getproductAdditional() {
		return ResponseEntity.ok(Response.builder().timeStamp(Instant.now())
				.data(Map.of("productAdditional", serviceImp.list())).message("List product additional")
				.status(HttpStatus.OK).statusCode(HttpStatus.OK.value()).build());
	}

//	UPDATE
	@PutMapping(value = "/{id}")
	public ResponseEntity<Response> updateProduct(@PathVariable("id") Long id,
			@RequestBody @Valid Product_Additional productAdditional) {
		if (serviceImp.exist(id)) {
			return ResponseEntity.ok(Response.builder().timeStamp(Instant.now())
					.data(Map.of("productAdditional", serviceImp.update(productAdditional)))
					.message("Update product additional with id:" + id).status(HttpStatus.OK)
					.statusCode(HttpStatus.OK.value()).build());
		} else {
			return ResponseEntity.ok(Response.builder().timeStamp(Instant.now())
					.message("The product additional with id:" + id + " does not exist").status(HttpStatus.BAD_REQUEST)
					.statusCode(HttpStatus.BAD_REQUEST.value()).build());
		}
	}

//	DELETE
	@DeleteMapping(value = "/{id}")
	public ResponseEntity<Response> deleteProduct(@PathVariable("id") Long id) {
		if (serviceImp.exist(id)) {
			return ResponseEntity.ok(
					Response.builder().timeStamp(Instant.now()).data(Map.of("productAdditional", serviceImp.delete(id)))
							.message("Delete product additional with id: " + id).status(HttpStatus.OK)
							.statusCode(HttpStatus.OK.value()).build());
		} else {
			return ResponseEntity.ok(Response.builder().timeStamp(Instant.now())
					.message("The product additional " + id + " does not exist").status(HttpStatus.BAD_REQUEST)
					.statusCode(HttpStatus.BAD_REQUEST.value()).build());
		}
	}
	
//	SEARCH BY PRODUCT 
	@GetMapping(value = "/product/{idProduct}")
	public ResponseEntity<Response> getproductAdditional(@PathVariable("idProduct") Long idProduct) {
		if (serviceImp.findByIdProduct(idProduct)!=null) {
			return ResponseEntity.ok(Response.builder().timeStamp(Instant.now())
					.data(Map.of("productAdditional", serviceImp.findByIdProduct(idProduct))).message("List product additional by id product")
					.status(HttpStatus.OK).statusCode(HttpStatus.OK.value()).build());
		}else {
			return ResponseEntity.ok(Response.builder().timeStamp(Instant.now())
					.developerMessage("Please check if exist any product").message("Cannot list the additionals")
					.status(HttpStatus.OK).statusCode(HttpStatus.OK.value()).build());
		}
		
	}
}
