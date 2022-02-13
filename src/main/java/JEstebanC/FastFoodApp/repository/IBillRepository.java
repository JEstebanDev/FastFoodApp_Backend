/**
 * 
 */
package JEstebanC.FastFoodApp.repository;


import java.util.Collection;
import java.util.Date;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import JEstebanC.FastFoodApp.model.Bill;

/**
 * @author Juan Esteban Castaño Holguin castanoesteban9@gmail.com 2022-01-26
 */
@Repository
public interface IBillRepository extends JpaRepository<Bill, Long> {

	@Query(value = "SELECT * FROM bill where id_user = ?", nativeQuery = true)
	Collection<Bill> findByIdUser(Long idUser);
	
	@Query(value = "SELECT * FROM bill where id_user = ?", nativeQuery = true)
	Bill findOneByIdUser(Long idUser);
	
	Collection<Bill> findByDateBetween(Date startDate,Date endDate);
	
	Bill findByIdBill(Long idBill);
	
}
