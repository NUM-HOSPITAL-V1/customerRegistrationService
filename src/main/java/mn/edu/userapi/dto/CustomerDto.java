package mn.edu.userapi.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import mn.edu.userapi.model.enums.Gender;
import mn.edu.userapi.model.enums.Profession;
import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CustomerDto {
    @Schema(description = "Харилцагчийн ID", example = "AB123CD456")
    private String customerId;
    @Schema(description = "Регистрийн дугаар", example = "УД92010199")
    private String nationalId;
    @Schema(description = "Овог", example = "Бат")
    private String surname;
    @Schema(description = "Нэр", example = "Болд")
    private String firstName;

    @Schema(description = "Төрсөн огноо", example = "1990-01-01")
    private String dateOfBirth;
    @Schema(description = "Нас", example = "35")
    private String age;
    @Schema(description = "Хүйс", example = "MALE")
    private String gender;
    @Schema(description = "Утасны дугаар", example = "99112233")
    private String phoneNumber;
    @Schema(description = "Имэйл", example = "bold@example.com")
    private String email;
    @Schema(description = "Аймаг/Хот", example = "Улаанбаатар")
    private String province;
    @Schema(description = "Дүүрэг", example = "Баянгол")
    private String district;
    @Schema(description = "Хороо", example = "1-р хороо")
    private String subdistrict;
    @Schema(description = "Хаяг дэлгэрэнгүй", example = "23-р байр 5 тоот")
    private String addressDetail;
    @Schema(description = "Мэргэжил", example = "DOCTOR")
    private String profession;
    @Schema(description = "СИСИ ID", example = "SI123456")
    private String sisiId;
    @Schema(description = "Хэрэглэгчийн ID", example = "USER1234")
    private String userId;

    public String getCustomerId() {
        return customerId;
    }

    public String getNationalId() {
        return nationalId;
    }

    public String getSurname() {
        return surname;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public String getAge() {
        return age;
    }

    public String getGender() {
        return gender;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public String getProvince() {
        return province;
    }

    public String getDistrict() {
        return district;
    }

    public String getSubdistrict() {
        return subdistrict;
    }

    public String getAddressDetail() {
        return addressDetail;
    }

    public String getProfession() {
        return profession;
    }

    public String getSisiId() {
        return sisiId;
    }

    public String getUserId() {
        return userId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public void setNationalId(String nationalId) {
        this.nationalId = nationalId;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public void setSubdistrict(String subdistrict) {
        this.subdistrict = subdistrict;
    }

    public void setAddressDetail(String addressDetail) {
        this.addressDetail = addressDetail;
    }

    public void setProfession(String profession) {
        this.profession = profession;
    }

    public void setSisiId(String sisiId) {
        this.sisiId = sisiId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}