package mn.edu.userapi.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import mn.edu.userapi.model.enums.Gender;
import mn.edu.userapi.model.enums.Profession;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CustomerDto {
    private String customerId;
    private String nationalId;
    private String surname;
    private String firstName;
    private LocalDate dateOfBirth;
    private Integer age;
    private Gender gender;
    private String phoneNumber;
    private String email;
    private String province;
    private String district;
    private String subdistrict;
    private String addressDetail;
    private Profession profession;
    private String sisiId;
    private String userId;
}