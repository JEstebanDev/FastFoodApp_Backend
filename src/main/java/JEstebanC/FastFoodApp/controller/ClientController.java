/**
 * 
 */
package JEstebanC.FastFoodApp.controller;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;

import java.io.IOException;
import java.time.Instant;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MimeTypeUtils;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.fasterxml.jackson.databind.ObjectMapper;

import JEstebanC.FastFoodApp.filter.OperationUtil;
import JEstebanC.FastFoodApp.model.Client;
import JEstebanC.FastFoodApp.model.Response;
import JEstebanC.FastFoodApp.service.ClientServiceImp;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * @author Juan Esteban Castaño Holguin castanoesteban9@gmail.com 2022-01-26
 */
@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/client")
public class ClientController {

	@Autowired
	private final ClientServiceImp serviceImp;

//	CREATE
	@PostMapping()
	public ResponseEntity<Response> saveClient(@RequestBody @Valid Client client) {
		return ResponseEntity
				.ok(Response.builder().timeStamp(Instant.now()).data(Map.of("client", serviceImp.create(client)))
						.message("Create client").status(HttpStatus.OK).statusCode(HttpStatus.OK.value()).build());
	}

//  READ
	@GetMapping(value = "/list")
	public ResponseEntity<Response> getClient() {
		return ResponseEntity.ok(Response.builder().timeStamp(Instant.now()).data(Map.of("client", serviceImp.list()))
				.message("List clients").status(HttpStatus.OK).statusCode(HttpStatus.OK.value()).build());
	}

//	UPDATE
	@PutMapping(value = "/{id}")
	public ResponseEntity<Response> updateClient(@PathVariable("id") Long id, @RequestBody @Valid Client client) {
		if (serviceImp.exist(id)) {
			return ResponseEntity.ok(Response.builder().timeStamp(Instant.now())
					.data(Map.of("client", serviceImp.update(client))).message("Update client with id:" + id)
					.status(HttpStatus.OK).statusCode(HttpStatus.OK.value()).build());
		} else {
			return ResponseEntity.ok(
					Response.builder().timeStamp(Instant.now()).message("The client with id:" + id + " does not exist")
							.status(HttpStatus.BAD_REQUEST).statusCode(HttpStatus.BAD_REQUEST.value()).build());
		}
	}

//	DELETE
	@DeleteMapping(value = "/{id}")
	public ResponseEntity<Response> deleteClient(@PathVariable("id") Long id) {
		if (serviceImp.exist(id)) {
			return ResponseEntity.ok(Response.builder().timeStamp(Instant.now())
					.data(Map.of("client", serviceImp.delete(id))).message("Delete client with id: " + id)
					.status(HttpStatus.OK).statusCode(HttpStatus.OK.value()).build());
		} else {
			return ResponseEntity
					.ok(Response.builder().timeStamp(Instant.now()).message("The client " + id + " does not exist")
							.status(HttpStatus.BAD_REQUEST).statusCode(HttpStatus.BAD_REQUEST.value()).build());
		}
	}

//	SEARCH BY NAME
	@GetMapping(value = "/{name}")
	public ResponseEntity<Response> getClientByName(@PathVariable("name") String name) {

		if (serviceImp.findByName(name) != null) {
			return ResponseEntity.ok(Response.builder().timeStamp(Instant.now())
					.data(Map.of("client", serviceImp.findByName(name))).message("Get client by name: " + name)
					.status(HttpStatus.OK).statusCode(HttpStatus.OK.value()).build());

		} else {
			return ResponseEntity.ok(
					Response.builder().timeStamp(Instant.now()).message("The client called" + name + " does not exist")
							.status(HttpStatus.BAD_REQUEST).statusCode(HttpStatus.BAD_REQUEST.value()).build());
		}

	}

//	SEARCH BY EMAIL
	@GetMapping(value = "/email/{email}")
	public ResponseEntity<Response> getClientByEmail(@PathVariable("email") String email) {

		if (serviceImp.findByEmail(email) != null) {
			return ResponseEntity.ok(Response.builder().timeStamp(Instant.now())
					.data(Map.of("client", serviceImp.findByEmail(email))).message("Get client by email: " + email)
					.status(HttpStatus.OK).statusCode(HttpStatus.OK.value()).build());

		} else {
			return ResponseEntity.ok(Response.builder().timeStamp(Instant.now())
					.message("The client with mail: " + email + " does not exist").status(HttpStatus.BAD_REQUEST)
					.statusCode(HttpStatus.BAD_REQUEST.value()).build());
		}

	}

	@GetMapping(value = "/token/refresh")
	public void refreshToken(HttpServletRequest request, HttpServletResponse response) throws IOException {
//		clientRepository = null;
		String authorizationHeader = request.getHeader(AUTHORIZATION);
		if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
			try {
				// Remove the word Bearer, because we just want the token
				String refresh_token = authorizationHeader.substring("Bearer ".length());
				// Reference to the keyValue
				Algorithm algorithm = Algorithm.HMAC256(OperationUtil.keyValue().getBytes());
				JWTVerifier verifier = JWT.require(algorithm).build();
				DecodedJWT decodeJWT = verifier.verify(refresh_token);
				String username = decodeJWT.getSubject();
				if (serviceImp.findbyUsername(username) != null) {
					Client client = serviceImp.findbyUsername(username);

					String access_token = JWT.create().withSubject(client.getUsername())
							.withExpiresAt(new Date(System.currentTimeMillis() + 10 * 60 * 1000))
							.withIssuer(request.getRequestURL().toString()).withClaim("roles", "ROLE_CLIENT")
							.sign(algorithm);

					Map<String, String> tokens = new HashMap<>();
					tokens.put("access_token", access_token);
					tokens.put("refresh_token", refresh_token);
					response.setContentType(MimeTypeUtils.APPLICATION_JSON_VALUE);
					new ObjectMapper().writeValue(response.getOutputStream(), tokens);
				}else {
					log.error("Error username not found");
				}

			} catch (Exception e) {
				log.error("Error logging in AuthorizationFilter: " + e.getMessage());
				response.setHeader("Error", e.getMessage());
				response.setStatus(403);
				Map<String, String> error = new HashMap<>();
				error.put("error_message", e.getMessage());
				response.setContentType(MimeTypeUtils.APPLICATION_JSON_VALUE);
				new ObjectMapper().writeValue(response.getOutputStream(), error);
			}

		} else {
			throw new RuntimeException("Refresh token is missing");
		}
	}
}
