package mn.edu.userapi.service.impl;

import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import lombok.RequiredArgsConstructor;
import mn.edu.userapi.dto.CustomerDto;
import mn.edu.userapi.model.Customer;
import mn.edu.userapi.repository.CustomerRepository;
import mn.edu.userapi.service.CustomerService;
import mn.edu.userapi.util.CryptoUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CustomerServiceImpl implements CustomerService {
    private static final Logger logger = LoggerFactory.getLogger(CustomerServiceImpl.class);

    private final CustomerRepository customerRepository;
    private final CryptoUtil cryptoUtil;

    @Override
    public String createCustomerId() {
        return customerRepository.createCustomerId();
    }

    @Override
    public JsonArray getAllCustomers() throws Exception {
        JsonArray ja = new JsonArray();
        try {
            List<Object[]> customerData = customerRepository.fetchAllCustomers();

            for (Object[] obj : customerData) {
                JsonObject jobj = new JsonObject();

                jobj.put("id", obj[0] != null ? obj[0].toString() : "");
                jobj.put("surname", obj[1] != null ? obj[1].toString() : "");
                jobj.put("firstName", obj[2] != null ? obj[2].toString() : "");

                ja.add(jobj);
            }
        } catch (Exception e) {
            logger.error("Error in selecting customers: {}", e.getMessage());
            throw e;
        }
        return ja;
    }

    @Override
    public Customer getCustomerById(String id) {
        try {
            return customerRepository.fetchCustomer(id).orElse(null);
        } catch (Exception e) {
            logger.error("Error in selecting customer by ID: {}", e.getMessage());
            return null;
        }
    }

    @Override
    public boolean saveCustomer(CustomerDto customerDto) throws Exception {
        try {
            Customer customer = mapDtoToEntity(customerDto);
            customerRepository.save(customer);
            return true;
        } catch (Exception e) {
            logger.error("Error in saving customer: {}", e.getMessage());
            return false;
        }
    }

    @Override
    public Customer mapDtoToEntity(CustomerDto dto) throws Exception {
        Customer customer = new Customer();
        customer.setCustomerId(dto.getCustomerId());
        customer.setSurname(dto.getSurname());
        customer.setFirstName(dto.getFirstName());
        customer.setDateOfBirth(dto.getDateOfBirth());
        customer.setAge(dto.getAge());
        customer.setGender(dto.getGender());
        customer.setPhoneNumber(dto.getPhoneNumber());
        customer.setEmail(dto.getEmail());
        customer.setProvince(dto.getProvince());
        customer.setDistrict(dto.getDistrict());
        customer.setSubdistrict(dto.getSubdistrict());
        customer.setAddressDetail(dto.getAddressDetail());
        customer.setProfession(dto.getProfession());
        customer.setSisiId(dto.getSisiId());
        customer.setUserId(dto.getUserId());
        customer.setNationalId(dto.getNationalId());

        // Encrypt sensitive data
        customer.setNationalId(cryptoUtil.encrypt_base64(dto.getNationalId()));

        logger.info("requestDTO={}, requestCustomer={}", dto, customer);
        return customer;
    }

    @Override
    public CustomerDto mapEntityToDto(Customer customer) throws Exception {
        CustomerDto dto = new CustomerDto();
        dto.setCustomerId(customer.getCustomerId());
        dto.setSurname(customer.getSurname());
        dto.setFirstName(customer.getFirstName());
        dto.setDateOfBirth(customer.getDateOfBirth());
        dto.setAge(customer.getAge());
        dto.setGender(customer.getGender());
        dto.setPhoneNumber(customer.getPhoneNumber());
        dto.setEmail(customer.getEmail());
        dto.setProvince(customer.getProvince());
        dto.setDistrict(customer.getDistrict());
        dto.setSubdistrict(customer.getSubdistrict());
        dto.setAddressDetail(customer.getAddressDetail());
        dto.setProfession(customer.getProfession());
        dto.setSisiId(customer.getSisiId());
        dto.setUserId(customer.getUserId());

        // Decrypt sensitive data
        dto.setNationalId(cryptoUtil.decrypt_base64(customer.getNationalId()));

        return dto;
    }

    @Override
    public void deleteCustomer(String id) throws Exception {
        customerRepository.deleteById(id);
    }

    @Override
    public void updateCustomer(String id, CustomerDto customerDto) throws Exception {
        Customer existingCustomer = customerRepository.fetchCustomer(id).orElse(null);
        logger.info("current_user={}", existingCustomer);

        existingCustomer.setNationalId(cryptoUtil.encrypt_base64(customerDto.getNationalId()));
        existingCustomer.setAge(customerDto.getAge());
        existingCustomer.setDistrict(customerDto.getDistrict());
        existingCustomer.setEmail(customerDto.getEmail());
        existingCustomer.setAddressDetail(customerDto.getAddressDetail());
        existingCustomer.setDateOfBirth(customerDto.getDateOfBirth());
        existingCustomer.setGender(customerDto.getGender());
        existingCustomer.setFirstName(customerDto.getFirstName());
        existingCustomer.setPhoneNumber(customerDto.getPhoneNumber());
        existingCustomer.setProfession(customerDto.getProfession());
        existingCustomer.setSisiId(customerDto.getSisiId());
        existingCustomer.setProvince(customerDto.getProvince());
        existingCustomer.setSubdistrict(customerDto.getSubdistrict());
        existingCustomer.setSurname(customerDto.getSurname());

        customerRepository.save(existingCustomer);
    }

    @Override
    public boolean checkCustomer(String nat_id) throws Exception {
        String encrypted_natID = cryptoUtil.encrypt_base64(nat_id);
        return customerRepository.existsByNationalId(encrypted_natID);
    }

    @Override
    public JsonArray searchCustomers(String param) throws Exception {
        try {
            List<Customer> customers = customerRepository.searchCustomers(param, cryptoUtil.encrypt_base64(param));
            JsonArray ja = new JsonArray();
            for(int i=0; i<customers.size(); i++) {
                Customer currentCust = customers.get(i);
                JsonObject jobj = new JsonObject();

                jobj.put("customerId", currentCust.getCustomerId());
                jobj.put("surname", currentCust.getSurname());
                jobj.put("firstName", currentCust.getFirstName());
                jobj.put("dateOfBirth", currentCust.getDateOfBirth());
                jobj.put("age", currentCust.getAge());
                jobj.put("gender", currentCust.getGender());
                jobj.put("phoneNumber", currentCust.getPhoneNumber());
                jobj.put("email", currentCust.getEmail());
                jobj.put("province", currentCust.getProvince());
                jobj.put("district", currentCust.getDistrict());
                jobj.put("subdistrict", currentCust.getSubdistrict());
                jobj.put("addressDetail", currentCust.getAddressDetail());
                jobj.put("profession", currentCust.getProfession());
                jobj.put("sisiId", currentCust.getSisiId());
                jobj.put("userId", currentCust.getUserId());
                jobj.put("nationalId", cryptoUtil.decrypt_base64(currentCust.getNationalId()));

                ja.add(jobj);
            }

            return ja;
        } catch (Exception e) {
            logger.error("error in search[error={}]", e.getMessage());
            return null;
        }
    }
}