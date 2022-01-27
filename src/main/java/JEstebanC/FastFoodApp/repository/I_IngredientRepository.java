/**
 * 
 */
package JEstebanC.FastFoodApp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

import JEstebanC.FastFoodApp.model.Ingredient;

/**
 * @author Juan Esteban Castaño Holguin castanoesteban9@gmail.com 2022-01-25
 */
@Component
public interface I_IngredientRepository extends JpaRepository<Ingredient, Long> {
	Ingredient findByName(String name);
}
