/**
 * 
 */
package JEstebanC.FastFoodApp.repository;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import JEstebanC.FastFoodApp.model.Category;

/**
 * @author Juan Esteban Castaño Holguin castanoesteban9@gmail.com 2022-01-24
 */
@Repository
public interface ICategoryRepository extends JpaRepository<Category, Long> {
	Collection<Category> findAllByNameStartsWith(String name);

	@Query(value = "SELECT distinct(Category.*) FROM product Product JOIN category Category ON Category.id_category=Product.id_category WHERE Category.status!=0 ORDER BY Category.id_category", nativeQuery = true)
	Collection<Category> findCategoriesWithProducts();
	Category findByName(String name);
}
