package JEstebanC.FastFoodApp.repository;

import JEstebanC.FastFoodApp.model.Tax;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author Juan Esteban Castaño Holguin castanoesteban9@gmail.com 6/3/2022
 */
@Repository
public interface ITaxRepository extends JpaRepository<Tax, Long> {
}
