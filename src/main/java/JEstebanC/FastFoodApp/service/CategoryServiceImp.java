package JEstebanC.FastFoodApp.service;

import java.util.Collection;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import JEstebanC.FastFoodApp.model.Category;
import JEstebanC.FastFoodApp.repository.ICategoryRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

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

	@Override
	public Category create(Category category) {
		log.info("Saving new category: " + category.getName());
		return categoryRepository.save(category);
	}

	@Override
	public Category update(Category category) {
		log.info("Updating category with id: " + category.getIdCategory());
		return categoryRepository.save(category);
	}

	@Override
	public Boolean delete(Long idCategory) {
		log.info("Deleting the category with id: " + idCategory);
		if (categoryRepository.existsById(idCategory)) {
			categoryRepository.deleteById(idCategory);
			return true;
		} else {
			return false;
		}
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

	public Category findByName(String name) {
		log.info("Searching category by name: " + name);
		return categoryRepository.findByName(name);
	}

}
