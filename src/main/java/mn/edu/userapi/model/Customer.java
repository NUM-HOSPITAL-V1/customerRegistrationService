package mn.edu.userapi.model;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import mn.edu.userapi.model.enums.Gender;
import mn.edu.userapi.model.enums.Profession;

import java.time.LocalDate;

@Entity
@Table(name = "customers")
@NamedStoredProcedureQuery(
        name = "create_user_id",
        procedureName = "create_user_id",
        parameters = {
                @StoredProcedureParameter(mode = ParameterMode.OUT, name = "result", type = String.class)
        }
)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Customer {
    @Id
    @Column(nullable = false)
    private String customerId;

    @Column(nullable = false)
    private String nationalId;  // Регистрийн дугаар

    @Column(nullable = false)
    private String surname;  // Овог

    @Column(nullable = false)
    private String firstName;  // Нэр

    @Column(nullable = false)
    private String dateOfBirth;  // Төрсөн он, сар, өдөр

    @Column(nullable = false)
    private String age;  // Нас

    @Column(nullable = false)
    private String gender;  // Хүйс

    @Column(nullable = false)
    private String phoneNumber;  // Утасны дугаар

    private String email;  // Email

    @Column(nullable = false)
    private String province;  // Аймаг/Хот

    @Column(nullable = false)
    private String district;  // Сум/Дүүрэг

    @Column(nullable = false)
    private String subdistrict;  // Хороо/Баг

    @Column(nullable = false)
    private String addressDetail;  // Гудамж/Байр/Тоот

    @Column(nullable = false)
    private String profession;  // Мэргэжил

    @Column(nullable = false)
    private String sisiId;  // User ID from the original code

    @Column(nullable = false)
    private String userId;  // User ID

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String getNationalId() {
        return nationalId;
    }

    public void setNationalId(String nationalId) {
        this.nationalId = nationalId;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getSubdistrict() {
        return subdistrict;
    }

    public void setSubdistrict(String subdistrict) {
        this.subdistrict = subdistrict;
    }

    public String getAddressDetail() {
        return addressDetail;
    }

    public void setAddressDetail(String addressDetail) {
        this.addressDetail = addressDetail;
    }

    public String getProfession() {
        return profession;
    }

    public void setProfession(String profession) {
        this.profession = profession;
    }

    public String getSisiId() {
        return sisiId;
    }

    public void setSisiId(String sisiId) {
        this.sisiId = sisiId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}