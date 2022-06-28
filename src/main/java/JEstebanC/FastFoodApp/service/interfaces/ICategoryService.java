/**
 * 
 */
package JEstebanC.FastFoodApp.service.interfaces;

import java.util.Collection;

import org.springframework.web.multipart.MultipartFile;

import JEstebanC.FastFoodApp.model.Category;

/**
 * @author Juan Esteban Castaño Holguin castanoesteban9@gmail.com 2022-01-23
 */
public interface ICategoryService {

    Category create(Category category, MultipartFile file);

    Category update(Long id,Category category, MultipartFile file);

    Boolean delete(Long idCategory);

    Collection<Category> list();

    Collection<Category> listCategoriesWithProducts();

    Boolean exist(Long idCategory);

}
