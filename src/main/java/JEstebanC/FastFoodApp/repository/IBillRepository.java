/**
 * 
 */
package JEstebanC.FastFoodApp.repository;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import JEstebanC.FastFoodApp.model.Bill;

/**
 * @author Juan Esteban Castaño Holguin
 * castanoesteban9@gmail.com
 * 2022-01-26
 */
@Repository
public interface IBillRepository extends JpaRepository<Bill, Long>{
	@Query(value = "SELECT * FROM bill where id_client = ?",nativeQuery = true)
	Collection<Bill> findByIdClient(Long idClient);
}
