package JEstebanC.FastFoodApp.controller;

import JEstebanC.FastFoodApp.model.Category;
import JEstebanC.FastFoodApp.model.Response;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.time.Instant;
import java.util.Map;

/**
 * @author Juan Esteban Castaño Holguin castanoesteban9@gmail.com 2022-01-24
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/mercadopago")
public class MercadoPagoController {

    /*@PostMapping()
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
    }*/
}
