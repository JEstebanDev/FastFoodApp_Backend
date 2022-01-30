/**
 * 
 */
package JEstebanC.FastFoodApp.service;

import java.util.Collection;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

	@Override
	public Product create(Product product) {

		if (categoryRepository.existsById(product.getIdCategory().getIdCategory())) {
			log.info("Saving new product: " + product.getName());
			return productRepository.save(product);
		} else {
			return null;
		}
	}

	@Override
	public Product update(Product product) {
		if (categoryRepository.existsById(product.getIdCategory().getIdCategory())) {
			log.info("Updating product with id: " + product.getName());
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

	@Override
	public Collection<Product> list() {
		log.info("List all products");
		return productRepository.findAll();
	}

	public Collection<Product> findByName(String name) {
		log.info("Searching product by name: " + name);
		return productRepository.findByName(name) != null ? productRepository.findByName(name) : null;
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
