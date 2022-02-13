/**
 * 
 */
package JEstebanC.FastFoodApp.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import JEstebanC.FastFoodApp.dto.UserDTO;
import JEstebanC.FastFoodApp.model.User;
import JEstebanC.FastFoodApp.repository.IUserRepository;
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
	public User create(User user) {
		log.info("Saving new user: " + user.getName());
		user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
		return userRepository.save(user);
	}

	@Override
	public UserDTO update(User user) {
		log.info("Updating user with id: " + user.getIdUser());
		user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
		return convertirUserToDTO(userRepository.save(user));
	}

	public UserDTO updateEmployee(User user, long id) {
		log.info("Updating user with id: " + id);
		User userOld = userRepository.findByIdUser(id);
		userOld.setName(user.getName());
		userOld.setUrlImage(user.getUrlImage());
		userOld.setPhone(user.getPhone());
		userOld.setEmail(user.getEmail());
		userOld.setStatus(user.getStatus());
		return convertirUserToDTO(userRepository.save(userOld));
	}

	public User updateClient(User user, long id) {
		log.info("Updating user with id: " + id);
		User userOld = userRepository.findByIdUser(id);

		userOld.setName(user.getName());
		userOld.setUrlImage(user.getUrlImage());
		userOld.setPhone(user.getPhone());
		userOld.setEmail(user.getEmail());
		userOld.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
		userOld.setStatus(user.getStatus());
		return userRepository.save(userOld);
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

}
