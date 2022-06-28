package JEstebanC.FastFoodApp.controller;

import JEstebanC.FastFoodApp.model.Company;
import JEstebanC.FastFoodApp.model.Response;
import JEstebanC.FastFoodApp.service.CompanyServiceImp;
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
 * @author Juan Esteban Castaño Holguin castanoesteban9@gmail.com 5/28/2022
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/company")
public class CompanyController {
    @Autowired
    private final CompanyServiceImp serviceImp;

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping()
    public ResponseEntity<Response> saveCompany(@RequestParam("request") @Valid String strCompany,
                                                @RequestParam("logoimage") @Nullable MultipartFile file) throws JsonProcessingException {
        Company company = serviceImp.create(new ObjectMapper().readValue(strCompany, Company.class), file);
        if (company != null) {
            return ResponseEntity.ok(
                    Response.builder()
                            .timeStamp(Instant.now())
                            .data(Map.of("company", company))
                            .message("Create company")
                            .status(HttpStatus.OK)
                            .statusCode(HttpStatus.OK.value())
                            .build());
        } else {
            return ResponseEntity.ok(Response.builder().timeStamp(Instant.now())
                    .developerMessage("Please check the information").message("Cannot create the company")
                    .status(HttpStatus.OK).statusCode(HttpStatus.OK.value()).build());
        }

    }

    @GetMapping()
    public ResponseEntity<Response> listCompanies() {
        return ResponseEntity.ok(
                Response.builder()
                        .timeStamp(Instant.now())
                        .data(Map.of("company", serviceImp.list()))
                        .message("List companies")
                        .status(HttpStatus.OK)
                        .statusCode(HttpStatus.OK.value())
                        .build());
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PutMapping(value = "/{id}")
    public ResponseEntity<Response> updateCompany(@PathVariable("id") Long id, @RequestParam("request") @Valid String strCompany,
                                                  @RequestParam("logoimage") @Nullable MultipartFile file) throws JsonProcessingException {

        if (serviceImp.exist(id)) {
            Company company = serviceImp.update(id, new ObjectMapper().readValue(strCompany, Company.class), file);
            if (company != null) {
                return ResponseEntity.ok(
                        Response.builder()
                                .timeStamp(Instant.now())
                                .data(Map.of("company", company))
                                .message("Updating company")
                                .status(HttpStatus.OK)
                                .statusCode(HttpStatus.OK.value())
                                .build());
            } else {
                return ResponseEntity.ok(Response.builder().timeStamp(Instant.now())
                        .developerMessage("Please check the information").message("Cannot create the company")
                        .status(HttpStatus.OK).statusCode(HttpStatus.OK.value()).build());
            }
        } else {
            return ResponseEntity.ok(Response.builder().timeStamp(Instant.now())
                    .message("The company with id:" + id + " does not exist").status(HttpStatus.BAD_REQUEST)
                    .statusCode(HttpStatus.BAD_REQUEST.value()).build());
        }

    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Response> deleteCompany(@PathVariable("id") Long id) {
        return ResponseEntity.ok(
                Response.builder()
                        .timeStamp(Instant.now())
                        .data(Map.of("company", serviceImp.delete(id)))
                        .message("Delete company")
                        .status(HttpStatus.OK)
                        .statusCode(HttpStatus.OK.value())
                        .build());
    }
}
