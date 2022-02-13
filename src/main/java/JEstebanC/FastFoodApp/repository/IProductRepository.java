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
	Collection<Product> findByNameStartsWith(String name);

	@Query(value = "SELECT * FROM product order by name OFFSET ? ROWS FETCH NEXT 10 ROWS ONLY", nativeQuery = true)
	Collection<Product> list(Long page);
	
	@Query(value = "SELECT pro.* FROM product pro join category ca on ca.id_category=pro.id_category  WHERE ca.name = ?", nativeQuery = true)
	Collection<Product> findByNameCategory(String name);
	
//	@Query(value = "SELECT * FROM product WHERE id_product=?", nativeQuery = true)
	Product findByIdProduct(Long idProduct);
}
