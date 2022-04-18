/**
 * 
 */
package JEstebanC.FastFoodApp.service;

import java.util.Collection;

import org.springframework.web.multipart.MultipartFile;

import JEstebanC.FastFoodApp.model.Additional;

/**
 * @author Juan Esteban Castaño Holguin castanoesteban9@gmail.com 2022-01-25
 */
public interface IAdditionalService {

	Additional create(Additional additional, MultipartFile file);

	Additional update(Long id,Additional additional,MultipartFile file);

	Boolean delete(Long id_Additional);

	Collection<Additional> list();

	Boolean exist(Long id_Additional);
}
