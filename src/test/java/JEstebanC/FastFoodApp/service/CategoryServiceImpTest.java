package JEstebanC.FastFoodApp.service;

import JEstebanC.FastFoodApp.enumeration.Status;
import JEstebanC.FastFoodApp.model.Category;
import JEstebanC.FastFoodApp.repository.ICategoryRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

class CategoryServiceImpTest {
    @Mock
    ICategoryRepository categoryRepository;
    @InjectMocks
    CategoryServiceImp categoryServiceImp;
    Category basicCategory;
    AutoCloseable autoCloseable;

    @BeforeEach
    void setUp() {
        autoCloseable = MockitoAnnotations.openMocks(this);
        basicCategory = new Category();
        basicCategory.setIdCategory(1L);
        basicCategory.setName("Test");
        basicCategory.setImageUrl(null);
        basicCategory.setStatus(Status.ACTIVE);
    }

    @Test
    @DisplayName("CATEGORY create() copies the a category and saves it")
    void createUpdatesRequiredFields() {
        when(categoryRepository.save(any(Category.class))).thenReturn(basicCategory);
        Category newCategory = categoryServiceImp.create(basicCategory, null);
        assertEquals(basicCategory.getName(), newCategory.getName());
    }

    @Test
    @DisplayName("CATEGORY create() throws exception when category is null")
    void createThrowsExceptionWhenCategoryIsNull() {
        assertThrows(NullPointerException.class, () -> categoryServiceImp.create(null, null));
    }

    @Test
    @DisplayName("CATEGORY update() takes a category and updates the Id and image")
    void updateFindsCategoryAndUpdatesIt() {
        when(categoryRepository.save(any(Category.class))).thenReturn(basicCategory);
        Category newCategory = categoryServiceImp.update(13L, basicCategory, null);
        assertEquals(basicCategory.getIdCategory(), newCategory.getIdCategory());
    }
    @Test
    @DisplayName("CATEGORY update() throws exception when argument id is null")
    void updateTrowsExceptionWhenIdIsNull() {
        assertThrows(IllegalArgumentException.class, () -> categoryServiceImp.update(null, basicCategory, null));
    }
}