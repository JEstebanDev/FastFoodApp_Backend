package JEstebanC.FastFoodApp.controller;

import JEstebanC.FastFoodApp.model.Product;
import JEstebanC.FastFoodApp.model.Response;
import JEstebanC.FastFoodApp.service.ProductServiceImp;
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
import java.util.Collection;
import java.util.Map;

/**
 * @author Juan Esteban Castaño Holguin castanoesteban9@gmail.com 2022-01-23
 */

@RestController
@RequiredArgsConstructor
@RequestMapping("/product")
public class ProductController {

    @Autowired
    private final ProductServiceImp serviceImp;

    //	CREATE
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping()
    public ResponseEntity<Response> saveProduct(@RequestParam("request") @Valid String strProduct,
                                                @RequestParam("productimage") @Nullable MultipartFile file) {
        try {
            Product product = serviceImp.create(new ObjectMapper().readValue(strProduct, Product.class), file);
            if (product != null) {
                return ResponseEntity.ok(Response.builder().timeStamp(Instant.now()).data(Map.of("product", product))
                        .message("Create product").status(HttpStatus.OK).statusCode(HttpStatus.OK.value()).build());
            } else {
                return ResponseEntity.ok(Response.builder().timeStamp(Instant.now())
                        .developerMessage("Please check if exist any category").message("Cannot create the product")
                        .status(HttpStatus.OK).statusCode(HttpStatus.OK.value()).build());
            }

        } catch (JsonProcessingException e) {
            return ResponseEntity.ok(
                    Response.builder().timeStamp(Instant.now()).message("Error creating the product: " + e.getMessage())
                            .status(HttpStatus.BAD_REQUEST).statusCode(HttpStatus.BAD_REQUEST.value()).build());
        }

    }

    //	READ
    @GetMapping(value = "/list/{page}")
    public ResponseEntity<Response> getProduct(@PathVariable(name = "page") Long page) {
        return ResponseEntity
                .ok(Response.builder().timeStamp(Instant.now()).data(Map.of("products", serviceImp.list(page)))
                        .message("List products").status(HttpStatus.OK).statusCode(HttpStatus.OK.value()).build());
    }

    //	UPDATE
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_EMPLOYEE')")
    @PutMapping(value = "/{id}")
    public ResponseEntity<Response> updateProduct(@PathVariable("id") Long id,
                                                  @RequestParam("request") @Valid String strProduct,
                                                  @RequestParam("productimage") @Nullable MultipartFile file) {
        try {
            if (serviceImp.exist(id)) {
                Product product = serviceImp.update(id, new ObjectMapper().readValue(strProduct, Product.class), file);
                if (product != null) {
                    return ResponseEntity.ok(Response.builder().timeStamp(Instant.now())
                            .data(Map.of("product", product)).message("Update product with id:" + id)
                            .status(HttpStatus.OK).statusCode(HttpStatus.OK.value()).build());
                } else {
                    return ResponseEntity.ok(Response.builder().timeStamp(Instant.now())
                            .developerMessage("Please check if exist any category").message("Cannot create the product")
                            .status(HttpStatus.OK).statusCode(HttpStatus.OK.value()).build());
                }
            } else {
                return ResponseEntity.ok(Response.builder().timeStamp(Instant.now())
                        .message("The product with id:" + id + " does not exist").status(HttpStatus.BAD_REQUEST)
                        .statusCode(HttpStatus.BAD_REQUEST.value()).build());
            }
        } catch (JsonProcessingException e) {
            return ResponseEntity.ok(
                    Response.builder().timeStamp(Instant.now()).message("Error updating the product: " + e.getMessage())
                            .status(HttpStatus.BAD_REQUEST).statusCode(HttpStatus.BAD_REQUEST.value()).build());
        }
    }

