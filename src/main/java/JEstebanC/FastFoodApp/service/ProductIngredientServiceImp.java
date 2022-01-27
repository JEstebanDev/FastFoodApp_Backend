/**
 * 
 */
package JEstebanC.FastFoodApp.service;

import java.util.Collection;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import JEstebanC.FastFoodApp.model.Product_Ingredient;
import JEstebanC.FastFoodApp.repository.IProductIngredientRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * @author Juan Esteban Castaño Holguin
 * castanoesteban9@gmail.com
 * 2022-01-25
 */
@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class ProductIngredientServiceImp implements IProduct_Ingredient{

	@Autowired
	private IProductIngredientRepository productIngredientRepository; 
	
	@Override
	public Product_Ingredient create(Product_Ingredient product_Ingredient) {
		log.info("Saving new product ingredient: " + product_Ingredient.getIdProductIngredient());
		return productIngredientRepository.save(product_Ingredient);
	}

	@Override
	public Product_Ingredient update(Product_Ingredient product_Ingredient) {
		log.info("Updating product ingredient with id: " + product_Ingredient.getIdProductIngredient());
		return productIngredientRepository.save(product_Ingredient);
	}

	@Override
	public Boolean delete(Long idProduct_Ingredient) {
		log.info("Deleting the product ingredient with id: " + idProduct_Ingredient);
		if (productIngredientRepository.existsById(idProduct_Ingredient)) {
			productIngredientRepository.deleteById(idProduct_Ingredient);
			return true;
		} else {
			return false;
		}
	}

	@Override
	public Collection<Product_Ingredient> list() {
		log.info("List all product ingredients");
		return productIngredientRepository.findAll();
	}

	@Override
	public Boolean exist(Long idProduct_Ingredient) {
		log.info("Searching product ingredients by id: " + idProduct_Ingredient);
		return productIngredientRepository.existsById(idProduct_Ingredient);
	}

}
