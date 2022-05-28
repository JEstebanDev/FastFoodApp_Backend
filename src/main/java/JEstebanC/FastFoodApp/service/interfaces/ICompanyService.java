package JEstebanC.FastFoodApp.service.interfaces;

import JEstebanC.FastFoodApp.model.Company;

import java.util.Collection;

/**
 * @author Juan Esteban Castaño Holguin castanoesteban9@gmail.com 5/28/2022
 */
public interface ICompanyService {

    Company create(Company company);

    Collection<Company> list();

    Company update(Long idCompany, Company company);

    Boolean delete(Long idCompany);

    Boolean exist(Long idCompany);

}
