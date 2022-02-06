/**
 * 
 */
package JEstebanC.FastFoodApp.repository;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import JEstebanC.FastFoodApp.model.User;

/**
 * @author Juan Esteban Castaño Holguin castanoesteban9@gmail.com 2022-01-26
 */
@Repository
public interface IUserRepository extends JpaRepository<User, Long> {
	@Query(value = "SELECT * FROM user_app WHERE name like ?%", nativeQuery = true)
	Collection<User> findByName(String name);
	
	User findByUsername(String username);
	
	User findByEmail(String email);

}
