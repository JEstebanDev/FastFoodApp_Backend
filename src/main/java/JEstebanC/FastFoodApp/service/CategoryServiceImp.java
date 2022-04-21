package JEstebanC.FastFoodApp.service;

import java.util.Collection;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import JEstebanC.FastFoodApp.model.Category;
import JEstebanC.FastFoodApp.repository.ICategoryRepository;
import JEstebanC.FastFoodApp.service.interfaces.ICategoryService;
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

	@Autowired
	private final FileStorageService fileStorageService;

	@Override
	public Category create(Category category,MultipartFile file) {
		log.info("Saving new category: " + category.getName());
		if (file!=null) {
			category.setImageUrl(fileStorageService.uploadAndDownloadFile(file, "categoryimage"));
		}

		return categoryRepository.save(category);
	}

	@Override
	public Category update(Long id, Category category, MultipartFile file) {
		log.info("Updating category with id: " + id);
		Optional<Category> oldCategory = categoryRepository.findById(id);
		category.setIdCategory(id);
		if (file != null) {
			category.setImageUrl(fileStorageService.uploadAndDownloadFile(file, "categoryimage"));
		} else {
			category.setImageUrl(oldCategory.get().getImageUrl());
		}
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

	public Collection<Category> findByName(String name) {
		log.info("Searching category by name: " + name);
		return categoryRepository.findAllByNameStartsWith(name);
	}

}