    //	DELETE
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Response> deleteProduct(@PathVariable("id") Long id) {
        if (serviceImp.exist(id)) {
            return ResponseEntity.ok(Response.builder().timeStamp(Instant.now())
                    .data(Map.of("product", serviceImp.delete(id))).message("Delete product with id: " + id)
                    .status(HttpStatus.OK).statusCode(HttpStatus.OK.value()).build());
        } else {
            return ResponseEntity
                    .ok(Response.builder().timeStamp(Instant.now()).message("The product " + id + " does not exist")
                            .status(HttpStatus.BAD_REQUEST).statusCode(HttpStatus.BAD_REQUEST.value()).build());
        }
    }

    //	SEARCH BY ID
    @GetMapping(value = "/id/{id}")
    public ResponseEntity<Response> getProductById(@PathVariable("id") Long id) {
        Product product = serviceImp.findById(id);
        if (product != null) {
            return ResponseEntity.ok(Response.builder().timeStamp(Instant.now()).data(Map.of("products", product))
                    .message("Get products by id: " + id).status(HttpStatus.OK).statusCode(HttpStatus.OK.value())
                    .build());

        } else {
            return ResponseEntity.ok(Response.builder().timeStamp(Instant.now())
                    .message("The product with id: " + id + " does not exist").status(HttpStatus.BAD_REQUEST)
                    .statusCode(HttpStatus.BAD_REQUEST.value()).build());
        }
    }

    //	SEARCH BY NAME
    @GetMapping(value = "/{name}")
    public ResponseEntity<Response> getProductByName(@PathVariable("name") String name) {
        Collection<Product> listProduct = serviceImp.findByName(name);
        if (listProduct != null) {
            return ResponseEntity.ok(Response.builder().timeStamp(Instant.now()).data(Map.of("products", listProduct))
                    .message("Get products by name: " + name).status(HttpStatus.OK).statusCode(HttpStatus.OK.value())
                    .build());

        } else {
            return ResponseEntity.ok(Response.builder().timeStamp(Instant.now())
                    .message("The product called " + name + " does not exist").status(HttpStatus.BAD_REQUEST)
                    .statusCode(HttpStatus.BAD_REQUEST.value()).build());
        }
    }

    //	ORDER BY HIGHLIGHT
    @GetMapping(value = "/highlight")
    public ResponseEntity<Response> getProductOrderByHighlight() {
        Collection<Product> listProducts = serviceImp.findAllOrderByHighlight();
        if (listProducts != null) {
            return ResponseEntity.ok(Response.builder().timeStamp(Instant.now()).data(Map.of("products", listProducts))
                    .message("Products highlight: ").status(HttpStatus.OK).statusCode(HttpStatus.OK.value()).build());

        } else {
            return ResponseEntity
                    .ok(Response.builder().timeStamp(Instant.now()).message("There are not products with highlight ")
                            .status(HttpStatus.BAD_REQUEST).statusCode(HttpStatus.BAD_REQUEST.value()).build());
        }

    }

    //	SEARCH BY CATEGORY
    @GetMapping(value = "/category/{name}")
    public ResponseEntity<Response> getProductByCategoryName(@PathVariable("name") String name) {
        Collection<Product> listProduct = serviceImp.findByNameCategory(name);
        return getProductByCategory(name, listProduct);

    }

    @GetMapping(value = "/category-admin/{name}")
    public ResponseEntity<Response> getProductByCategoryNameAdmin(@PathVariable("name") String name) {
        Collection<Product> listProduct = serviceImp.findByNameCategoryAdmin(name);
        return getProductByCategory(name, listProduct);

    }

    private ResponseEntity<Response> getProductByCategory(@PathVariable("name") String name, Collection<Product> listProduct) {
        if (listProduct != null) {
            return ResponseEntity.ok(Response.builder().timeStamp(Instant.now()).data(Map.of("products", listProduct))
                    .message("Get product with category called: " + name).status(HttpStatus.OK)
                    .statusCode(HttpStatus.OK.value()).build());

        } else {
            return ResponseEntity.ok(Response.builder().timeStamp(Instant.now())
                    .message("The product with category called " + name + " does not exist")
                    .status(HttpStatus.BAD_REQUEST).statusCode(HttpStatus.BAD_REQUEST.value()).build());
        }
    }
}
