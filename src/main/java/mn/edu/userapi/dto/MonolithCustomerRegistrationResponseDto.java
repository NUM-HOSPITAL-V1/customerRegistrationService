package mn.edu.userapi.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class MonolithCustomerRegistrationResponseDto {
    private String customerId;
    private String status;
}
