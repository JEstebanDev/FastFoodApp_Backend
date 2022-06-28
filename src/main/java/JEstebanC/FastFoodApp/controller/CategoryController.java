/**
 *
 */
package JEstebanC.FastFoodApp.controller;

import JEstebanC.FastFoodApp.model.Category;
import JEstebanC.FastFoodApp.model.Response;
import JEstebanC.FastFoodApp.service.CategoryServiceImp;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.time.Instant;
import java.util.Map;

/**
 * @author Juan Esteban Castaño Holguin castanoesteban9@gmail.com 2022-01-24
 */
@RestController

@RequiredArgsConstructor
@RequestMapping("/category")
public class CategoryController {

    @Autowired
    private final CategoryServiceImp serviceImp;

    //	CREATE
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping()
    public ResponseEntity<Response> saveCategory(@RequestParam("request") @Valid String strCategory,
                                                 @RequestParam("categoryImage") @Nullable MultipartFile file) {
        try {
            Category category = new ObjectMapper().readValue(strCategory, Category.class);
            return ResponseEntity.ok(Response.builder().timeStamp(Instant.now())
                    .data(Map.of("category", serviceImp.create(category, file))).message("Create category")
                    .status(HttpStatus.OK).statusCode(HttpStatus.OK.value()).build());

        } catch (JsonProcessingException e) {
            return ResponseEntity.ok(Response.builder().timeStamp(Instant.now())
                    .message("Error creating the category: " + e.getMessage()).status(HttpStatus.BAD_REQUEST)
                    .statusCode(HttpStatus.BAD_REQUEST.value()).build());
        }
    }

    //  READ
    @GetMapping(value = "/list")
    public ResponseEntity<Response> getCategory() {
        return ResponseEntity.ok(Response.builder().timeStamp(Instant.now()).data(Map.of("category", serviceImp.list()))
                .message("List categories").status(HttpStatus.OK).statusCode(HttpStatus.OK.value()).build());
    }

    @GetMapping(value = "/category-product")
    public ResponseEntity<Response> getCategoriesWithProducts() {
        return ResponseEntity.ok(Response.builder().timeStamp(Instant.now()).data(Map.of("category", serviceImp.listCategoriesWithProducts()))
                .message("List categories with products").status(HttpStatus.OK).statusCode(HttpStatus.OK.value()).build());
    }

    //	UPDATE
    @PreAuthorize("hasRole('ROLE_ADMIN') OR hasRole('ROLE_EMPLOYEE')")
    @PutMapping(value = "/{id}")
    public ResponseEntity<Response> updateCategory(@PathVariable("id") Long id,
                                                   @RequestParam("request") @Valid String strCategory, @RequestParam("categoryImage") @Nullable MultipartFile file) {
        try {
            Category category = new ObjectMapper().readValue(strCategory, Category.class);
            if (serviceImp.exist(id)) {
                return ResponseEntity.ok(Response.builder().timeStamp(Instant.now())
                        .data(Map.of("category", serviceImp.update(id, category, file)))
                        .message("Update category with id:" + id).status(HttpStatus.OK)
                        .statusCode(HttpStatus.OK.value()).build());
            } else {
                return ResponseEntity.ok(Response.builder().timeStamp(Instant.now())
                        .message("The category with id:" + id + " does not exist").status(HttpStatus.BAD_REQUEST)
                        .statusCode(HttpStatus.BAD_REQUEST.value()).build());
            }
        } catch (JsonProcessingException e) {
            return ResponseEntity.ok(Response.builder().timeStamp(Instant.now())
                    .message("Error creating the category: " + e.getMessage()).status(HttpStatus.BAD_REQUEST)
                    .statusCode(HttpStatus.BAD_REQUEST.value()).build());
        }
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
//	DELETE
    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Response> deleteCategory(@PathVariable("id") Long id) {
        if (serviceImp.exist(id)) {
            return ResponseEntity.ok(Response.builder().timeStamp(Instant.now())
                    .data(Map.of("category", serviceImp.delete(id))).message("Delete category with id: " + id)
                    .status(HttpStatus.OK).statusCode(HttpStatus.OK.value()).build());
        } else {
            return ResponseEntity
                    .ok(Response.builder().timeStamp(Instant.now()).message("The category " + id + " does not exist")
                            .status(HttpStatus.BAD_REQUEST).statusCode(HttpStatus.BAD_REQUEST.value()).build());
        }
    }

    //	SEARCH BY NAME
    @GetMapping(value = "/{name}")
    public ResponseEntity<Response> getCategoryByName(@PathVariable("name") String name) {

        if (serviceImp.findByName(name) != null) {
            return ResponseEntity.ok(Response.builder().timeStamp(Instant.now())
                    .data(Map.of("category", serviceImp.findByName(name))).message("Get category by name: " + name)
                    .status(HttpStatus.OK).statusCode(HttpStatus.OK.value()).build());

        } else {
            return ResponseEntity.ok(Response.builder().timeStamp(Instant.now())
                    .message("The category called" + name + " does not exist").status(HttpStatus.BAD_REQUEST)
                    .statusCode(HttpStatus.BAD_REQUEST.value()).build());
        }

    }
}
