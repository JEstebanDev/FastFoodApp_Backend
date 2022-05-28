package JEstebanC.FastFoodApp.repository;

import JEstebanC.FastFoodApp.model.Company;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author Juan Esteban Castaño Holguin castanoesteban9@gmail.com 5/28/2022
 */
@Repository
public interface ICompanyRepository  extends JpaRepository<Company, Long> {



}
