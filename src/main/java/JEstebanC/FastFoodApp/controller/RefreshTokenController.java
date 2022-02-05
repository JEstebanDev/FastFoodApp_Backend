/**
 * 
 */
package JEstebanC.FastFoodApp.controller;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.MimeTypeUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.fasterxml.jackson.databind.ObjectMapper;

import JEstebanC.FastFoodApp.filter.OperationUtil;
import JEstebanC.FastFoodApp.model.Client;
import JEstebanC.FastFoodApp.service.ClientServiceImp;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * @author Juan Esteban Castaño Holguin
 * castanoesteban9@gmail.com
 * 2022-02-05
 */
@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/token-refresh")
public class RefreshTokenController {
	
	@Autowired
	private final ClientServiceImp serviceImp;
	
	@GetMapping()
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
