/**
 * 
 */
package JEstebanC.FastFoodApp.service;

import java.util.Collection;

import JEstebanC.FastFoodApp.model.Product;

/**
 * @author Juan Esteban Castaño Holguin castanoesteban9@gmail.com 2022-01-23
 */
public interface IProductService {

    Product create(Product product);

    Product update(Product product);

    Boolean delete(Long idProduct);

    Collection<Product> list();

    Boolean exist(Long idProduct);

}
