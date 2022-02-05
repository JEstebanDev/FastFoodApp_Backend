/**
 * 
 */
package JEstebanC.FastFoodApp.service;

import java.util.Collection;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import JEstebanC.FastFoodApp.model.Role;
import JEstebanC.FastFoodApp.model.UserApp;
import JEstebanC.FastFoodApp.repository.IRoleRepository;
import JEstebanC.FastFoodApp.repository.IUserAppRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * @author Juan Esteban Castaño Holguin castanoesteban9@gmail.com 2022-02-01
 */
@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class UserAppServiceImp implements IUserAppService {

	@Autowired
	private final IUserAppRepository userAppRepository;
	@Autowired
	private final IRoleRepository roleRepository;

	@Override
	public UserApp create(UserApp userApp) {
		log.info("Saving new userApp with id: " + userApp.getIdUserApp());
		return userAppRepository.save(userApp);
	}

	@Override
	public Boolean addRoleToUserApp(Long idUser, Role role) {
		if (roleRepository.existsById(role.getIdRol())) {
			Role newRole = roleRepository.findByIdRol(role.getIdRol());
			userAppRepository.getById(idUser).getRole().add(newRole);
			return true;
		} else {
			return false;
		}
	}

	@Override
	public UserApp update(UserApp userApp) {
		log.info("Updating userApp with id: " + userApp.getIdUserApp());
		return userAppRepository.save(userApp);
	}

	@Override
	public Boolean delete(Long idUserApp) {
		if (userAppRepository.existsById(idUserApp)) {
			log.info("Delete userApp with id: " + idUserApp);
			userAppRepository.deleteById(idUserApp);
			return true;
		} else {
			return false;
		}
	}

	@Override
	public Collection<UserApp> list() {
		log.info("List all userApp");
		return userAppRepository.findAll();
	}

	@Override
	public Boolean exist(Long idUserApp) {
		log.info("Searching userApp by id: " + idUserApp);
		return userAppRepository.existsById(idUserApp) ? true : false;
	}

	public Collection<UserApp> findByUserName(String name) {
		log.info("Searching userApp by name: " + name);
		return userAppRepository.findByUserName(name);
	}

}
