/**
 *
 */
package JEstebanC.FastFoodApp.service;

import JEstebanC.FastFoodApp.model.Product;
import JEstebanC.FastFoodApp.repository.ICategoryRepository;
import JEstebanC.FastFoodApp.repository.IProductRepository;
import JEstebanC.FastFoodApp.service.interfaces.IProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.io.IOException;
import java.util.Collection;

/**
 * @author Juan Esteban Castaño Holguin castanoesteban9@gmail.com 2022-01-23
 */
@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class ProductServiceImp implements IProductService {

    @Autowired
    private final IProductRepository productRepository;
    @Autowired
    private final ICategoryRepository categoryRepository;
    @Autowired
    private final CloudinaryService cloudinaryService;

    @Override
    public Product create(Product product, MultipartFile file) {
        if (product.getCategory() != null) {
            if (categoryRepository.existsById(product.getCategory().getIdCategory())) {
                log.info("Saving new product: " + product.getName());
                if (file != null) {
                    try {
                        product.setImageUrl(cloudinaryService.upload(file, "product"));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                return productRepository.save(product);
            }
        }
        return null;
    }

    @Override
    public Product update(Long id, Product product, MultipartFile file) {
        log.info("Updating product with id: " + id);
        if (product.getCategory() != null) {
            if (categoryRepository.existsById(product.getCategory().getIdCategory())) {
                product.setIdProduct(id);
                if (file != null) {
                    try {
                        product.setImageUrl(cloudinaryService.upload(file, "product"));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                if(product.getImageUrl()==null && file == null){
                    product.setImageUrl(null);
                }
                return productRepository.save(product);
            }
        }
        return null;
    }

    @Override
    public Boolean delete(Long idProduct) {
        log.info("Deleting the products with id: " + idProduct);
        productRepository.deleteById(idProduct);
        return true;
    }

    public Collection<Product> list(Long page) {
        log.info("List all products");
        return productRepository.list(page * 10);
    }

    public Product findById(Long id) {
        log.info("Searching product by id: " + id);
        return productRepository.findById(id).get();
    }

    public Collection<Product> findByName(String name) {
        log.info("Searching product by name: " + name);
        return productRepository.findByNameStartsWith(name);
    }

    public Collection<Product> findByNameCategory(String name) {
        log.info("Searching product by category: " + name);
        try {
            if (categoryRepository.findByName(name).getIdCategory() != null) {
                return productRepository.findByNameCategory(categoryRepository.findByName(name).getName());
            } else {
                return null;
            }
        } catch (NullPointerException e) {
            return null;
        }
    }

    public Collection<Product> findAllOrderByHighlight() {
        log.info("Searching product order by highlight");
        return productRepository.findAllOrderByHighlight();
    }

    @Override
    public Boolean exist(Long idProduct) {
        log.info("Searching product by id: " + idProduct);
        return productRepository.existsById(idProduct);
    }

}
