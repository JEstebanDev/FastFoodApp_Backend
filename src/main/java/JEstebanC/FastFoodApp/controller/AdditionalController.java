/**
 * 
 */
package JEstebanC.FastFoodApp.controller;

import java.time.Instant;
import java.util.Collection;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

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
	public ResponseEntity<Response> saveAdditional(@RequestParam("request") @Valid String strAddiotional,
			@RequestParam("additionalImage")@Nullable MultipartFile file) {
		try {
			Additional additional= serviceImp.create( new ObjectMapper().readValue(strAddiotional, Additional.class), file);
			if (additional!=null) {
				return ResponseEntity.ok(Response.builder().timeStamp(Instant.now())
						.data(Map.of("additional", additional)).message("Create additional")
						.status(HttpStatus.OK).statusCode(HttpStatus.OK.value()).build());
			}else {
				return ResponseEntity.ok(Response.builder().timeStamp(Instant.now())
						.message("Error creating the additional verify if you added the category").status(HttpStatus.BAD_REQUEST)
						.statusCode(HttpStatus.BAD_REQUEST.value()).build());
			}
		} catch (JsonProcessingException e) {
			return ResponseEntity.ok(Response.builder().timeStamp(Instant.now())
					.message("Error creating the additional: " + e.getMessage()).status(HttpStatus.BAD_REQUEST)
					.statusCode(HttpStatus.BAD_REQUEST.value()).build());
		}
	}

//  READ
	@GetMapping(value = "/list")
	public ResponseEntity<Response> getadditional() {
		return ResponseEntity
				.ok(Response.builder().timeStamp(Instant.now()).data(Map.of("additional", serviceImp.list()))
						.message("List additionals").status(HttpStatus.OK).statusCode(HttpStatus.OK.value()).build());
	}

//	UPDATE
	@PreAuthorize("hasRole('ROLE_ADMIN') OR hasRole('ROLE_EMPLOYEE')")
	@PutMapping(value = "/{id}")
	public ResponseEntity<Response> updateAdditional(@PathVariable("id") Long id,
			@RequestParam("strAdditional") @Valid String strAdditional,
			@RequestParam("additionalImage") @Nullable MultipartFile file) {
		try {
			if (serviceImp.exist(id)) {
				Additional additional= serviceImp.update(id,new ObjectMapper().readValue(strAdditional, Additional.class), file);
				if (additional!=null) {
					return ResponseEntity.ok(Response.builder().timeStamp(Instant.now())
							.data(Map.of("additional", additional))
							.message("Update additional with id:" + id).status(HttpStatus.OK)
							.statusCode(HttpStatus.OK.value()).build());
				} else {
					return ResponseEntity.ok(Response.builder().timeStamp(Instant.now())
							.message("Error updating the additional verify if you added the category").status(HttpStatus.BAD_REQUEST)
							.statusCode(HttpStatus.BAD_REQUEST.value()).build());
				}
				
			} else {
				return ResponseEntity.ok(Response.builder().timeStamp(Instant.now())
						.message("The additional with id:" + id + " does not exist").status(HttpStatus.BAD_REQUEST)
						.statusCode(HttpStatus.BAD_REQUEST.value()).build());
			}

		} catch (JsonProcessingException e) {
			return ResponseEntity.ok(Response.builder().timeStamp(Instant.now())
					.message("Error creating the additional: " + e.getMessage()).status(HttpStatus.BAD_REQUEST)
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
			return ResponseEntity.ok(Response.builder().timeStamp(Instant.now())
					.message("The additional called" + name + " does not exist").status(HttpStatus.BAD_REQUEST)
					.statusCode(HttpStatus.BAD_REQUEST.value()).build());
		}

	}
	
//	SEARCH BY IDCATEGORY
	@GetMapping(value = "/category/{idCategory}")
	public ResponseEntity<Response> getAdditionalByCategory(@PathVariable("idCategory") Long idCategory) {
		Collection<Additional> additional=serviceImp.findByCategory(idCategory);
		if (additional!= null) {
			return ResponseEntity.ok(Response.builder().timeStamp(Instant.now())
					.data(Map.of("additional",additional)).message("Get additional by category: " + idCategory)
					.status(HttpStatus.OK).statusCode(HttpStatus.OK.value()).build());

		} else {
			return ResponseEntity.ok(Response.builder().timeStamp(Instant.now())
					.message("The additional with id category: " + idCategory + " does not exist").status(HttpStatus.BAD_REQUEST)
					.statusCode(HttpStatus.BAD_REQUEST.value()).build());
		}

	}
}
