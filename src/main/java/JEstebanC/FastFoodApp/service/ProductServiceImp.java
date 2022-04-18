/**
 * 
 */
package JEstebanC.FastFoodApp.service;

import java.util.Collection;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import JEstebanC.FastFoodApp.model.Product;
import JEstebanC.FastFoodApp.repository.ICategoryRepository;
import JEstebanC.FastFoodApp.repository.IProductRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

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
	private final FileStorageService fileStorageService;
	
	@Override
	public Product create(Product product, MultipartFile file) {

		if (categoryRepository.existsById(product.getCategory().getIdCategory())) {
			log.info("Saving new product: " + product.getName());
			product.setImageUrl(fileStorageService.uploadAndDownloadFile(file, "productimage"));
			return productRepository.save(product);
		} else {
			return null;
		}
	}

	@Override
	public Product update(Long id, Product product, MultipartFile file) {
		if (categoryRepository.existsById(product.getCategory().getIdCategory())) {
			log.info("Updating product with id: " + id);
			Optional<Product> oldProduct = productRepository.findById(id);
			product.setIdProduct(id);
			if (file != null) {
				product.setImageUrl(fileStorageService.uploadAndDownloadFile(file, "productimage"));
			} else {
				product.setImageUrl(oldProduct.get().getImageUrl());
			}
			return productRepository.save(product);
		} else {
			return null;
		}
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

	public Collection<Product> findByName(String name) {
		log.info("Searching product by name: " + name);
		return productRepository.findByNameStartsWith(name) != null ? productRepository.findByNameStartsWith(name)
				: null;
	}

	public Collection<Product> findByNameCategory(String name) {
		log.info("Searching product by category: " + name);
		try {
			if (categoryRepository.findByName(name).getIdCategory() != null) {
				return productRepository.findByNameCategory(categoryRepository.findByName(name).getName().toString());
			} else {
				return null;
			}
		} catch (NullPointerException e) {
			return null;
		}

	}

	@Override
	public Boolean exist(Long idProduct) {
		log.info("Searching product by id: " + idProduct);
		return productRepository.existsById(idProduct);
	}

}
