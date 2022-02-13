/**
 * 
 */
package JEstebanC.FastFoodApp.service;

import JEstebanC.FastFoodApp.dto.UserDTO;
import JEstebanC.FastFoodApp.model.User;

/**
 * @author Juan Esteban Castaño Holguin castanoesteban9@gmail.com 2022-01-26
 */
public interface IUserService {

	User create(User user);

	UserDTO update(User user);

	Boolean delete(Long idUser);

	Boolean exist(Long idUser);

}
