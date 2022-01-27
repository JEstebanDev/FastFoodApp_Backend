/**
 * 
 */
package JEstebanC.FastFoodApp.service;

import java.util.Collection;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import JEstebanC.FastFoodApp.model.Product;
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

	@Override
	public Product create(Product product) {
		log.info("Saving new product: " + product.getName());
		return productRepository.save(product);
	}

	@Override
	public Product update(Product product) {
		log.info("Updating product with id: " + product.getIdProduct());
		return productRepository.save(product);
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

	public Product findByName(String name) {
		log.info("Searching product by name: " + name);
		return productRepository.findByName(name) != null ? productRepository.findByName(name) : null;
	}

	@Override
	public Boolean exist(Long idProduct) {
		log.info("Searching product by id: " + idProduct);
		return productRepository.existsById(idProduct);
	}

}
