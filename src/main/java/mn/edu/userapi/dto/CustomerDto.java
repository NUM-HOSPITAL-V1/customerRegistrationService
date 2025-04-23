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
    private String dateOfBirth;
    private String age;
    private String gender;
    private String phoneNumber;
    private String email;
    private String province;
    private String district;
    private String subdistrict;
    private String addressDetail;
    private String profession;
    private String sisiId;
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