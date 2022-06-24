package JEstebanC.FastFoodApp.service;

import JEstebanC.FastFoodApp.enumeration.Status;
import JEstebanC.FastFoodApp.model.Category;
import JEstebanC.FastFoodApp.model.Product;
import JEstebanC.FastFoodApp.repository.ICategoryRepository;
import JEstebanC.FastFoodApp.repository.IProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

class ProductServiceImpTest {
    @Mock
    IProductRepository productRepository;
    @Mock
    ICategoryRepository categoryRepository;
    @InjectMocks
    ProductServiceImp productServiceImp;
    Product basicProduct;
    Category basicCategory;
    AutoCloseable autoCloseable;

    @BeforeEach
    void setUp() {
        autoCloseable = MockitoAnnotations.openMocks(this);
        basicCategory = new Category();
        basicCategory.setIdCategory(1L);

        basicProduct = new Product();
        basicProduct.setIdProduct(1L);
        basicProduct.setName("Basic Product");
        basicProduct.setDescription("Basic Product Description");
        basicProduct.setPrice(10);
        basicProduct.setCategory(basicCategory);
        basicProduct.setStatus(Status.ACTIVE);
        basicProduct.setCalories(100);
        basicProduct.setDiscountPoint(50);
        basicProduct.setHighlight(2);
        basicProduct.setImageUrl(null);

    }

    @Test
    @DisplayName("PRODUCT create() checks the category exists and saves it")
    void createProductModifiesProduct() {
        when(productRepository.save(any(Product.class))).thenReturn(basicProduct);
        when(categoryRepository.existsById(any(Long.class))).thenReturn(true);
        assertSame(basicProduct.getCategory().getIdCategory(),
                productServiceImp.create(basicProduct, null).getCategory().getIdCategory());
    }

    @Test
    @DisplayName("PRODUCT create() returns null when category does not exist in repository")
    void createProductReturnsNullIfCategoryDoesNotExistInRepo() {
        when(productRepository.save(any(Product.class))).thenReturn(basicProduct);
        when(categoryRepository.existsById(any(Long.class))).thenReturn(false);
        assertNull(productServiceImp.create(basicProduct, null));
    }

    @Test
    @DisplayName("PRODUCT create() returns null when argument Product has no associated category")
    void createProductReturnsNullIfProductArgumentHasNoAssociatedCategory() {
        basicProduct.setCategory(null);
        when(productRepository.save(any(Product.class))).thenReturn(basicProduct);
        when(categoryRepository.existsById(any(Long.class))).thenReturn(false);
        assertNull(productServiceImp.create(basicProduct, null));
    }

    @Test
    @DisplayName("PRODUCT update() returns null when Product argument has no associated category")
    void updateReturnsNullIfProductArgumentHasAssociatedCategory() {
        basicProduct.setCategory(null);
        when(productRepository.save(any(Product.class))).thenReturn(basicProduct);
        when(categoryRepository.existsById(any(Long.class))).thenReturn(false);
        assertNull(productServiceImp.update(50L, basicProduct, null));
    }

    @Test
    @DisplayName("PRODUCT update() returns null if category does not exist in repository")
    void updateReturnsNullIfCategoryIdDoesNotExistInRepo() {
        when(productRepository.save(any(Product.class))).thenReturn(basicProduct);
        when(categoryRepository.existsById(any(Long.class))).thenReturn(false);
        assertNull(productServiceImp.update(50L, basicProduct, null));
    }

    @Test
    @DisplayName("PRODUCT update() returns modified Product with null imageURL")
    void updateReturnsUpdateProductArguments() {
        when(productRepository.save(any(Product.class))).thenReturn(basicProduct);
        when(categoryRepository.existsById(any(Long.class))).thenReturn(true);
        assertSame(50L,
                productServiceImp.update(50L, basicProduct, null).getIdProduct());
        assertNull(productServiceImp.update(50L, basicProduct, null).getImageUrl());
    }
}