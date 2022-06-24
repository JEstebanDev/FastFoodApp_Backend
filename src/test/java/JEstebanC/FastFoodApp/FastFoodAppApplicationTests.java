/*package JEstebanC.FastFoodApp;

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

}