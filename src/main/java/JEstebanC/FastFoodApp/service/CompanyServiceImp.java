package JEstebanC.FastFoodApp.service;

import JEstebanC.FastFoodApp.model.Company;
import JEstebanC.FastFoodApp.repository.ICompanyRepository;
import JEstebanC.FastFoodApp.service.interfaces.ICompanyService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.io.IOException;
import java.util.Collection;

/**
 * @author Juan Esteban Castaño Holguin castanoesteban9@gmail.com 5/28/2022
 */
@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class CompanyServiceImp implements ICompanyService {

    @Autowired
    private final ICompanyRepository companyRepository;
    @Autowired
    private final CloudinaryService cloudinaryService;
    @Override
    public Company create(Company company, MultipartFile file) {
        log.info("Saving company: " + company.getName());
        if (file != null) {
            try {
                company.setUrlImage(cloudinaryService.upload(file, "company"));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return companyRepository.save(company);
    }

    @Override
    public Collection<Company> list() {
        log.info("List company");
        return companyRepository.findAll();
    }

    @Override
    public Company update(Long idCompany, Company company, MultipartFile file) {
        log.info("Updating company: " + company.getName());
        company.setIdCompany(idCompany);
        if (file != null) {
            try {
                company.setUrlImage(cloudinaryService.upload(file, "company"));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if(company.getUrlImage()==null && file == null){
            company.setUrlImage(null);
        }
        return companyRepository.save(company);
    }

    @Override
    public Boolean delete(Long idCompany) {
        log.info("Deleting company id: " + idCompany);
        companyRepository.deleteById(idCompany);
        return true;
    }

    @Override
    public Boolean exist(Long idCompany) {
        log.info("Searching company by id: " + idCompany);
        return companyRepository.existsById(idCompany);
    }
}
