/**
 * 
 */
package JEstebanC.FastFoodApp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

import JEstebanC.FastFoodApp.model.Product_Ingredient;

/**
 * @author Juan Esteban Castaño Holguin castanoesteban9@gmail.com 2022-01-25
 */
@Component
public interface IProductIngredientRepository extends JpaRepository<Product_Ingredient, Long> {

}
