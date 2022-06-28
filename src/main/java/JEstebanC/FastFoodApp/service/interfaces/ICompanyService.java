package JEstebanC.FastFoodApp.service.interfaces;

import JEstebanC.FastFoodApp.model.Company;
import org.springframework.web.multipart.MultipartFile;

import java.util.Collection;

/**
 * @author Juan Esteban Castaño Holguin castanoesteban9@gmail.com 5/28/2022
 */
public interface ICompanyService {

    Company create(Company company, MultipartFile file);

    Collection<Company> list();

    Company update(Long idCompany, Company company, MultipartFile file);

    Boolean delete(Long idCompany);

    Boolean exist(Long idCompany);

}
