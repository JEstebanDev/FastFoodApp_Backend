/**
 * 
 */
package JEstebanC.FastFoodApp.service;

import java.util.Collection;

import JEstebanC.FastFoodApp.model.Product_Ingredient;

/**
 * @author Juan Esteban Castaño Holguin castanoesteban9@gmail.com 2022-01-25
 */
public interface IProduct_Ingredient {

	Product_Ingredient create(Product_Ingredient product_Ingredient);

	Product_Ingredient update(Product_Ingredient product_Ingredient);

	Boolean delete(Long idProduct_Ingredient);

	Collection<Product_Ingredient> list();

	Boolean exist(Long idProduct_Ingredient);

}