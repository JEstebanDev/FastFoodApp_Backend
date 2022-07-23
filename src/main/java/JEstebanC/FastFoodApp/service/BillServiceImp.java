/**
 *
 */
package JEstebanC.FastFoodApp.service;

import JEstebanC.FastFoodApp.dto.BillUserDTO;
import JEstebanC.FastFoodApp.dto.EntireBillOrderDTO;
import JEstebanC.FastFoodApp.dto.OrdersDTO;
import JEstebanC.FastFoodApp.dto.UserBillOrdersDTO;
import JEstebanC.FastFoodApp.dto.validation.UserForBillDTO;
import JEstebanC.FastFoodApp.dto.wompi.Wompi;
import JEstebanC.FastFoodApp.enumeration.StatusBill;
import JEstebanC.FastFoodApp.enumeration.StatusOrder;
import JEstebanC.FastFoodApp.model.*;
import JEstebanC.FastFoodApp.repository.IBillRepository;
import JEstebanC.FastFoodApp.repository.IOrdersRepository;
import JEstebanC.FastFoodApp.repository.IPriceAdditionalHistory;
import JEstebanC.FastFoodApp.repository.IPriceProductHistory;
import JEstebanC.FastFoodApp.service.interfaces.IBillService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

/**
 * @author Juan Esteban Castaño Holguin castanoesteban9@gmail.com 2022-01-26
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class BillServiceImp implements IBillService {

    @Autowired
    private final IOrdersRepository ordersRepository;
    @Autowired
    private final IPriceProductHistory priceProductHistory;
    @Autowired
    private final IPriceAdditionalHistory priceAdditionalHistory;
    @Autowired
    private IBillRepository billRepository;

    @Override
    public BillUserDTO create(Bill bill) {
        log.info("Saving new bill");
        if (bill.getUser().getIdUser() == null) {
            bill.setUser(null);
        }
        bill.setStatusBill(StatusBill.PENDING);
        return convertBillToDTO(billRepository.save(bill));
    }

    private Wompi searchWompi(String referenceTransaction) {
        String url = "https://sandbox.wompi.co/v1/transactions?reference=" + referenceTransaction;
        // create headers
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        headers.set("Authorization", "Bearer prv_test_2Vjk6fZaET3oNejoRegTLiJO4Lk6yyjW");
        RestTemplate restTemplate = new RestTemplate();
        HttpEntity<Object> entity = new HttpEntity<>(headers);
        return restTemplate.exchange(url, HttpMethod.GET, entity, Wompi.class).getBody();
    }

    public StatusBill validateTransaction(long idBill) {
        Bill bill = billRepository.findByIdBill(idBill);
        bill.setIdBill(idBill);
        Wompi wompi = searchWompi(bill.getReferenceTransaction());
        AtomicReference<StatusBill> statusBill = new AtomicReference<>(StatusBill.NONE);
        if (wompi != null) {
            wompi.data.forEach(datum -> {
                if (datum.status.equals("APPROVED")) {
                    bill.setStatusBill(StatusBill.PAID);
                    billRepository.save(bill);
                    statusBill.set(StatusBill.PAID);
                }
                if (datum.status.equals("DECLINED")) {
                    bill.setStatusBill(StatusBill.DECLINED);
                    billRepository.save(bill);
                    statusBill.set(StatusBill.DECLINED);
                }
                if (datum.status.equals("VOIDED")) {
                    bill.setStatusBill(StatusBill.VOIDED);
                    billRepository.save(bill);
                    statusBill.set(StatusBill.VOIDED);
                }
            });
        }
        return statusBill.get();
    }

    public boolean cancelTransaction(Long idBill, String referenceTransaction) {
        Wompi wompi = searchWompi(referenceTransaction);
        AtomicReference<String> idTransaction = new AtomicReference<>("");
        wompi.data.forEach(datum -> idTransaction.set(datum.id));
        log.info("idTransaction" + idTransaction);
        String url = "https://sandbox.wompi.co/v1/transactions/" + idTransaction + "/void";
        // create headers
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        headers.set("Authorization", "Bearer prv_test_2Vjk6fZaET3oNejoRegTLiJO4Lk6yyjW");
        RestTemplate restTemplate = new RestTemplate();
        HttpEntity<Object> entity = new HttpEntity<>(headers);
        try {
            restTemplate.exchange(url, HttpMethod.POST, entity, Object.class);
            //this time is mandatory we've to wait to wompi do the changes
            Thread.sleep(5000);
        } catch (Exception e) {
            return false;
        }
        Bill bill = billRepository.findByIdBill(idBill);
        bill.setIdBill(idBill);
        bill.setStatusBill(StatusBill.VOIDED);
        billRepository.save(bill);
        return true;
    }

    @Override
    public BillUserDTO update(Long idBill, Bill bill) {
        log.info("Updating bill with id: " + idBill);
        int totalOrder = 0;
        Bill billOld = billRepository.findByIdBill(idBill);
        if (bill.getStatusBill().equals(StatusBill.PAID)) {
            Collection<Orders> orders = ordersRepository.findByIdBill(idBill);
            for (Orders order : orders) {
                totalOrder += order.getTotal();
            }
            billOld.setTotalPrice(totalOrder);
        }
        billOld.setPayMode(bill.getPayMode());
        billOld.setNoTable(bill.getNoTable());
        billOld.setStatusBill(bill.getStatusBill());
        billOld.setDate(bill.getDate());
        return convertBillToDTO(billRepository.save(billOld));
    }

    @Override
    public BillUserDTO updateStatusBill(Long idBill, StatusBill statusBill) {
        log.info("Updating bill with id: " + idBill);
        Bill billOld = billRepository.findByIdBill(idBill);
        billOld.setStatusBill(statusBill);
        return convertBillToDTO(billRepository.save(billOld));
    }

    @Override
    public Boolean delete(Long idBill) {
        log.info("Deleting the bill id: " + idBill);
        if (billRepository.existsById(idBill)) {
            Bill bill = billRepository.findById(idBill).get();
            bill.setStatusBill(StatusBill.DELETED);
            return true;
        } else {
            return false;
        }
    }

    @Override
    public Boolean exist(Long idBill) {
        log.info("Searching bills by id: " + idBill);
        return billRepository.existsById(idBill);
    }

    @Override
    public UserBillOrdersDTO findByIdBill(Long idBill) {
        return convertBillOrderToDTO(billRepository.findByIdBill(idBill));
    }

    @Override
    public Collection<UserBillOrdersDTO> findByOrder(StatusOrder statusOrder, String startDate, String endDate) {
        try {
            log.info("Searching bills by StatusOrder DateBetween");
            //Important this method has a specific filter to show only the available statusBill (PAID 0, PENDING 1, ACCEPTED 6)
            return billRepository.findByDateBetweenAndStatusOrder(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(startDate), new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(endDate), statusOrder.ordinal()).stream().map(this::convertBillOrderToDTO).collect(Collectors.toList());
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public EntireBillOrderDTO findByBillParams(String username, StatusBill statusBill, String startDate, String endDate, int number, int page) {

        boolean fullSearch = username != null && statusBill != null && startDate != null && endDate != null;
        boolean withoutParams = username == null && statusBill == null && startDate == null && endDate == null;
        boolean byUsername = username != null && statusBill == null && startDate == null && endDate == null;
        boolean byStatusBill = username == null && statusBill != null && startDate == null && endDate == null;
        boolean byUsername_StatusBill = username != null && statusBill != null && startDate == null && endDate == null;
        boolean byDate = username == null && statusBill == null && startDate != null && endDate != null;
        boolean byUsername_Date = username != null && statusBill == null && startDate != null && endDate != null;
        boolean byStatusBill_Date = username == null && statusBill != null && startDate != null && endDate != null;

        PageRequest pageRequest = PageRequest.of(page, 5);
        EntireBillOrderDTO entity = new EntireBillOrderDTO();

        if (fullSearch) {
            try {
                log.info("Searching bills by User StatusBill DateBetween");
                Page<Bill> pages = billRepository.findByIdUserAndStatusBillAndDateBetween(username, statusBill.ordinal(),
                        new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(startDate), new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(endDate), pageRequest);
                List<UserBillOrdersDTO> ListBillDTO = pages.getContent().stream().map(this::convertBillOrderToDTO).collect(Collectors.toList());
                entity.setListBill(ListBillDTO);
                entity.setPages(pages.getTotalPages());
            } catch (ParseException e) {
                e.printStackTrace();
            }
            return entity;
        }
        if (withoutParams) {
            log.info("Searching bills");
            Page<Bill> pages = billRepository.findAll(pageRequest);
            List<UserBillOrdersDTO> ListBillDTO = pages.getContent().stream().map(this::convertBillOrderToDTO).collect(Collectors.toList());
            entity.setListBill(ListBillDTO);
            entity.setPages(pages.getTotalPages());
            return entity;
        }
        if (byUsername) {
            if (number == 1) {
                log.info("Searching bills by User Client");
                Page<Bill> pages = billRepository.findByIdUser(username, pageRequest);

                List<UserBillOrdersDTO> ListBillDTO = pages.getContent().stream().map(this::convertBillOrderToDTO).collect(Collectors.toList());
                entity.setListBill(ListBillDTO);
                entity.setPages(pages.getTotalPages());
            } else {
                log.info("Searching bills by User Admin");
                Page<Bill> pages = billRepository.findByIdUserAdmin(username, pageRequest);
                List<UserBillOrdersDTO> ListBillDTO = pages.getContent().stream().map(this::convertBillOrderToDTO).collect(Collectors.toList());
                entity.setListBill(ListBillDTO);
                entity.setPages(pages.getTotalPages());
            }
            return entity;
        }
        if (byStatusBill) {
            log.info("Searching bills by StatusBill");
            Page<Bill> pages = billRepository.findByStatusBill(statusBill, pageRequest);
            List<UserBillOrdersDTO> ListBillDTO = pages.getContent().stream().map(this::convertBillOrderToDTO).collect(Collectors.toList());
            entity.setListBill(ListBillDTO);
            entity.setPages(pages.getTotalPages());
            return entity;
        }

        if (byUsername_StatusBill) {
            log.info("Searching bills by User StatusBill");
            Page<Bill> pages = billRepository.findByIdUserAndStatusBill(username, statusBill.ordinal(), pageRequest);
            List<UserBillOrdersDTO> ListBillDTO = pages.getContent().stream().map(this::convertBillOrderToDTO).collect(Collectors.toList());
            entity.setListBill(ListBillDTO);
            entity.setPages(pages.getTotalPages());
            return entity;
        }
        if (byDate) {
            try {
                log.info("Searching bills by DateBetween");
                Page<Bill> pages = billRepository.findByDateBetween(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(startDate),
                        new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(endDate), pageRequest);
                List<UserBillOrdersDTO> ListBillDTO = pages.getContent().stream().map(this::convertBillOrderToDTO).collect(Collectors.toList());
                entity.setListBill(ListBillDTO);
                entity.setPages(pages.getTotalPages());
                return entity;
            } catch (ParseException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        if (byUsername_Date) {
            try {
                log.info("Searching bills by User DateBetween");
                Page<Bill> pages = billRepository.findByDateBetweenAndIdUser(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(startDate),
                        new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(endDate), username, pageRequest);
                List<UserBillOrdersDTO> ListBillDTO = pages.getContent().stream().map(this::convertBillOrderToDTO).collect(Collectors.toList());
                entity.setListBill(ListBillDTO);
                entity.setPages(pages.getTotalPages());
                return entity;
            } catch (ParseException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        if (byStatusBill_Date) {
            try {
                log.info("Searching bills by StatusBill DateBetween");
                Page<Bill> pages = billRepository.findByDateBetweenAndStatusBill(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(startDate),
                        new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(endDate), statusBill, pageRequest);
                List<UserBillOrdersDTO> ListBillDTO = pages.getContent().stream().map(this::convertBillOrderToDTO).collect(Collectors.toList());
                entity.setListBill(ListBillDTO);
                entity.setPages(pages.getTotalPages());
                return entity;
            } catch (ParseException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        return null;
    }

    private BillUserDTO convertBillToDTO(Bill bill) {

        BillUserDTO billUser = new BillUserDTO();
        billUser.setIdBill(bill.getIdBill());
        billUser.setReferenceTransaction(bill.getReferenceTransaction());
        billUser.setNoTable(bill.getNoTable());
        billUser.setTotalPrice(bill.getTotalPrice());

        convertPartBillDTO(bill, billUser);

        return billUser;
    }

    @Transactional(readOnly = true)
    UserBillOrdersDTO convertBillOrderToDTO(Bill bill) {
        UserBillOrdersDTO billOrder = new UserBillOrdersDTO();
        BillUserDTO billUser = new BillUserDTO();
        billUser.setIdBill(bill.getIdBill());
        billUser.setNoTable(bill.getNoTable());
        billUser.setReferenceTransaction(bill.getReferenceTransaction());
        convertPartBillDTO(bill, billUser);
        Collection<OrdersDTO> orders = ordersRepository.findByIdBill(bill.getIdBill()).stream().map(this::convertOrderToDTO).collect(Collectors.toList());
        int totalPrice = 0;
        for (OrdersDTO order : orders) {
            PriceProductHistory priceProductHistories = priceProductHistory.findProductHistoryByIdProductAndDate(order.getProduct().getIdProduct(), bill.getDate());
            if (priceProductHistories != null) {
                order.getProduct().setPrice(priceProductHistories.getPrice());
            }
            if (order.getAdditional() != null) {
                order.getAdditional().forEach(additional -> {
                    PriceAdditionalHistory priceAdditionalHistories = priceAdditionalHistory.findAdditionalHistoryByIdAdditionalAndDate(additional.getIdAdditional(), bill.getDate());
                    additional.setPrice(priceAdditionalHistories.getPrice());
                });
            }
            totalPrice += order.getTotal();
        }
        billUser.setTotalPrice(totalPrice);
        billOrder.setBillUserDTO(billUser);
        billOrder.setOrdersDTO(orders);
        return billOrder;
    }

    private OrdersDTO convertOrderToDTO(Orders orders) {
        OrdersDTO billOrder = new OrdersDTO();
        billOrder.setIdOrder(orders.getIdOrder());
        billOrder.setStatusOrder(orders.getStatusOrder());
        billOrder.setAmount(orders.getAmount());
        billOrder.setTotal(orders.getTotal());
        billOrder.setProduct(orders.getProduct());
        Collection<Additional> additional = new ArrayList<>(orders.getAdditional());
        billOrder.setAdditional(additional);
        return billOrder;
    }

    private void convertPartBillDTO(Bill bill, BillUserDTO billUser) {
        if (bill.getUser() != null) {
            UserForBillDTO userForBill = new UserForBillDTO();
            userForBill.setIdUser(bill.getUser().getIdUser());
            userForBill.setUrlImage(bill.getUser().getUrlImage());
            userForBill.setUsername(bill.getUser().getUsername());
            userForBill.setName(bill.getUser().getName());
            billUser.setUserForBill(userForBill);
        }

        PayMode payMode = new PayMode();
        payMode.setIdPayMode(bill.getPayMode().getIdPayMode());
        payMode.setName(bill.getPayMode().getName());
        payMode.setStatus(bill.getPayMode().getStatus());

        billUser.setPayMode(payMode);

        billUser.setDate(bill.getDate());
        billUser.setStatusBill(bill.getStatusBill());
    }
}
