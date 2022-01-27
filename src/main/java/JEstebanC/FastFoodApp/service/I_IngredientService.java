/**
 * 
 */
package JEstebanC.FastFoodApp.service;

import java.util.Collection;

import JEstebanC.FastFoodApp.model.Ingredient;

/**
 * @author Juan Esteban Castaño Holguin castanoesteban9@gmail.com 2022-01-25
 */
public interface I_IngredientService {

	Ingredient create(Ingredient ingredient);

	Ingredient update(Ingredient ingredient);

	Boolean delete(Long id_Ingredient);

	Collection<Ingredient> list();

	Boolean exist(Long id_Ingredient);
}
