/**
 * 
 */
package JEstebanC.FastFoodApp.controller;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;

import java.time.Instant;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
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

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;

import JEstebanC.FastFoodApp.model.User;
import JEstebanC.FastFoodApp.security.OperationUtil;
import JEstebanC.FastFoodApp.model.Response;
import JEstebanC.FastFoodApp.service.UserServiceImp;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * @author Juan Esteban Castaño Holguin castanoesteban9@gmail.com 2022-01-26
 */
@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {

	@Autowired
	private final UserServiceImp serviceImp;

//	CREATE
	@PostMapping()
	public ResponseEntity<Response> saveUser(@RequestBody @Valid User user) {
		if (serviceImp.findByUsername(user.getUsername()) == null) {
			return ResponseEntity
					.ok(Response.builder().timeStamp(Instant.now()).data(Map.of("user", serviceImp.create(user)))
							.message("Create user").status(HttpStatus.OK).statusCode(HttpStatus.OK.value()).build());
		} else {
			return ResponseEntity.ok(Response.builder().timeStamp(Instant.now())
					.message("The username: " + user.getUsername() + " already exist").status(HttpStatus.BAD_REQUEST)
					.statusCode(HttpStatus.BAD_REQUEST.value()).build());
		}

	}

	@PreAuthorize("hasRole('ROLE_ADMIN') OR hasRole('ROLE_EMPLOYEE')")
//  READ
	@GetMapping(value = "/list/{page}")
	public ResponseEntity<Response> getUser(@PathVariable("page") Long page) {
		return ResponseEntity.ok(Response.builder().timeStamp(Instant.now()).data(Map.of("user", serviceImp.list(page)))
				.message("List users").status(HttpStatus.OK).statusCode(HttpStatus.OK.value()).build());
	}

//	UPDATE
	@PreAuthorize("hasRole('ROLE_ADMIN') OR hasRole('ROLE_EMPLOYEE') OR hasRole('ROLE_CLIENT')")
	@PutMapping(value = "/{id}")
	public ResponseEntity<Response> updateUser(@PathVariable("id") Long id, @RequestBody @Valid User user,
			HttpServletRequest request) {
		return actionForRole(id, user, request);
	}

//	DELETE
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@DeleteMapping(value = "/{id}")
	public ResponseEntity<Response> deleteUser(@PathVariable("id") Long id) {
		if (serviceImp.exist(id)) {
			return ResponseEntity.ok(Response.builder().timeStamp(Instant.now())
					.data(Map.of("user", serviceImp.delete(id))).message("Delete user with id: " + id)
					.status(HttpStatus.OK).statusCode(HttpStatus.OK.value()).build());
		} else {
			return ResponseEntity
					.ok(Response.builder().timeStamp(Instant.now()).message("The user " + id + " does not exist")
							.status(HttpStatus.BAD_REQUEST).statusCode(HttpStatus.BAD_REQUEST.value()).build());
		}
	}

//	SEARCH BY NAME
	@PreAuthorize("hasRole('ROLE_ADMIN') OR hasRole('ROLE_EMPLOYEE')")
	@GetMapping(value = "/{name}")
	public ResponseEntity<Response> getUserByName(@PathVariable("name") String name) {

		if (serviceImp.findByName(name) != null) {
			return ResponseEntity.ok(Response.builder().timeStamp(Instant.now())
					.data(Map.of("User", serviceImp.findByName(name))).message("Get user by name: " + name)
					.status(HttpStatus.OK).statusCode(HttpStatus.OK.value()).build());

		} else {
			return ResponseEntity.ok(
					Response.builder().timeStamp(Instant.now()).message("The user called" + name + " does not exist")
							.status(HttpStatus.BAD_REQUEST).statusCode(HttpStatus.BAD_REQUEST.value()).build());
		}
	}

//	SEARCH BY EMAIL
	@PreAuthorize("hasRole('ROLE_ADMIN') OR hasRole('ROLE_EMPLOYEE')")
	@GetMapping(value = "/email/{email}")
	public ResponseEntity<Response> getUserByEmail(@PathVariable("email") String email) {
		if (serviceImp.findByEmail(email) != null) {
			return ResponseEntity.ok(Response.builder().timeStamp(Instant.now())
					.data(Map.of("User", serviceImp.findByEmail(email))).message("Get user by email: " + email)
					.status(HttpStatus.OK).statusCode(HttpStatus.OK.value()).build());
		} else {
			return ResponseEntity.ok(Response.builder().timeStamp(Instant.now())
					.message("The User with mail: " + email + " does not exist").status(HttpStatus.BAD_REQUEST)
					.statusCode(HttpStatus.BAD_REQUEST.value()).build());
		}
	}

	private ResponseEntity<Response> actionForRole(Long id, @Valid User user, HttpServletRequest request) {

		if (request.isUserInRole("ROLE_CLIENT")) {
			String authorizationHeader = request.getHeader(AUTHORIZATION);
			if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
				try {
					// Remove the word Bearer, because we just want the token
					String token = authorizationHeader.substring("Bearer ".length());
					// Reference to the keyValue
					Algorithm algorithm = Algorithm.HMAC256(OperationUtil.keyValue().getBytes());
					JWTVerifier verifier = JWT.require(algorithm).build();
					DecodedJWT decodeJWT = verifier.verify(token);
					String username = decodeJWT.getSubject();

					if (serviceImp.exist(id)) {

						if (serviceImp.findByIdUser(username, id)) {
							return ResponseEntity.ok(Response.builder().timeStamp(Instant.now())
									.data(Map.of("user", serviceImp.updateClient(user, id)))
									.message("Update user with id:" + id).status(HttpStatus.OK)
									.statusCode(HttpStatus.OK.value()).build());
						} else {
							return ResponseEntity.ok(Response.builder().timeStamp(Instant.now())
									.message("Does not have permission").status(HttpStatus.BAD_REQUEST)
									.statusCode(HttpStatus.BAD_REQUEST.value()).build());
						}

					} else {
						return ResponseEntity.ok(Response.builder().timeStamp(Instant.now())
								.message("The user with id:" + id + " does not exist").status(HttpStatus.BAD_REQUEST)
								.statusCode(HttpStatus.BAD_REQUEST.value()).build());
					}

				} catch (Exception e) {
					log.error("Error updateOrder: " + e.getMessage());
				}
			}
		} else if (request.isUserInRole("ROLE_EMPLOYEE")) {
			String authorizationHeader = request.getHeader(AUTHORIZATION);
			if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {

				if (serviceImp.exist(id)) {
					return ResponseEntity.ok(Response.builder().timeStamp(Instant.now())
							.data(Map.of("user", serviceImp.updateEmployee(user, id)))
							.message("Update user with id:" + id).status(HttpStatus.OK)
							.statusCode(HttpStatus.OK.value()).build());
				} else {
					return ResponseEntity.ok(Response.builder().timeStamp(Instant.now())
							.message("The user with id:" + id + " does not exist").status(HttpStatus.BAD_REQUEST)
							.statusCode(HttpStatus.BAD_REQUEST.value()).build());
				}
			}
		} else {
			if (serviceImp.exist(id)) {

				return ResponseEntity.ok(Response.builder().timeStamp(Instant.now())
						.data(Map.of("user", serviceImp.update(user))).message("Update user with id:" + id)
						.status(HttpStatus.OK).statusCode(HttpStatus.OK.value()).build());
			} else {
				return ResponseEntity.ok(Response.builder().timeStamp(Instant.now())
						.message("The user with id:" + id + " does not exist").status(HttpStatus.BAD_REQUEST)
						.statusCode(HttpStatus.BAD_REQUEST.value()).build());
			}
		}

		return ResponseEntity.ok(Response.builder().timeStamp(Instant.now()).message("Error with the authorization")
				.status(HttpStatus.BAD_REQUEST).statusCode(HttpStatus.BAD_REQUEST.value()).build());
	}

}
