/**
 * 
 */
package JEstebanC.FastFoodApp.repository;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import JEstebanC.FastFoodApp.model.Client;

/**
 * @author Juan Esteban Castaño Holguin castanoesteban9@gmail.com 2022-01-26
 */
@Repository
public interface IClientRepository extends JpaRepository<Client, Long> {
	@Query(value = "SELECT * FROM client WHERE name like ?%", nativeQuery = true)
	Collection<Client> findByName(String name);
	
	@Query(value = "SELECT * FROM client WHERE username = ?", nativeQuery = true)
	Client findbyUsername(String username);
	
	Client findByEmail(String email);

}
