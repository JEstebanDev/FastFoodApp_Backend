/**
 * 
 */
package JEstebanC.FastFoodApp.service;

import java.util.Collection;

import JEstebanC.FastFoodApp.model.CategoryIngredient;

/**
 * @author Juan Esteban Castaño Holguin castanoesteban9@gmail.com 2022-01-23
 */
public interface ICategoryIngredientService {

    CategoryIngredient create(CategoryIngredient categoryIngredient);

    CategoryIngredient update(CategoryIngredient categoryIngredient);

    Boolean delete(Long idCategoryIngredient);

    Collection<CategoryIngredient> list();

    Boolean exist(Long idCategoryIngredient);

}
