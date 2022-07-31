/**
 *
 */
package JEstebanC.FastFoodApp.service;

import JEstebanC.FastFoodApp.model.Additional;
import JEstebanC.FastFoodApp.model.PriceAdditionalHistory;
import JEstebanC.FastFoodApp.repository.IAdditionalRepository;
import JEstebanC.FastFoodApp.repository.IOrdersRepository;
import JEstebanC.FastFoodApp.repository.IPriceAdditionalHistory;
import JEstebanC.FastFoodApp.service.interfaces.IAdditionalService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.io.IOException;
import java.time.Instant;
import java.util.Collection;
import java.util.Date;

/**
 * @author Juan Esteban Castaño Holguin castanoesteban9@gmail.com 2022-01-25
 */
@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class AdditionalServiceImp implements IAdditionalService {

    @Autowired
    private final IAdditionalRepository additionalRepository;
    @Autowired
    private final IPriceAdditionalHistory priceAdditionalHistory;
    @Autowired
    private final IOrdersRepository ordersRepository;
    @Autowired
    private final CloudinaryService cloudinaryService;

    @Override
    public Additional create(Additional additional, MultipartFile file) {
        log.info("Saving new additional: " + additional.getName());
        if (additional.getCategory().size() > 0) {
            if (file != null) {
                try {
                    additional.setImageUrl(cloudinaryService.upload(file, "additional"));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (additional.getImageUrl() == null && file == null) {
                additional.setImageUrl(null);
            }
            Additional savedAdditional = additionalRepository.save(additional);
            //this is for the product's price history
            addPriceHistory(savedAdditional);
            return savedAdditional;
        } else {
            return null;
        }
    }

    @Override
    public Additional update(Long id, Additional additional, MultipartFile file) {
        log.info("Updating additional: " + additional.getName());
        additional.setIdAdditional(id);
        if (additional.getCategory().size() > 0) {
            if (file != null) {
                try {
                    additional.setImageUrl(cloudinaryService.upload(file, "additional"));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (additional.getImageUrl() == null && file == null) {
                additional.setImageUrl(null);
            }
            //this validation is for the priceHistory when the price change, it save the new price
            Additional checkPriceAdditional = additionalRepository.findById(id).get();
            if (checkPriceAdditional.getPrice() != additional.getPrice()) {
                addPriceHistory(additional);
            }
            return additionalRepository.save(additional);
        } else {
            return null;
        }
    }

    private void addPriceHistory(Additional additional) {
        log.info("Adding price product history");
        PriceAdditionalHistory additionalHistory = new PriceAdditionalHistory();
        additionalHistory.setAdditional(additional);
        additionalHistory.setPrice(additional.getPrice());
        additionalHistory.setCreationDate(Date.from(Instant.now()));
        priceAdditionalHistory.save(additionalHistory);
    }

    @Override
    public Boolean delete(Long id_additional) {
        log.info("Deleting the additional id: " + id_additional);
        if (ordersRepository.countIdAdditional(id_additional) != 0) {
            return false;
        }
        priceAdditionalHistory.deleteByIdAdditional(id_additional);
        additionalRepository.deleteById(id_additional);
        return true;
    }

    @Override
    public Collection<Additional> list() {
        log.info("List all additional");
        return additionalRepository.findAll();
    }

    @Override
    public Boolean exist(Long id_additional) {
        log.info("Searching additional by id: " + id_additional);
        return additionalRepository.existsById(id_additional);
    }

    public Collection<Additional> findByName(String name) {
        log.info("Searching additional by name: " + name);
        return additionalRepository.findByNameStartsWith(name);
    }

    public Collection<Additional> findByCategory(Long idCategory) {
        log.info("Searching additional by idCategory: " + idCategory);
        return additionalRepository.findByIdCategory(idCategory);
    }

}
