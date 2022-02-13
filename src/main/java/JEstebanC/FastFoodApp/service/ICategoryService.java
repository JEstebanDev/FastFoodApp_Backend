/**
 * 
 */
package JEstebanC.FastFoodApp.service;

import java.util.Collection;

import JEstebanC.FastFoodApp.model.Category;

/**
 * @author Juan Esteban Castaño Holguin castanoesteban9@gmail.com 2022-01-23
 */
public interface ICategoryService {

    Category create(Category category);

    Category update(Long id,Category category);

    Boolean delete(Long idCategory);

    Collection<Category> list();

    Boolean exist(Long idCategory);

}
