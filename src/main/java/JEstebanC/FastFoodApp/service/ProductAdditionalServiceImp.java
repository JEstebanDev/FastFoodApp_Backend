/**
 * 
 */
package JEstebanC.FastFoodApp.service;

import java.util.Collection;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import JEstebanC.FastFoodApp.model.Product_Additional;
import JEstebanC.FastFoodApp.repository.IProductAdditionalRepository;
import JEstebanC.FastFoodApp.repository.IProductRepository;
import JEstebanC.FastFoodApp.repository.IAdditionalRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * @author Juan Esteban Castaño Holguin castanoesteban9@gmail.com 2022-01-25
 */
@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class ProductAdditionalServiceImp implements IProduct_Additional {

	@Autowired
	private IProductAdditionalRepository productAdditionalRepository;
	@Autowired
	private IAdditionalRepository additionalRepository;
	@Autowired
	private IProductRepository productRepository;

	@Override
	public Product_Additional create(Product_Additional product_Ingredient) {
		if (additionalRepository.existsById(product_Ingredient.getIdAdditional().getIdAdditional())
				|| productRepository.existsById(product_Ingredient.getIdProduct().getIdProduct())) {
			log.info("Saving new product ingredient: " + product_Ingredient.getIdProductAdditional());
			return productAdditionalRepository.save(product_Ingredient);
		} else {
			return null;
		}
	}

	@Override
	public Product_Additional update(Product_Additional product_Ingredient) {
		if (additionalRepository.existsById(product_Ingredient.getIdAdditional().getIdAdditional())
				|| productRepository.existsById(product_Ingredient.getIdProduct().getIdProduct())) {
			log.info("Updating product ingredient with id: " + product_Ingredient.getIdProductAdditional());
			return productAdditionalRepository.save(product_Ingredient);
		} else {
			return null;
		}
	}

	@Override
	public Boolean delete(Long idProduct_Ingredient) {
		log.info("Deleting the product ingredient with id: " + idProduct_Ingredient);
		if (productAdditionalRepository.existsById(idProduct_Ingredient)) {
			productAdditionalRepository.deleteById(idProduct_Ingredient);
			return true;
		} else {
			return false;
		}
	}

	@Override
	public Collection<Product_Additional> list() {
		log.info("List all product ingredients");
		return productAdditionalRepository.findAll();
	}

	@Override
	public Boolean exist(Long idProduct_Ingredient) {
		log.info("Searching product ingredients by id: " + idProduct_Ingredient);
		return productAdditionalRepository.existsById(idProduct_Ingredient);
	}

	public Collection<Product_Additional> findByIdProduct(Long idProduct) {
		if (productRepository.existsById(idProduct)) {
			log.info("Searching product ingredients by id product: " + idProduct);
			return productAdditionalRepository.findByIdProduct(idProduct);
		} else {
			return null;

		}
	}

}
