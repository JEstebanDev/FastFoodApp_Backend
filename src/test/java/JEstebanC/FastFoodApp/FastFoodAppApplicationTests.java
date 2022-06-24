package JEstebanC.FastFoodApp;

import JEstebanC.FastFoodApp.service.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class FastFoodAppApplicationTests {
    @Autowired
    UserServiceImp userServiceImp;
    @Autowired
    CategoryServiceImp categoryServiceImp;
    @Autowired
    ProductServiceImp productServiceImp;
    @Autowired
    AdditionalServiceImp additionalServiceImp;
    @Autowired
    BillServiceImp billServiceImp;
    @Autowired
    CloudinaryService cloudinaryService;
    @Autowired
    CompanyServiceImp companyServiceImp;
    @Autowired
    OrdersServiceImp orderServiceImp;
    @Autowired
    PayModeServiceImp payModeServiceImp;
    @Autowired
    ReportServiceImp reportServiceImp;
    @Autowired
    SubscriberServiceImp subscriberServiceImp;
    @Autowired
    TaxServiceImp taxServiceImp;


    @Test
    void contextLoads() {
        assertThat(userServiceImp).isNotNull();
        assertThat(categoryServiceImp).isNotNull();
        assertThat(productServiceImp).isNotNull();
        assertThat(additionalServiceImp).isNotNull();
        assertThat(billServiceImp).isNotNull();
        assertThat(cloudinaryService).isNotNull();
        assertThat(companyServiceImp).isNotNull();
        assertThat(orderServiceImp).isNotNull();
        assertThat(payModeServiceImp).isNotNull();
        assertThat(reportServiceImp).isNotNull();
        assertThat(subscriberServiceImp).isNotNull();
        assertThat(taxServiceImp).isNotNull();
    }

}