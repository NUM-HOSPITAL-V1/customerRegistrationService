package mn.edu.userapi.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CustomerSummaryDto {
    private String customerId;
    private String userId;
    private String firstName;
    private String surname;
    private String phoneNumber;
    private String email;
}
