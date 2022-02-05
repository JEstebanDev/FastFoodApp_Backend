/**
 * 
 */
package JEstebanC.FastFoodApp.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import JEstebanC.FastFoodApp.filter.CustomAuthenticationFilter;
import JEstebanC.FastFoodApp.filter.CustomAuthorizationFilter;
import lombok.RequiredArgsConstructor;

/**
 * @author Juan Esteban Castaño Holguin castanoesteban9@gmail.com 2022-02-01
 */
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
@RequiredArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	private final UserDetailsService userDetailsService;

	private final BCryptPasswordEncoder bCryptPasswordEncoder;

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userDetailsService).passwordEncoder(bCryptPasswordEncoder);
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		/*
		 * 1. Disable the use of cookies. 2. Activate CORS configuration with default
		 * values. 3. Disable CSRF filtering 4. Indicate that login does not require
		 * authentication. 5. The rest of the URLs are secured.
		 */
//		CustomAuthenticationFilter customAuthenticationFilter = new CustomAuthenticationFilter(authenticationManagerBean());
//		customAuthenticationFilter.setFilterProcessesUrl("/api/v1/login");
		http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
		http.csrf().disable();
//		http.authorizeRequests().antMatchers(HttpMethod.POST, "/api/v1/login").permitAll().anyRequest();
		http.authorizeRequests().antMatchers("/api/v1/login/**", "/api/v1/client/token/refresh/**").permitAll();
		authorizeRequest(http);
		http.addFilter(new CustomAuthenticationFilter(authenticationManagerBean()));
		http.addFilterBefore(new CustomAuthorizationFilter(), UsernamePasswordAuthenticationFilter.class);
		
	}

	private void authorizeRequest(HttpSecurity http) throws Exception {

		http.authorizeRequests().antMatchers(HttpMethod.GET, "/api/v1/product/**").hasAuthority("ROLE_CLIENT");
		http.authorizeRequests().antMatchers(HttpMethod.GET, "/api/v1/category/**").hasAuthority("ROLE_CLIENT");
		http.authorizeRequests().antMatchers(HttpMethod.GET, "/api/v1/orders/**").hasAuthority("ROLE_CLIENT");
		http.authorizeRequests().antMatchers(HttpMethod.GET, "/api/v1/product/**").hasAuthority("ROLE_CLIENT");
	}

	@Bean
	@Override
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return super.authenticationManagerBean();
	}
}
