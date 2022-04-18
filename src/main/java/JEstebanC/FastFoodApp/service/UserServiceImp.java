/**
 * 
 */
package JEstebanC.FastFoodApp.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;

import JEstebanC.FastFoodApp.dto.UserDTO;
import JEstebanC.FastFoodApp.model.User;
import JEstebanC.FastFoodApp.repository.IUserRepository;
import JEstebanC.FastFoodApp.security.OperationUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * @author Juan Esteban Castaño Holguin castanoesteban9@gmail.com 2022-02-05
 */
@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class UserServiceImp implements IUserService, UserDetailsService {
	@Autowired
	private IUserRepository userRepository;
	private final BCryptPasswordEncoder bCryptPasswordEncoder;
	@Autowired
	private JavaMailSender javaMailSender;
	@Autowired
	private final FileStorageService fileStorageService;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = userRepository.findByUsername(username);
		if (user == null) {
			log.error("User with username: " + username + " not found");
			throw new UsernameNotFoundException("User with username: " + username + " not found");
		} else {
			log.info("User found " + username);
			Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
			authorities.add(new SimpleGrantedAuthority(user.getUserRoles().getAuthority()));
			return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(),
					authorities);
		}
	}

	@Override
	public User create(User user, MultipartFile file) {
		log.info("Saving new user: " + user.getName());
		user.setUrlImage(fileStorageService.uploadAndDownloadFile(file, "userimage"));
		user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
		return userRepository.save(user);
	}

	@Override
	public UserDTO update(Long id, User user, MultipartFile file) {
		log.info("Updating user with id: " + user.getIdUser());
		User userOld = userRepository.findByIdUser(id);
		userOld.setName(user.getName());
		userOld.setUsername(user.getUsername());
		userOld.setPhone(user.getPhone());
		userOld.setEmail(user.getEmail());
		userOld.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
		userOld.setDiscountPoint(user.getDiscountPoint());
		userOld.setUrlImage(fileStorageService.uploadAndDownloadFile(file, "profileimage"));
		userOld.setStatus(user.getStatus());
		return convertirUserToDTO(userRepository.save(userOld));
	}

	public UserDTO updateEmployee(User user, long id) {
		log.info("Updating user with id: " + id);
		User userOld = userRepository.findByIdUser(id);
		userOld.setPhone(user.getPhone());
		userOld.setEmail(user.getEmail());
		return convertirUserToDTO(userRepository.save(userOld));
	}

	public UserDTO updateClient(User user, long id, MultipartFile file) {
		log.info("Updating user with id: " + id);
		User userOld = userRepository.findByIdUser(id);

		userOld.setName(user.getName());
		userOld.setUrlImage(fileStorageService.uploadAndDownloadFile(file, "profileimage"));
		userOld.setPhone(user.getPhone());
		userOld.setEmail(user.getEmail());
		userOld.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
		userOld.setStatus(user.getStatus());
		return convertirUserToDTO(userRepository.save(userOld));
	}

	public String validationToken(String token) {
		try {
			Algorithm algorithm = Algorithm.HMAC256(OperationUtil.keyValue().getBytes());
			JWTVerifier verifier = JWT.require(algorithm).build();
			DecodedJWT decodeJWT = verifier.verify(token);
			String username = decodeJWT.getSubject();
			return username;
		} catch (Exception e) {
			log.error("Error logging in AuthorizationFilter: " + e.getMessage());
		}
		return null;
	}

	public User updatePasswordClient(String username, String password) {
		log.info("Updating password username: " + username);
		User user = userRepository.findByUsername(username);

		user.setPassword(bCryptPasswordEncoder.encode(password));
		return userRepository.save(user);
	}

	@Override
	public Boolean delete(Long iduser) {
		log.info("Deleting the user with id: " + iduser);
		if (userRepository.existsById(iduser)) {
			userRepository.deleteById(iduser);
			return true;
		} else {
			return false;
		}
	}

	public Collection<UserDTO> list(Long page) {
		log.info("List all users");
		return userRepository.list(page * 10).stream().map(this::convertirUserToDTO).collect(Collectors.toList());
	}

	private UserDTO convertirUserToDTO(User user) {
		UserDTO userDto = new UserDTO();
		userDto.setIdUser(user.getIdUser());
		userDto.setName(user.getName());
		userDto.setUsername(user.getUsername());
		userDto.setUrlImage(user.getUrlImage());
		userDto.setPhone(user.getPhone());
		userDto.setEmail(user.getEmail());
		userDto.setDiscountPoint(user.getDiscountPoint());
		userDto.setStatus(user.getStatus());
		return userDto;
	}

	@Override
	public Boolean exist(Long iduser) {
		log.info("Searching user by id: " + iduser);
		return userRepository.existsById(iduser);
	}

	public Collection<UserDTO> findByName(String name) {
		log.info("Searching user by name: " + name);

		return userRepository.findByNameStartsWith(name).stream().map(this::convertirUserToDTO)
				.collect(Collectors.toList());

	}

	public Boolean findByIdUser(String username, long id) {
		log.info("Searching user by username: " + username);
		User user = userRepository.findByUsername(username);

		return user != null && user.getIdUser() == id ? true : false;
	}

	public UserDTO findByEmail(String email) {
		log.info("Searching user by email: " + email);
		if (userRepository.findByEmail(email) != null) {
			return convertirUserToDTO(userRepository.findByEmail(email));
		}
		return null;

	}

	public User findByUsername(String username) {
		log.info("Searching user by username: " + username);
		return userRepository.findByUsername(username) != null ? userRepository.findByUsername(username) : null;

	}

	public Boolean sendMail(HttpServletRequest request, HttpServletResponse response, String email, String userName,
			String name) {

		// Reference to the keyValue
		Algorithm algorithm = Algorithm.HMAC256(OperationUtil.keyValue().getBytes());
		String token = JWT.create().withSubject(userName)
				// give 10 minutes for the token to expire
				.withExpiresAt(new Date(System.currentTimeMillis() + 20 * 60 * 1000))
				.withIssuer(request.getRequestURL().toString()).sign(algorithm);

		SimpleMailMessage message = new SimpleMailMessage();
		message.setTo(email);

		message.setSubject("Burger App");
		String html = "Hola " + name + "! \n" + "¿olvidaste tu contraseña? \n"
				+ "Recibimos una petición para restablecer tu contraseña\n"
				+ "Para restablecer tu contraseña por favor presiona clic en el siguiente enlace \n"
				+ "http://localhost:8081/api/v1/reset-password?token=" + token;
		message.setText(html);
		javaMailSender.send(message);
		return true;
	}

}
