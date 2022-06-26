package JEstebanC.FastFoodApp.controller;

import JEstebanC.FastFoodApp.dto.OrdersDTO;
import JEstebanC.FastFoodApp.dto.UserBillOrdersDTO;
import JEstebanC.FastFoodApp.enumeration.StatusBill;
import JEstebanC.FastFoodApp.enumeration.StatusOrder;
import JEstebanC.FastFoodApp.model.Orders;
import JEstebanC.FastFoodApp.model.Response;
import JEstebanC.FastFoodApp.service.BillServiceImp;
import JEstebanC.FastFoodApp.service.OrdersServiceImp;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.Instant;
import java.util.Map;

/**
 * @author Juan Esteban Castaño Holguin castanoesteban9@gmail.com 2022-01-28
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/orders")
@Slf4j
public class OrdersController {

    @Autowired
    private final OrdersServiceImp serviceImp;
    @Autowired
    private final BillServiceImp serviceBillImp;

    //	CREATE
    @PostMapping()
    public ResponseEntity<Response> saveOrder(@RequestBody @Valid Orders order) {

        if (serviceBillImp.exist(order.getBill().getIdBill())) {
            UserBillOrdersDTO userBillOrdersDTO = serviceBillImp.findByIdBill(order.getBill().getIdBill());
            if (userBillOrdersDTO.getBillUserDTO().getStatusBill() != StatusBill.PAID) {
                OrdersDTO ordersDTO=serviceImp.create(order);
                //this is important because this method update the totalPrice
                serviceImp.updateTotalPrice(order.getBill().getIdBill());
                return ResponseEntity.ok(
                        Response.builder()
                                .timeStamp(Instant.now())
                                .data(Map.of("order", ordersDTO))
                                .message("Create order")
                                .status(HttpStatus.OK)
                                .statusCode(HttpStatus.OK.value())
                                .build());
            } else {
                return ResponseEntity.ok(
                        Response.builder()
                                .timeStamp(Instant.now())
                                .message("The order does not created because the bill already paid")
                                .status(HttpStatus.BAD_REQUEST)
                                .statusCode(HttpStatus.BAD_REQUEST.value())
                                .build());
            }

        }
        return ResponseEntity.ok(
                Response.builder()
                        .timeStamp(Instant.now())
                        .message("The bill " + order.getIdOrder() + " does not exist")
                        .status(HttpStatus.BAD_REQUEST)
                        .statusCode(HttpStatus.BAD_REQUEST.value())
                        .build());


    }

    @PreAuthorize("hasRole('ROLE_ADMIN') OR hasRole('ROLE_EMPLOYEE')")
    @GetMapping(value = "/status/{idBill}")
    public ResponseEntity<Response> updateStatusOrder(
            @PathVariable("idBill") Long idBill,@Param(value = "statusOrder") StatusOrder statusOrder){
        UserBillOrdersDTO userBillOrdersDTO = serviceBillImp.findByIdBill(idBill);
        if (userBillOrdersDTO != null) {
            return ResponseEntity.ok(
                    Response.builder()
                            .timeStamp(Instant.now())
                            .data(Map.of("order", serviceImp.updateStatus(idBill,statusOrder)))
                            .message("Updating order with bill id: " + idBill)
                            .status(HttpStatus.OK)
                            .statusCode(HttpStatus.OK.value())
                            .build());
        }else{
            return ResponseEntity.ok(
                    Response.builder()
                            .timeStamp(Instant.now())
                            .message("The bill " + idBill + " does not exist")
                            .status(HttpStatus.BAD_REQUEST)
                            .statusCode(HttpStatus.BAD_REQUEST.value())
                            .build());
        }
    }

    //	UPDATE
    @PreAuthorize("hasRole('ROLE_ADMIN') OR hasRole('ROLE_EMPLOYEE')")
    @PutMapping(value = "/{id}")
    public ResponseEntity<Response> updateOrder(
            @PathVariable("id") Long id, @RequestBody @Valid Orders order) {
        UserBillOrdersDTO userBillOrdersDTO = serviceBillImp.findByIdBill(order.getBill().getIdBill());
        if (userBillOrdersDTO != null) {
            if (userBillOrdersDTO.getBillUserDTO().getStatusBill() != StatusBill.PAID) {
                if (order.getIdOrder() != null
                        && order.getAmount() != 0
                        && order.getStatusOrder() != null) {
                    Orders orderRequest = serviceImp.findByIdOrder(id);
                    if (orderRequest != null) {
                        return ResponseEntity.ok(
                                Response.builder()
                                        .timeStamp(Instant.now())
                                        .data(Map.of("order", serviceImp.update(id, order)))
                                        .message("Updating order with id: " + id)
                                        .status(HttpStatus.OK)
                                        .statusCode(HttpStatus.OK.value())
                                        .build());
                    } else {
                        return ResponseEntity.ok(
                                Response.builder()
                                        .timeStamp(Instant.now())
                                        .message("The order " + id + " does not exist")
                                        .status(HttpStatus.BAD_REQUEST)
                                        .statusCode(HttpStatus.BAD_REQUEST.value())
                                        .build());
                    }
                } else {
                    return ResponseEntity.ok(
                            Response.builder()
                                    .timeStamp(Instant.now())
                                    .message("The order does not have the mandatory information")
                                    .status(HttpStatus.BAD_REQUEST)
                                    .statusCode(HttpStatus.BAD_REQUEST.value())
                                    .build());
                }

            } else {
                return ResponseEntity.ok(
                        Response.builder()
                                .timeStamp(Instant.now())
                                .message(
                                        "The order with id:"
                                                + order.getIdOrder()
                                                + " does not created because the bill already paid")
                                .status(HttpStatus.BAD_REQUEST)
                                .statusCode(HttpStatus.BAD_REQUEST.value())
                                .build());
            }
        } else {
            return ResponseEntity.ok(
                    Response.builder()
                            .timeStamp(Instant.now())
                            .message("The bill " + order.getBill().getIdBill() + " does not exist")
                            .status(HttpStatus.BAD_REQUEST)
                            .statusCode(HttpStatus.BAD_REQUEST.value())
                            .build());
        }
    }

    @PreAuthorize("hasRole('ROLE_ADMIN') OR hasRole('ROLE_EMPLOYEE')")
    //	DELETE
    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Response> deleteOrder(@PathVariable("id") Long id) {

        if (serviceImp.exist(id)) {
            return ResponseEntity.ok(
                    Response.builder()
                            .timeStamp(Instant.now())
                            .data(Map.of("order", serviceImp.delete(id)))
                            .message("order bill with id: " + id)
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
}
