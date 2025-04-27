package mn.edu.userapi.service;


import io.vertx.core.json.JsonArray;
import mn.edu.userapi.dto.CustomerDto;
import mn.edu.userapi.model.Customer;

import java.util.List;

public interface CustomerService {
    String createCustomerId();
    JsonArray getAllCustomers() throws Exception;
    Customer getCustomerById(String id);
    boolean saveCustomer(CustomerDto customerDto) throws Exception;
    Customer mapDtoToEntity(CustomerDto dto) throws Exception;
    CustomerDto mapEntityToDto(Customer customer) throws Exception;
    void deleteCustomer(String id) throws Exception;
    void updateCustomer(String id, CustomerDto customerDto) throws Exception;
}