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
	Collection<User> findByNameStartsWith(String name);
	
	@Query(value = "select * from user_app order by name OFFSET ? ROWS FETCH NEXT 10 ROWS ONLY", nativeQuery = true)
	Collection<User> list(Long page);
	
	User findByIdUser(Long idUser);
	
	User findByUsername(String username);
	
	User findByEmail(String email);

}
