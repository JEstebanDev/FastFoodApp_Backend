/**
 * 
 */
package JEstebanC.FastFoodApp.service;

import JEstebanC.FastFoodApp.model.Additional;
import JEstebanC.FastFoodApp.model.Product;

/**
 * @author Juan Esteban Castaño Holguin castanoesteban9@gmail.com 2022-01-23
 */
public interface IProductService {

	Product create(Product product);

	Product update(Long id, Product product);

	Boolean delete(Long idProduct);

	Boolean addAdditionalToProduct(Long idProduct, Additional additional);

	Boolean exist(Long idProduct);

}
