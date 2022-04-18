/**
 * 
 */
package JEstebanC.FastFoodApp.service;

import org.springframework.web.multipart.MultipartFile;

import JEstebanC.FastFoodApp.dto.UserDTO;
import JEstebanC.FastFoodApp.model.User;

/**
 * @author Juan Esteban Castaño Holguin castanoesteban9@gmail.com 2022-01-26
 */
public interface IUserService {

	User create(User user, MultipartFile file);

	UserDTO update(Long id, User user, MultipartFile file);

	UserDTO updateEmployee(User user, long id);

	UserDTO updateClient(User user, long id, MultipartFile file);

	Boolean delete(Long idUser);

	Boolean exist(Long idUser);

}
