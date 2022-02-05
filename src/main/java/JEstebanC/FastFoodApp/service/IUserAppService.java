/**
 * 
 */
package JEstebanC.FastFoodApp.service;

import java.util.Collection;

import JEstebanC.FastFoodApp.model.Role;
import JEstebanC.FastFoodApp.model.UserApp;

/**
 * @author Juan Esteban Castaño Holguin castanoesteban9@gmail.com 2022-02-01
 */
public interface IUserAppService {

	UserApp create(UserApp userApp);

	UserApp update(UserApp userApp);

	Boolean delete(Long idUserApp);

	Collection<UserApp> list();

	Boolean addRoleToUserApp(Long idUser, Role role);

	Boolean exist(Long idUserApp);
}
