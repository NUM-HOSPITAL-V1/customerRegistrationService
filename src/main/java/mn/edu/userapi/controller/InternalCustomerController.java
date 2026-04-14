package mn.edu.userapi.controller;

import lombok.RequiredArgsConstructor;
import mn.edu.userapi.dto.CustomerDto;
import mn.edu.userapi.dto.MonolithCustomerRegistrationResponseDto;
import mn.edu.userapi.dto.MonolithPatientRegistrationDto;
import mn.edu.userapi.dto.CustomerSummaryDto;
import mn.edu.userapi.model.Customer;
import mn.edu.userapi.service.CustomerService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.time.Period;

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

    @PostMapping("/register-profile")
    public ResponseEntity<?> registerProfile(@RequestBody MonolithPatientRegistrationDto request) throws Exception {
        if (request.getRegister() == null || request.getRegister().isBlank()) {
            return ResponseEntity.badRequest().body("register is required");
        }

        if (customerService.checkCustomer(request.getRegister())) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Customer profile already exists for register");
        }

        String sisiId = normalizeSisiId(request.getSisiID());
        if (!sisiId.isBlank() && customerService.checkCustomerSisiId(sisiId)) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Customer profile already exists for sisiID");
        }

        String customerId = customerService.createCustomerId();

        CustomerDto dto = new CustomerDto();
        dto.setCustomerId(customerId);
        dto.setNationalId(request.getRegister());
        dto.setSurname(defaultString(request.getLastname()));
        dto.setFirstName(defaultString(request.getFirstname()));
        dto.setDateOfBirth(defaultDateOfBirth(request.getBirthOfDate()));
        dto.setAge(calculateAge(dto.getDateOfBirth()));
        dto.setGender(mapGender(request.getGender()));
        dto.setPhoneNumber(defaultString(request.getPhoneNumber()));
        dto.setEmail(defaultString(request.getEmail()));
        dto.setProvince(extractAddressPart(request.getAddress(), 0, "UNSPECIFIED"));
        dto.setDistrict(extractAddressPart(request.getAddress(), 1, "UNSPECIFIED"));
        dto.setSubdistrict(extractAddressPart(request.getAddress(), 2, "UNSPECIFIED"));
        dto.setAddressDetail(defaultAddressDetail(request.getAddress()));
        dto.setProfession(mapProfession(request.getType(), request.getOccupation()));
        dto.setSisiId(sisiId.isBlank() ? customerId : sisiId);
        dto.setUserId("");

        if (!customerService.saveCustomer(dto)) {
            return ResponseEntity.internalServerError().body("Failed to create customer profile");
        }

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new MonolithCustomerRegistrationResponseDto(customerId, "created"));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCustomer(@PathVariable String id) throws Exception {
        Customer customer = customerService.getCustomerById(id);
        if (customer == null) {
            return ResponseEntity.notFound().build();
        }

        customerService.deleteCustomer(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/health")
    public ResponseEntity<String> health() {
        return ResponseEntity.ok("ok");
    }

    private String normalizeSisiId(String sisiId) {
        return sisiId == null ? "" : sisiId.trim();
    }

    private String defaultString(String value) {
        return value == null ? "" : value.trim();
    }

    private String defaultDateOfBirth(String value) {
        if (value == null || value.isBlank()) {
            return "1900-01-01";
        }
        return value.trim();
    }

    private String defaultAddressDetail(String address) {
        String normalized = defaultString(address);
        return normalized.isBlank() ? "UNSPECIFIED" : normalized;
    }

    private String extractAddressPart(String address, int index, String fallback) {
        String normalized = defaultString(address);
        if (normalized.isBlank()) {
            return fallback;
        }

        String[] parts = normalized.split(",");
        if (index >= parts.length) {
            return fallback;
        }

        String part = parts[index].trim();
        return part.isBlank() ? fallback : part;
    }

    private String calculateAge(String birthOfDate) {
        try {
            LocalDate date = LocalDate.parse(birthOfDate);
            int years = Math.max(0, Period.between(date, LocalDate.now()).getYears());
            return String.valueOf(years);
        } catch (Exception ignored) {
            return "0";
        }
    }

    private String mapGender(String gender) {
        if (gender == null || gender.isBlank()) {
            return "OTHER";
        }

        return switch (gender.trim().toLowerCase()) {
            case "male", "эрэгтэй" -> "MALE";
            case "female", "эмэгтэй" -> "FEMALE";
            default -> "OTHER";
        };
    }

    private String mapProfession(String type, String occupation) {
        if (type != null && !type.isBlank()) {
            return switch (type.trim().toLowerCase()) {
                case "teacher" -> "TEACHER";
                case "staff", "employee" -> "EMPLOYEE";
                case "student" -> "STUDENT";
                default -> mapOccupation(occupation);
            };
        }

        return mapOccupation(occupation);
    }

    private String mapOccupation(String occupation) {
        if (occupation == null || occupation.isBlank()) {
            return "EMPLOYEE";
        }

        return switch (occupation.trim().toLowerCase()) {
            case "doctor", "эмч" -> "DOCTOR";
            case "nurse", "сувилагч" -> "NURSE";
            case "teacher", "багш" -> "TEACHER";
            case "student", "оюутан" -> "STUDENT";
            default -> "EMPLOYEE";
        };
    }
}
