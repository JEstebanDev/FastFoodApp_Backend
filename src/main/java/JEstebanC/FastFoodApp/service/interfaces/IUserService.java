/**
 * 
 */
package JEstebanC.FastFoodApp.service.interfaces;

import org.springframework.web.multipart.MultipartFile;

import JEstebanC.FastFoodApp.dto.UserDTO;
import JEstebanC.FastFoodApp.dto.update.UserClientDTO;
import JEstebanC.FastFoodApp.dto.update.UserEmployeeDTO;
import JEstebanC.FastFoodApp.model.User;

/**
 * @author Juan Esteban Castaño Holguin castanoesteban9@gmail.com 2022-01-26
 */
public interface IUserService {

	User create(User user, MultipartFile file);

	UserDTO update(Long id, User user, MultipartFile file);

	UserDTO updateEmployee(UserEmployeeDTO userEmployeeDTO, long id);

	UserDTO updateClient(UserClientDTO userClientDTO, long id, MultipartFile file);

	Boolean delete(Long idUser);

	Boolean exist(Long idUser);

	Boolean exist(String username);

}
