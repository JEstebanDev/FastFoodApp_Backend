package JEstebanC.FastFoodApp.service;

import java.util.Collection;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import JEstebanC.FastFoodApp.model.Role;
import JEstebanC.FastFoodApp.repository.IRoleRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * @author Juan Esteban Castaño Holguin castanoesteban9@gmail.com 2022-02-01
 */
@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class RoleServiceImp implements IRoleService {

	@Autowired
	private final IRoleRepository roleRepository;

	@Override
	public Role create(Role role) {
		log.info("Saving new role with id: " + role.getIdRol());
		return roleRepository.save(role);
	}

	@Override
	public Role update(Role role) {
		log.info("Updating role with id: " + role.getIdRol());
		return roleRepository.save(role);
	}

	@Override
	public Boolean delete(Long idRole) {
		if (roleRepository.existsById(idRole)) {
			log.info("Delete role with id: " + idRole);
			roleRepository.deleteById(idRole);
			return true;
		} else {
			return false;
		}
	}

	@Override
	public Collection<Role> list() {
		log.info("List all roles");
		return roleRepository.findAll();
	}

	@Override
	public Boolean exist(Long idRole) {
		log.info("Searching role by id: " + idRole);
		return roleRepository.existsById(idRole) ? true : false;
	}

	public Collection<Role> findByDescription(String description) {
		log.info("Searching role by name: " + description);
		return roleRepository.findByDescription(description);
	}

}
