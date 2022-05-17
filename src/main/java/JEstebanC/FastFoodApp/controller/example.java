package JEstebanC.FastFoodApp.controller;

import java.time.Instant;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import JEstebanC.FastFoodApp.model.Response;
import lombok.RequiredArgsConstructor;
@RestController
@RequiredArgsConstructor
@RequestMapping("/example")
public class example {
	@GetMapping()
	public ResponseEntity<Response> getPayMode() {
		return ResponseEntity.ok(Response.builder().timeStamp(Instant.now()).data(Map.of("Example", "HOLA"))
				.message("Example").status(HttpStatus.OK).statusCode(HttpStatus.OK.value()).build());
	}
}
