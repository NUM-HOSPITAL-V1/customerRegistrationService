package mn.edu.userapi.controller;

import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import lombok.RequiredArgsConstructor;
import mn.edu.userapi.dto.CustomerDto;
import mn.edu.userapi.model.Customer;
import mn.edu.userapi.model.enums.Gender;
import mn.edu.userapi.model.enums.Profession;
import mn.edu.userapi.service.CustomerService;
import mn.edu.userapi.util.CryptoUtil;
import mn.edu.userapi.util.HttpClientUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.KeyPair;
import java.time.LocalDate;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/hospital")
@RequiredArgsConstructor
public class HospitalApiController {
    private final Logger logger = LoggerFactory.getLogger(HospitalApiController.class);

    private final CustomerService customerService;
    private final CryptoUtil cryptoUtil;
    private final HttpClientUtil httpClientUtil;

    @PostMapping("/search")
    public ResponseEntity<JsonObject> onSearch(@RequestBody JsonObject request) {
        // This method was not implemented in the original code
        return null;
    }

    @PostMapping("/register/{id}")
    public ResponseEntity<JsonObject> onRegisterId(@PathVariable String id) throws Exception {
        try {
            Map<String, String> headers = new HashMap<>();
            headers.put("Content-Type", "application/json");
            JsonObject jwt = new JsonObject(httpClientUtil.httpRequest("", id, "POST", headers));

            JsonObject jres = new JsonObject();
            // Process jwt if needed

            return ResponseEntity.ok(jres);
        } catch (Exception e) {
            logger.error("Error in register by id: {}", e.getMessage());
            return ResponseEntity.internalServerError().build();
        }
    }

    @GetMapping("/register")
    public ResponseEntity<JsonObject> onRegister() {
        try {
            Map<String, String> headers = new HashMap<>();
            headers.put("Content-Type", "application/json");
            JsonObject jwt = new JsonObject(httpClientUtil.httpRequest("", null, "POST", headers));

            JsonObject jres = new JsonObject();
            // Process jwt if needed

            return ResponseEntity.ok(jres);
        } catch (Exception e) {
            logger.error("Error in register by national ID: {}", e.getMessage());
            return ResponseEntity.internalServerError().build();
        }
    }

    @GetMapping("/fetch")
    public ResponseEntity<JsonObject> onFetch() {
        try {
            JsonArray ja = customerService.getAllCustomers();
            JsonObject response = new JsonObject();
            response.put("body", ja);
            response.put("code", "200");

            return ResponseEntity.ok(response);
        } catch (Exception e) {
            logger.error("Error in fetch all customers: {}", e.getMessage());
            return ResponseEntity.internalServerError().build();
        }
    }

    @GetMapping("/fetch/{id}")
    public ResponseEntity<JsonObject> onFetchCustomer(@PathVariable String id) {
        try {
            Customer customer = customerService.getCustomerById(id);

            if (customer == null) {
                return ResponseEntity.ok(new JsonObject().put("message", "No customer found").put("code", "403"));
            }

            CustomerDto customerDto = customerService.mapEntityToDto(customer);

            JsonObject response = new JsonObject();
            response.put("customerId", customerDto.getCustomerId());
            response.put("surname", customerDto.getSurname());
            response.put("firstName", customerDto.getFirstName());
            response.put("dateOfBirth", customerDto.getDateOfBirth().toString());
            response.put("age", customerDto.getAge());
            response.put("gender", customerDto.getGender().toString());
            response.put("phoneNumber", customerDto.getPhoneNumber());
            response.put("email", customerDto.getEmail());
            response.put("province", customerDto.getProvince());
            response.put("district", customerDto.getDistrict());
            response.put("subdistrict", customerDto.getSubdistrict());
            response.put("addressDetail", customerDto.getAddressDetail());
            response.put("profession", customerDto.getProfession().toString());
            response.put("sisiId", customerDto.getSisiId());
            response.put("userId", customerDto.getUserId());
            response.put("nationalId", customerDto.getNationalId());

            return ResponseEntity.ok(response);
        } catch (Exception e) {
            logger.error("Error in fetch customer: {}", e.getMessage());
            return ResponseEntity.internalServerError().build();
        }
    }

    @PostMapping("/save/customer")
    public ResponseEntity<JsonObject> onSave(@RequestBody JsonObject request) {
        try {
            String customerId = customerService.createCustomerId();

            CustomerDto customerDto = new CustomerDto();
            customerDto.setCustomerId(customerId);
            customerDto.setSurname(request.getString("surname"));
            customerDto.setFirstName(request.getString("firstName"));
            customerDto.setDateOfBirth(LocalDate.parse(request.getString("dateOfBirth")));
            customerDto.setAge(request.getInteger("age"));
            customerDto.setGender(Gender.valueOf(request.getString("gender")));
            customerDto.setPhoneNumber(request.getString("phoneNumber"));
            customerDto.setEmail(request.getString("email", ""));
            customerDto.setProvince(request.getString("province"));
            customerDto.setDistrict(request.getString("district"));
            customerDto.setSubdistrict(request.getString("subdistrict"));
            customerDto.setAddressDetail(request.getString("addressDetail"));
            customerDto.setProfession(Profession.valueOf(request.getString("profession")));
            customerDto.setSisiId(request.getString("sisiId"));
            customerDto.setUserId(request.getString("userId"));
            customerDto.setNationalId(request.getString("nationalId"));

            if (customerService.saveCustomer(customerDto)) {
                return ResponseEntity.ok(new JsonObject().put("message", "Customer saved successfully").put("code", "200"));
            } else {
                return ResponseEntity.internalServerError().build();
            }

        } catch (Exception e) {
            logger.error("Error in saving customer: {}", e.getMessage());
            return ResponseEntity.internalServerError().build();
        }
    }

    @PostMapping("/test")
    public ResponseEntity<JsonObject> onTest() throws Exception {
        KeyPair keyPair = cryptoUtil.generateKeys();

        String publicKeyStr = Base64.getEncoder().encodeToString(keyPair.getPublic().getEncoded());
        String privateKeyStr = Base64.getEncoder().encodeToString(keyPair.getPrivate().getEncoded());

        JsonObject jwt = new JsonObject()
                .put("public", publicKeyStr)
                .put("private", privateKeyStr);

        return ResponseEntity.ok(jwt);
    }
}