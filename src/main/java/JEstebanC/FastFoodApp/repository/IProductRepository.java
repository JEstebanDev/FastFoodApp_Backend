/**
 * 
 */
package JEstebanC.FastFoodApp.repository;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import JEstebanC.FastFoodApp.model.Product;

/**
 * @author Juan Esteban Castaño Holguin castanoesteban9@gmail.com 2022-01-23
 */
@Repository
public interface IProductRepository extends JpaRepository<Product, Long> {
	@Query(value = "SELECT * FROM product where name like ?%  ", nativeQuery = true)
	Collection<Product> findByName(String name);

	@Query(value = "SELECT pro.* FROM product pro join category ca on ca.id_category=pro.id_category  WHERE ca.name = ?", nativeQuery = true)
	Collection<Product> findByNameCategory(String name);
}
