/**
 * 
 */
package JEstebanC.FastFoodApp.service;


import java.util.ArrayList;
import java.util.Collection;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

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
			authorities.add(new SimpleGrantedAuthority(user.getRole().getDescription()));
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
	public User update(User user) {
		log.info("Updating user with id: " + user.getIdUser());
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

	@Override
	public Collection<User> list() {
		log.info("List all users");
		return userRepository.findAll();
	}

	@Override
	public Boolean exist(Long iduser) {
		log.info("Searching user by id: " + iduser);
		return userRepository.existsById(iduser);
	}

	public Collection<User> findByName(String name) {
		log.info("Searching user by name: " + name);
		
		userRepository.findByName(name);
		return userRepository.findByName(name);
	}

	public User findByEmail(String email) {
		log.info("Searching user by email: " + email);
		return userRepository.findByEmail(email);
	}

	public User findByUsername(String username) {
		log.info("Searching client by username: " + username);
		return userRepository.findByUsername(username) != null ? userRepository.findByUsername(username) : null;
	}
}
