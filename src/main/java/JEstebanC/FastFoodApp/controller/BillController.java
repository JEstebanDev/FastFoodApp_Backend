package JEstebanC.FastFoodApp.controller;

import JEstebanC.FastFoodApp.dto.BillUserDTO;
import JEstebanC.FastFoodApp.dto.UserBillOrdersDTO;
import JEstebanC.FastFoodApp.enumeration.StatusBill;
import JEstebanC.FastFoodApp.enumeration.StatusOrder;
import JEstebanC.FastFoodApp.model.Bill;
import JEstebanC.FastFoodApp.model.Response;
import JEstebanC.FastFoodApp.model.User;
import JEstebanC.FastFoodApp.security.OperationUtil;
import JEstebanC.FastFoodApp.service.BillServiceImp;
import JEstebanC.FastFoodApp.service.UserServiceImp;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.*;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.time.Instant;
import java.util.Map;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;

/**
 * @author Juan Esteban Castaño Holguin castanoesteban9@gmail.com 2022-01-26
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/bill")
@Slf4j
public class BillController {
    @Autowired
    private final BillServiceImp serviceImp;
    @Autowired
    private final UserServiceImp serviceImpUser;
    // CREATE
    @PostMapping()
    public ResponseEntity<Response> saveBill(@RequestBody @Valid Bill bill) {
        return ResponseEntity.ok(
                Response.builder()
                        .timeStamp(Instant.now())
                        .data(Map.of("bill", serviceImp.create(bill)))
                        .message("Create bill")
                        .status(HttpStatus.OK)
                        .statusCode(HttpStatus.OK.value())
                        .build());
    }

    @PreAuthorize("hasRole('ROLE_ADMIN') OR hasRole('ROLE_EMPLOYEE')")
    @GetMapping(value = "/status/{idBill}")
    public ResponseEntity<Response> updateStatusBill(
            @PathVariable("idBill") Long idBill, @Param(value = "statusBill") StatusBill statusBill) {
        if (serviceImp.exist(idBill)) {
            UserBillOrdersDTO userBillOrdersClient = serviceImp.findByIdBill(idBill);
            if (!userBillOrdersClient.getBillUserDTO().getStatusBill().equals(StatusBill.PAID)) {
                return ResponseEntity.ok(
                        Response.builder()
                                .timeStamp(Instant.now())
                                .data(Map.of("bill", serviceImp.updateStatusBill(idBill, statusBill)))
                                .message("bill")
                                .status(HttpStatus.OK)
                                .statusCode(HttpStatus.OK.value())
                                .build());
            } else {
                return ResponseEntity.ok(
                        Response.builder()
                                .timeStamp(Instant.now())
                                .message("The bill with id:" + idBill + " is already paid")
                                .status(HttpStatus.BAD_REQUEST)
                                .statusCode(HttpStatus.BAD_REQUEST.value())
                                .build());
            }
        } else {
            return ResponseEntity.ok(
                    Response.builder()
                            .timeStamp(Instant.now())
                            .message("The bill with id:" + idBill + " does not exist")
                            .status(HttpStatus.BAD_REQUEST)
                            .statusCode(HttpStatus.BAD_REQUEST.value())
                            .build());
        }
    }

    // READ SEARCH BY PARAMS
    @PreAuthorize("hasRole('ROLE_ADMIN') OR hasRole('ROLE_EMPLOYEE') OR hasRole('ROLE_CLIENT')")
    @GetMapping(value = "/list")
    public ResponseEntity<Response> listByParams(
            @Param(value = "idBill") Long idBill,
            @Param(value = "username") String username,
            @Param(value = "statusBill") StatusBill statusBill,
            @Param(value = "statusOrder") StatusOrder statusOrder,
            @Param(value = "startDate") String startDate,
            @Param(value = "endDate") String endDate,
            HttpServletRequest request) {
        if (request.isUserInRole("ROLE_CLIENT")) {
            String authorizationHeader = request.getHeader(AUTHORIZATION);
            if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
                try {
                    String token = authorizationHeader.substring("Bearer ".length());
                    Algorithm algorithm = Algorithm.HMAC256(OperationUtil.keyValue().getBytes());
                    JWTVerifier verifier = JWT.require(algorithm).build();
                    DecodedJWT decodeJWT = verifier.verify(token);
                    if (idBill != null) {
                        if (serviceImp.exist(idBill)) {
                            UserBillOrdersDTO userBillOrdersClient = serviceImp.findByIdBill(idBill);
                            if (userBillOrdersClient
                                    .getBillUserDTO()
                                    .getUserForBill()
                                    .getUsername()
                                    .equals(decodeJWT.getSubject())) {
                                return ResponseEntity.ok(
                                        Response.builder()
                                                .timeStamp(Instant.now())
                                                .data(Map.of("bill", userBillOrdersClient))
                                                .message("bill")
                                                .status(HttpStatus.OK)
                                                .statusCode(HttpStatus.OK.value())
                                                .build());
                            } else {
                                return ResponseEntity.ok(
                                        Response.builder()
                                                .timeStamp(Instant.now())
                                                .message(("Error seeing the bill, you have not the permissions"))
                                                .status(HttpStatus.BAD_REQUEST)
                                                .statusCode(HttpStatus.BAD_REQUEST.value())
                                                .build());
                            }
                        } else {
                            return ResponseEntity.ok(
                                    Response.builder()
                                            .timeStamp(Instant.now())
                                            .message("The bill with id:" + idBill + " does not exist")
                                            .status(HttpStatus.BAD_REQUEST)
                                            .statusCode(HttpStatus.BAD_REQUEST.value())
                                            .build());
                        }
                    }
                    if (username != null) {
                        if (serviceImpUser.exist(username)) {
                            User userOld = serviceImpUser.findByUsername(username);
                            if (userOld.getUsername().equals(decodeJWT.getSubject())) {
                                return ResponseEntity.ok(
                                        Response.builder()
                                                .timeStamp(Instant.now())
                                                .data(
                                                        Map.of(
                                                                "bill",
                                                                serviceImp.findByNewIdUser(
                                                                        username, statusBill, startDate, endDate)))
                                                .message("bill")
                                                .status(HttpStatus.OK)
                                                .statusCode(HttpStatus.OK.value())
                                                .build());
                            } else {
                                return ResponseEntity.ok(
                                        Response.builder()
                                                .timeStamp(Instant.now())
                                                .message(("Error seeing the bill, you have not the permissions"))
                                                .status(HttpStatus.BAD_REQUEST)
                                                .statusCode(HttpStatus.BAD_REQUEST.value())
                                                .build());
                            }
                        } else {
                            return ResponseEntity.ok(
                                    Response.builder()
                                            .timeStamp(Instant.now())
                                            .message(("Error seeing the bill, you have not the permissions"))
                                            .status(HttpStatus.BAD_REQUEST)
                                            .statusCode(HttpStatus.BAD_REQUEST.value())
                                            .build());
                        }

                    } else {
                        return ResponseEntity.ok(
                                Response.builder()
                                        .timeStamp(Instant.now())
                                        .message(("Error seeing the bill, you have not the permissions"))
                                        .status(HttpStatus.BAD_REQUEST)
                                        .statusCode(HttpStatus.BAD_REQUEST.value())
                                        .build());
                    }
                } catch (Exception e) {
                    return ResponseEntity.ok(
                            Response.builder()
                                    .timeStamp(Instant.now())
                                    .message(("Error validating bill by client: " + e.getMessage()))
                                    .status(HttpStatus.BAD_REQUEST)
                                    .statusCode(HttpStatus.BAD_REQUEST.value())
                                    .build());
                }
            }
        }
        if (idBill != null) {
            if (serviceImp.exist(idBill)) {
                return ResponseEntity.ok(
                        Response.builder()
                                .timeStamp(Instant.now())
                                .data(Map.of("bill", serviceImp.findByIdBill(idBill)))
                                .message("bill")
                                .status(HttpStatus.OK)
                                .statusCode(HttpStatus.OK.value())
                                .build());
            } else {
                return ResponseEntity.ok(
                        Response.builder()
                                .timeStamp(Instant.now())
                                .message("The bill with id:" + idBill + " does not exist")
                                .status(HttpStatus.BAD_REQUEST)
                                .statusCode(HttpStatus.BAD_REQUEST.value())
                                .build());
            }
        }
        if (statusBill == null && statusOrder != null && startDate != null && endDate != null) {
            return ResponseEntity.ok(
                    Response.builder()
                            .timeStamp(Instant.now())
                            .data(Map.of("bill", serviceImp.findByOrder(statusOrder, startDate, endDate)))
                            .message("bill")
                            .status(HttpStatus.OK)
                            .statusCode(HttpStatus.OK.value())
                            .build());
        }
        return ResponseEntity.ok(
                Response.builder()
                        .timeStamp(Instant.now())
                        .data(
                                Map.of("bill", serviceImp.findByNewIdUser(username, statusBill, startDate, endDate)))
                        .message("bill")
                        .status(HttpStatus.OK)
                        .statusCode(HttpStatus.OK.value())
                        .build());
    }

    // UPDATE
    @PreAuthorize("hasRole('ROLE_ADMIN') OR hasRole('ROLE_EMPLOYEE')")
    @PutMapping(value = "/{id}")
    public ResponseEntity<Response> updateBill(
            @PathVariable("id") Long id, @RequestBody @Valid Bill bill) {
        if (serviceImp.exist(id)) {
            if (bill.getStatusBill() != null) {
                BillUserDTO billUserDTO = serviceImp.update(id, bill);
                if (billUserDTO != null) {
                    return ResponseEntity.ok(
                            Response.builder()
                                    .timeStamp(Instant.now())
                                    .data(Map.of("bill", billUserDTO))
                                    .message("Update bill with id:" + id)
                                    .status(HttpStatus.OK)
                                    .statusCode(HttpStatus.OK.value())
                                    .build());
                } else {
                    return ResponseEntity.ok(
                            Response.builder()
                                    .timeStamp(Instant.now())
                                    .message("The user is not the same")
                                    .status(HttpStatus.BAD_REQUEST)
                                    .statusCode(HttpStatus.BAD_REQUEST.value())
                                    .build());
                }
            } else {
                return ResponseEntity.ok(
                        Response.builder()
                                .timeStamp(Instant.now())
                                .message("The bill does not have the mandatory information")
                                .status(HttpStatus.BAD_REQUEST)
                                .statusCode(HttpStatus.BAD_REQUEST.value())
                                .build());
            }
        } else {
            return ResponseEntity.ok(
                    Response.builder()
                            .timeStamp(Instant.now())
                            .message("The bill with id:" + id + " does not exist")
                            .status(HttpStatus.BAD_REQUEST)
                            .statusCode(HttpStatus.BAD_REQUEST.value())
                            .build());
        }
    }

    // DELETE
    @PreAuthorize("hasRole('ROLE_ADMIN') OR hasRole('ROLE_EMPLOYEE')")
    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Response> deleteBill(@PathVariable("id") Long id) {
        if (serviceImp.exist(id)) {
            return ResponseEntity.ok(
                    Response.builder()
                            .timeStamp(Instant.now())
                            .data(Map.of("bill", serviceImp.delete(id)))
                            .message("Delete bill with id: " + id)
                            .status(HttpStatus.OK)
                            .statusCode(HttpStatus.OK.value())
                            .build());
        } else {
            return ResponseEntity.ok(
                    Response.builder()
                            .timeStamp(Instant.now())
                            .message("The bill " + id + " does not exist")
                            .status(HttpStatus.BAD_REQUEST)
                            .statusCode(HttpStatus.BAD_REQUEST.value())
                            .build());
        }
    }

    @PreAuthorize("hasRole('ROLE_UNATTRIBUTED')")
    @GetMapping(value = "/unattributed")
    public ResponseEntity<Response> seeBillUnattributed(HttpServletRequest request) {
        String authorizationHeader = request.getHeader(AUTHORIZATION);
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            try {
                String token = authorizationHeader.substring("Bearer ".length());
                Algorithm algorithm = Algorithm.HMAC256(OperationUtil.keyValue().getBytes());
                JWTVerifier verifier = JWT.require(algorithm).build();
                DecodedJWT decodeJWT = verifier.verify(token);
                Long idBill = Long.valueOf(decodeJWT.getKeyId());
                if (serviceImp.exist(idBill)) {
                    return ResponseEntity.ok(
                            Response.builder()
                                    .timeStamp(Instant.now())
                                    .data(Map.of("bill", serviceImp.findByIdBill(idBill)))
                                    .message("Searching bill with id: " + idBill)
                                    .status(HttpStatus.OK)
                                    .statusCode(HttpStatus.OK.value())
                                    .build());
                } else {
                    return ResponseEntity.ok(
                            Response.builder()
                                    .timeStamp(Instant.now())
                                    .message("The bill " + idBill + " does not exist")
                                    .status(HttpStatus.BAD_REQUEST)
                                    .statusCode(HttpStatus.BAD_REQUEST.value())
                                    .build());
                }
            } catch (Exception e) {
                return ResponseEntity.ok(
                        Response.builder()
                                .timeStamp(Instant.now())
                                .message(("Error validating token " + e.getMessage()))
                                .status(HttpStatus.BAD_REQUEST)
                                .statusCode(HttpStatus.BAD_REQUEST.value())
                                .build());
            }
        }
        return ResponseEntity.ok(
                Response.builder()
                        .timeStamp(Instant.now())
                        .message("The user does not have permissions")
                        .status(HttpStatus.BAD_REQUEST)
                        .statusCode(HttpStatus.BAD_REQUEST.value())
                        .build());

    }
    @GetMapping(value = "/transaction/{idBill}")
    public ResponseEntity<Response> checkTransaction(@PathVariable("idBill") Long idBill){
        if (serviceImp.exist(idBill)) {
            if (serviceImp.validateTransaction(idBill)) {
                return ResponseEntity.ok(
                        Response.builder()
                                .timeStamp(Instant.now())
                                .data(Map.of("bill", true))
                                .message("bill")
                                .status(HttpStatus.OK)
                                .statusCode(HttpStatus.OK.value())
                                .build());
            } else {
                return ResponseEntity.ok(
                        Response.builder()
                                .timeStamp(Instant.now())
                                .message("Error problems to connect with Wompi")
                                .status(HttpStatus.BAD_REQUEST)
                                .statusCode(HttpStatus.BAD_REQUEST.value())
                                .build());
            }
        } else {
            return ResponseEntity.ok(
                    Response.builder()
                            .timeStamp(Instant.now())
                            .message("The bill with id:" + idBill + " does not exist")
                            .status(HttpStatus.BAD_REQUEST)
                            .statusCode(HttpStatus.BAD_REQUEST.value())
                            .build());
        }
    }


}
