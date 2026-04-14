package mn.edu.userapi.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MonolithPatientRegistrationDto {
    private String firstname;
    private String lastname;
    private String register;
    private String phoneNumber;
    private String email;
    private String address;
    private String occupation;
    private String birthOfDate;
    private String gender;
    private String type;
    private String sisiID;
}
