/**
 * 
 */
package JEstebanC.FastFoodApp.repository;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import JEstebanC.FastFoodApp.model.UserApp;

/**
 * @author Juan Esteban Castaño Holguin
 * castanoesteban9@gmail.com
 * 2022-02-01
 */
@Repository
public interface IUserAppRepository extends JpaRepository<UserApp, Long>{

	Collection<UserApp> findByUserName(String userName);
	
}
