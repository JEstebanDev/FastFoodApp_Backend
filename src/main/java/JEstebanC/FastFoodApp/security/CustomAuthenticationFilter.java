
package JEstebanC.FastFoodApp.security;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.util.MimeTypeUtils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.fasterxml.jackson.databind.ObjectMapper;

import JEstebanC.FastFoodApp.model.Response;

/**
 * @author Juan Esteban Castaño Holguin castanoesteban9@gmail.com 2022-02-02
 */
public class CustomAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

	private final AuthenticationManager authenticationManager;

	public CustomAuthenticationFilter(AuthenticationManager authenticationManager) {
		this.authenticationManager = authenticationManager;
	}

	@Override
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
			throws AuthenticationException {

		String username = request.getParameter("username");
		String password = request.getParameter("password");
		UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(username,
				password);

		return authenticationManager.authenticate(authenticationToken);
	}

	@Override
	protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
			Authentication authentication) throws IOException, ServletException {
		User user = (User) authentication.getPrincipal();
		// Reference to the keyValue
		Algorithm algorithm = Algorithm.HMAC256(OperationUtil.keyValue().getBytes());
		String access_token = JWT.create().withSubject(user.getUsername())
				// give 10 minutes for the token to expire
				.withExpiresAt(new Date(System.currentTimeMillis() + 10 * 60 * 1000))
				.withIssuer(request.getRequestURL().toString())
				.withClaim("roles",
						user.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList()))
				.sign(algorithm);

		String refresh_token = JWT.create().withSubject(user.getUsername())
				// give 30 minutes for the token to expire
				.withExpiresAt(new Date(System.currentTimeMillis() + 360 * 60 * 1000))
//				.withExpiresAt(new Date(System.currentTimeMillis() + 30 * 60 * 1000))
				.withIssuer(request.getRequestURL().toString())
				.withClaim("roles",
						user.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList()))
				.sign(algorithm);

		Map<String, Object> tokens = new HashMap<>();
		tokens.put("valid", true);
		tokens.put("access_token", access_token);
		tokens.put("refresh_token", refresh_token);
		response.setStatus(200);
		response.setContentType(MimeTypeUtils.APPLICATION_JSON_VALUE);
		new ObjectMapper().writeValue(response.getOutputStream(),Response.builder()
				.data(Map.of("tokens", tokens))
				.message("tokens").status(HttpStatus.OK).statusCode(HttpStatus.OK.value()).build());

	}

}