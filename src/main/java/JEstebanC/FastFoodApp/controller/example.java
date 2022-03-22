/**
 * 
 */
package JEstebanC.FastFoodApp.controller;

import java.time.Instant;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import JEstebanC.FastFoodApp.model.Response;
import lombok.RequiredArgsConstructor;

/**
 * @author Juan Esteban Castaño Holguin
 * castanoesteban9@gmail.com
 * 2022-03-21
 */
@RestController
 @RequiredArgsConstructor
 @RequestMapping("/example")
public class example {
	@GetMapping(value = "/list")
	public ResponseEntity<Response> getCategory() {
		return ResponseEntity.ok(Response.builder().timeStamp(Instant.now())
				.message("Hola").status(HttpStatus.OK).statusCode(HttpStatus.OK.value()).build());
	}

}
