package mn.edu.userapi.util;


import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import lombok.RequiredArgsConstructor;
import mn.edu.userapi.model.Customer;
import mn.edu.userapi.repository.CustomerRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class DatabaseUtil {
    private static final Logger logger = LoggerFactory.getLogger(DatabaseUtil.class);

    private final CustomerRepository customerRepository;

    public String createCustomerId() {
        return customerRepository.createCustomerId();
    }

    public void insertCustomer(Customer newCustomer) {
        customerRepository.save(newCustomer);
    }

    public JsonArray selectAllCustomers() throws Exception {
        JsonArray ja = new JsonArray();
        try {
            List<Object[]> customerData = customerRepository.fetchAllCustomers();

            for(Object[] obj : customerData) {
                JsonObject jobj = new JsonObject();

                jobj.put("id", obj[0] != null ? obj[0].toString() : "");
                jobj.put("surname", obj[1] != null ? obj[1].toString() : "");
                jobj.put("firstName", obj[2] != null ? obj[2].toString() : "");

                ja.add(jobj);
            }
        } catch (Exception e) {
            logger.error("Error in selecting all customers: {}", e.getMessage());
            throw e;
        }
        return ja;
    }

    public Customer selectCustomerById(String id) {
        try {
            return customerRepository.fetchCustomer(id).orElse(null);
        } catch (Exception e) {
            logger.error("Error in selecting customer by ID: {}", e.getMessage());
            return null;
        }
    }

    public boolean insertNewCustomer(Customer newCustomer) {
        try {
            customerRepository.save(newCustomer);
            return true;
        } catch (Exception e) {
            logger.error("Error in inserting new customer: {}", e.getMessage());
            return false;
        }
    }
}