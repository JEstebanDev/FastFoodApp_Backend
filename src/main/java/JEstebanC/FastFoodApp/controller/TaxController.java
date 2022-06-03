package JEstebanC.FastFoodApp.controller;

import JEstebanC.FastFoodApp.model.Response;
import JEstebanC.FastFoodApp.model.Tax;
import JEstebanC.FastFoodApp.service.TaxServiceImp;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.Instant;
import java.util.Map;

/**
 * @author Juan Esteban Castaño Holguin castanoesteban9@gmail.com 6/3/2022
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/tax")
public class TaxController {
    @Autowired
    private final TaxServiceImp serviceImp;

    @PreAuthorize("hasRole('ROLE_ADMIN') OR hasRole('ROLE_EMPLOYEE')")
    @PostMapping()
    public ResponseEntity<Response> saveTax(@RequestBody @Valid Tax tax) {
        return ResponseEntity
                .ok(Response.builder().timeStamp(Instant.now()).data(Map.of("tax", serviceImp.create(tax)))
                        .message("Create tax").status(HttpStatus.OK).statusCode(HttpStatus.OK.value()).build());
    }

    @GetMapping()
    public ResponseEntity<Response> listTax() {
        return ResponseEntity
                .ok(Response.builder().timeStamp(Instant.now()).data(Map.of("tax", serviceImp.read()))
                        .message("List taxes").status(HttpStatus.OK).statusCode(HttpStatus.OK.value()).build());
    }

    @PreAuthorize("hasRole('ROLE_ADMIN') OR hasRole('ROLE_EMPLOYEE')")
    @PutMapping(value = "/{id}")
    public ResponseEntity<Response> updateTax(@PathVariable("id") Long idTax, @RequestBody @Valid Tax tax) {

        if (serviceImp.exist(idTax)) {
            return ResponseEntity
                    .ok(Response.builder().timeStamp(Instant.now()).data(Map.of("tax", serviceImp.update(idTax, tax)))
                            .message("Updated tax").status(HttpStatus.OK).statusCode(HttpStatus.OK.value()).build());
        } else {
            return ResponseEntity.ok(
                    Response.builder().timeStamp(Instant.now()).message("The tax with id:" + idTax + " does not exist")
                            .status(HttpStatus.BAD_REQUEST).statusCode(HttpStatus.BAD_REQUEST.value()).build());
        }

    }

    @PreAuthorize("hasRole('ROLE_ADMIN') OR hasRole('ROLE_EMPLOYEE')")
    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Response> deleteTax(@PathVariable("id") Long idTax) {

        if (serviceImp.exist(idTax)) {
            return ResponseEntity
                    .ok(Response.builder().timeStamp(Instant.now()).data(Map.of("tax", serviceImp.delete(idTax)))
                            .message("Updated tax").status(HttpStatus.OK).statusCode(HttpStatus.OK.value()).build());
        } else {
            return ResponseEntity.ok(
                    Response.builder().timeStamp(Instant.now()).message("The tax with id:" + idTax + " does not exist")
                            .status(HttpStatus.BAD_REQUEST).statusCode(HttpStatus.BAD_REQUEST.value()).build());
        }

    }
}
