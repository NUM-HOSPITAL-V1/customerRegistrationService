package mn.edu.userapi.controller;

import lombok.RequiredArgsConstructor;
import mn.edu.userapi.dto.CustomerSummaryDto;
import mn.edu.userapi.model.Customer;
import mn.edu.userapi.service.CustomerService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/internal/customers")
@RequiredArgsConstructor
public class InternalCustomerController {

    private final CustomerService customerService;

    @GetMapping("/{id}")
    public ResponseEntity<CustomerSummaryDto> findCustomer(@PathVariable String id) {
        Customer customer = customerService.getCustomerById(id);
        if (customer == null) {
            return ResponseEntity.notFound().build();
        }

        CustomerSummaryDto response = new CustomerSummaryDto(
                customer.getCustomerId(),
                customer.getUserId(),
                customer.getFirstName(),
                customer.getSurname(),
                customer.getPhoneNumber(),
                customer.getEmail()
        );
        return ResponseEntity.ok(response);
    }

    @GetMapping("/health")
    public ResponseEntity<String> health() {
        return ResponseEntity.ok("ok");
    }
}
