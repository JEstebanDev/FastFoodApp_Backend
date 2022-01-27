/**
 * 
 */
package JEstebanC.FastFoodApp.service;

import java.util.Collection;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import JEstebanC.FastFoodApp.model.Ingredient;
import JEstebanC.FastFoodApp.repository.I_IngredientRepository;
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
public class IngredientServiceImp  implements I_IngredientService{

	@Autowired
	private I_IngredientRepository ingredientRepository;
	
	@Override
	public Ingredient create(Ingredient ingredient) {
		log.info("Saving new ingredient: " + ingredient.getName());
		return ingredientRepository.save(ingredient);
	}

	@Override
	public Ingredient update(Ingredient ingredient) {
		log.info("Updating ingredient with id:" + ingredient.getIdIngredient());
		return ingredientRepository.save(ingredient);
	}

	@Override
	public Boolean delete(Long id_Ingredient) {
		log.info("Deleting the ingredient id: " + id_Ingredient);
		if (ingredientRepository.existsById(id_Ingredient)) {
			ingredientRepository.deleteById(id_Ingredient);
			return true;
		} else {
			return false;
		}
	}

	@Override
	public Collection<Ingredient> list() {
		log.info("List all ingredient");
		return ingredientRepository.findAll();
	}

	@Override
	public Boolean exist(Long id_Ingredient) {
		log.info("Searching ingredient by id: " + id_Ingredient);
		return ingredientRepository.existsById(id_Ingredient);
	}

	public Ingredient findByName(String name) {
		log.info("Searching ingredient by name: " + name);
		return ingredientRepository.findByName(name);
	}

	
}
