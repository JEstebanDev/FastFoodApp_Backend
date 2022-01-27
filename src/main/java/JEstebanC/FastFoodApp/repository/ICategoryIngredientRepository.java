/**
 * 
 */
package JEstebanC.FastFoodApp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

import JEstebanC.FastFoodApp.model.CategoryIngredient;

/**
 * @author Juan Esteban Castaño Holguin
 * castanoesteban9@gmail.com
 * 2022-01-24
 */
@Component
public interface ICategoryIngredientRepository extends JpaRepository<CategoryIngredient, Long>{
	CategoryIngredient findByName(String name);
}
