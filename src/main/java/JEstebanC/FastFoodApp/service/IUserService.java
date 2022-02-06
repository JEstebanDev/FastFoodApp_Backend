/**
 * 
 */
package JEstebanC.FastFoodApp.service;

import java.util.Collection;

import JEstebanC.FastFoodApp.model.User;

/**
 * @author Juan Esteban Castaño Holguin castanoesteban9@gmail.com 2022-01-26
 */
public interface IUserService {

	User create(User idUser);

	User update(User idUser);

	Boolean delete(Long idUser);

	Collection<User> list();

	Boolean exist(Long idUser);

}
