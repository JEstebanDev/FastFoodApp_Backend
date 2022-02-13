/**
 * 
 */
package JEstebanC.FastFoodApp.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.MimeTypeUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import JEstebanC.FastFoodApp.model.User;
import JEstebanC.FastFoodApp.security.OperationUtil;
import JEstebanC.FastFoodApp.service.UserServiceImp;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.fasterxml.jackson.core.exc.StreamWriteException;
import com.fasterxml.jackson.databind.DatabindException;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * @author Juan Esteban Castaño Holguin castanoesteban9@gmail.com 2022-02-05
 */
@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/token-refresh")
public class RefreshTokenController {

	@Autowired
	private final UserServiceImp serviceImp;
	@GetMapping()
	public void refreshToken(HttpServletRequest request, HttpServletResponse response) throws StreamWriteException, DatabindException, IOException {
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
				if (serviceImp.findByUsername(username) != null) {
					User user = serviceImp.findByUsername(username);

					String access_token = JWT.create().withSubject(user.getUsername())
							.withExpiresAt(new Date(System.currentTimeMillis() + 15 * 60 * 1000))
							.withIssuer(request.getRequestURL().toString())
							.withClaim("roles",user.getUserRoles().getAuthority())
							.sign(algorithm);

					Map<String, String> tokens = new HashMap<>();
					tokens.put("access_token", access_token);
					tokens.put("refresh_token", refresh_token);
					response.setContentType(MimeTypeUtils.APPLICATION_JSON_VALUE);
					new ObjectMapper().writeValue(response.getOutputStream(), tokens);
				} else {
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
