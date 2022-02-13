/**
 * 
 */
package JEstebanC.FastFoodApp.repository;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import JEstebanC.FastFoodApp.model.Category;

/**
 * @author Juan Esteban Castaño Holguin castanoesteban9@gmail.com 2022-01-24
 */
@Repository
public interface ICategoryRepository extends JpaRepository<Category, Long> {
	Collection<Category> findAllByNameStartsWith(String name);

	Category findByName(String name);
}
