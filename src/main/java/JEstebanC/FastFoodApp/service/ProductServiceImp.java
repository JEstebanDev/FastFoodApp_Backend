/**
 *
 */
package JEstebanC.FastFoodApp.service;

import JEstebanC.FastFoodApp.model.PriceProductHistory;
import JEstebanC.FastFoodApp.model.Product;
import JEstebanC.FastFoodApp.repository.ICategoryRepository;
import JEstebanC.FastFoodApp.repository.IPriceProductHistory;
import JEstebanC.FastFoodApp.repository.IProductRepository;
import JEstebanC.FastFoodApp.service.interfaces.IProductService;
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
    private final IPriceProductHistory priceProductHistory;
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

                Product savedProduct = productRepository.save(product);
                //this is for the product's price history
                addPriceHistory(savedProduct);
                return savedProduct;
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
                if (product.getImageUrl() == null && file == null) {
                    product.setImageUrl(null);
                }
                //this validation is for the priceHistory when the price change, it save the new price
                Product checkPriceProduct = productRepository.findById(id).get();
                if (checkPriceProduct.getPrice() != product.getPrice()) {
                    addPriceHistory(product);
                }
                return productRepository.save(product);
            }
        }
        return null;
    }

    private void addPriceHistory(Product product) {
        log.info("Adding price product history");
        PriceProductHistory productHistory = new PriceProductHistory();
        productHistory.setProduct(product);
        productHistory.setPrice(product.getPrice());
        productHistory.setCreationDate(Date.from(Instant.now()));
        priceProductHistory.save(productHistory);
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

    public Collection<Product> findByNameCategoryAdmin(String name) {
        log.info("Searching product by category-admin: " + name);
        try {
            if (categoryRepository.findByName(name).getIdCategory() != null) {
                return productRepository.findByNameCategoryAdmin(categoryRepository.findByName(name).getName());
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
