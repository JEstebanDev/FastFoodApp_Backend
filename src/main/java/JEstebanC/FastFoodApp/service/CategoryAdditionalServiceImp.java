package JEstebanC.FastFoodApp.service;

import java.util.Collection;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import JEstebanC.FastFoodApp.model.CategoryAdditional;
import JEstebanC.FastFoodApp.repository.ICategoryAdditionalRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * @author Juan Esteban Castaño Holguin castanoesteban9@gmail.com 2022-01-24
 */
@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class CategoryAdditionalServiceImp implements ICategoryAdditionalService {

	@Autowired
	private final ICategoryAdditionalRepository categoryAdditionalRepository;

	@Override
	public CategoryAdditional create(CategoryAdditional categoryAdditional) {
		log.info("Saving new category additional: " + categoryAdditional.getName());
		return categoryAdditionalRepository.save(categoryAdditional);
	}

	@Override
	public CategoryAdditional update(Long id,CategoryAdditional categoryAdditional) {
		log.info("Updating category additional with id: " +  id);
		return categoryAdditionalRepository.save(categoryAdditional);
	}

	@Override
	public Boolean delete(Long idcategoryAdditional) {
		log.info("Deleting the category additional with id: " + idcategoryAdditional);
		if (categoryAdditionalRepository.existsById(idcategoryAdditional)) {
			categoryAdditionalRepository.deleteById(idcategoryAdditional);
			return true;
		} else {
			return false;
		}
	}

	@Override
	public Collection<CategoryAdditional> list() {
		log.info("List all categories additional");
		return categoryAdditionalRepository.findAll();
	}

	@Override
	public Boolean exist(Long idCategory) {
		log.info("Searching category additional by id: " + idCategory);
		return categoryAdditionalRepository.existsById(idCategory);
	}

	public Collection<CategoryAdditional> findByName(String name) {
		log.info("Searching category additional by name: " + name);
		return categoryAdditionalRepository.findByNameStartsWith(name);
	}

}
