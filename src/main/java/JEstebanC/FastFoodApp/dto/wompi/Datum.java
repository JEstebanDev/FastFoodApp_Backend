package JEstebanC.FastFoodApp.dto.wompi;

import java.util.Date;

public class Datum{
    public String id;
    public Date created_at;
    public Date finalized_at;
    public int amount_in_cents;
    public String reference;
    public String customer_email;
    public String currency;
    public String payment_method_type;
    public PaymentMethod payment_method;
    public String status;
    public Object status_message;
    public Object shipping_address;
    public String redirect_url;
    public Object payment_source_id;
    public Object payment_link_id;
    public CustomerData customer_data;
    public Object billing_data;
}
