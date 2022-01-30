/**
 * 
 */
package JEstebanC.FastFoodApp.service;

import java.util.Collection;

import JEstebanC.FastFoodApp.model.Product_Additional;

/**
 * @author Juan Esteban Castaño Holguin castanoesteban9@gmail.com 2022-01-25
 */
public interface IProduct_Additional {

	Product_Additional create(Product_Additional productAdditional);

	Product_Additional update(Product_Additional productAdditional);

	Boolean delete(Long idproductAdditional);

	Collection<Product_Additional> list();

	Boolean exist(Long idproductAdditional);

}