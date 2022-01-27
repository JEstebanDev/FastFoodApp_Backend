package JEstebanC.FastFoodApp.service;

import java.util.Collection;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import JEstebanC.FastFoodApp.model.CategoryIngredient;
import JEstebanC.FastFoodApp.repository.ICategoryIngredientRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * @author Juan Esteban Castaño Holguin castanoesteban9@gmail.com 2022-01-24
 */
@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class CategoryIngredientServiceImp implements ICategoryIngredientService {

	@Autowired
	private final ICategoryIngredientRepository categoryIngredientRepository;

	@Override
	public CategoryIngredient create(CategoryIngredient categoryIngredient) {
		log.info("Saving new category ingredient: " + categoryIngredient.getName());
		return categoryIngredientRepository.save(categoryIngredient);
	}

	@Override
	public CategoryIngredient update(CategoryIngredient categoryIngredient) {
		log.info("Updating category ingredient with id: " + categoryIngredient.getIdCategoryIngredient());
		return categoryIngredientRepository.save(categoryIngredient);
	}

	@Override
	public Boolean delete(Long idCategoryIngredient) {
		log.info("Deleting the category ingredient with id: " + idCategoryIngredient);
		if (categoryIngredientRepository.existsById(idCategoryIngredient)) {
			categoryIngredientRepository.deleteById(idCategoryIngredient);
			return true;
		} else {
			return false;
		}
	}

	@Override
	public Collection<CategoryIngredient> list() {
		log.info("List all categories ingredient");
		return categoryIngredientRepository.findAll();
	}

	@Override
	public Boolean exist(Long idCategory) {
		log.info("Searching category ingredient by id: " + idCategory);
		return categoryIngredientRepository.existsById(idCategory);
	}

	public CategoryIngredient findByName(String name) {
		log.info("Searching category ingredient by name: " + name);
		return categoryIngredientRepository.findByName(name);
	}

}
