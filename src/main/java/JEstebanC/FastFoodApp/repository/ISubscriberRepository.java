/**
 * 
 */
package JEstebanC.FastFoodApp.repository;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import JEstebanC.FastFoodApp.model.Subscriber;

/**
 * @author Juan Esteban Castaño Holguin
 * castanoesteban9@gmail.com
 * 2022-03-11
 */
@Repository
public interface ISubscriberRepository extends JpaRepository<Subscriber, Long>  {
	
	@Query(value = "SELECT * FROM subscriber order by email OFFSET ? ROWS FETCH NEXT 10 ROWS ONLY", nativeQuery = true)
	Collection<Subscriber> list(Long page);
	
	Collection<Subscriber> findByEmailStartsWith(String email);
	
	Subscriber findByEmail(String email);

}
