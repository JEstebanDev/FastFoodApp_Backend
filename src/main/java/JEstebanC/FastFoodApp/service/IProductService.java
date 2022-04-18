/**
 * 
 */
package JEstebanC.FastFoodApp.service;
import org.springframework.web.multipart.MultipartFile;

import JEstebanC.FastFoodApp.model.Product;

/**
 * @author Juan Esteban Castaño Holguin castanoesteban9@gmail.com 2022-01-23
 */
public interface IProductService {

	Product create(Product product, MultipartFile file);

	Product update(Long id, Product product, MultipartFile file);

	Boolean delete(Long idProduct);

	Boolean exist(Long idProduct);

}
