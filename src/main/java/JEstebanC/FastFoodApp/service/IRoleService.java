package JEstebanC.FastFoodApp.service;

import java.util.Collection;

import JEstebanC.FastFoodApp.model.Role;

public interface IRoleService {

	Role create(Role role);

	Role update (Role role);
	
	Boolean delete(Long idRole);
	
	Collection<Role> list();
	
	Boolean exist(Long idRole);
	
}
