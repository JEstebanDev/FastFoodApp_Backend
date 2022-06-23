package JEstebanC.FastFoodApp.service;

import JEstebanC.FastFoodApp.model.Category;
import JEstebanC.FastFoodApp.repository.ICategoryRepository;
import JEstebanC.FastFoodApp.service.interfaces.ICategoryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.io.IOException;
import java.util.Collection;

/**
 * @author Juan Esteban Castaño Holguin castanoesteban9@gmail.com 2022-01-24
 */
@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class CategoryServiceImp implements ICategoryService {

    @Autowired
    private final ICategoryRepository categoryRepository;
    @Autowired
    private final CloudinaryService cloudinaryService;

    @Override
    public Category create(Category category, MultipartFile file) {
        log.info("Saving new category: " + category.getName());
        if (file != null) {
            try {
                category.setImageUrl(cloudinaryService.upload(file, "category"));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return categoryRepository.save(category);
    }

    @Override
    public Category update(Long id, Category category, MultipartFile file) {
        log.info("Updating category with id: " + id);
        if(id == null){
            throw new IllegalArgumentException("Id cannot be null");
        }
        category.setIdCategory(id);
        if (file != null) {
            try {
                category.setImageUrl(cloudinaryService.upload(file, "category"));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if(category.getImageUrl()==null && file == null){
            category.setImageUrl(null);
        }
        return categoryRepository.save(category);
    }

    @Override
    public Boolean delete(Long idCategory) {
        log.info("Deleting the category with id: " + idCategory);
        categoryRepository.deleteById(idCategory);
        return true;
    }

    @Override
    public Collection<Category> list() {
        log.info("List all categories");
        return categoryRepository.findAll();
    }

    @Override
    public Boolean exist(Long idCategory) {
        log.info("Searching category by id: " + idCategory);
        return categoryRepository.existsById(idCategory);
    }

    public Collection<Category> findByName(String name) {
        log.info("Searching category by name: " + name);
        return categoryRepository.findAllByNameStartsWith(name);
    }
}
