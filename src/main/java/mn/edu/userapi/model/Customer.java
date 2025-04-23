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
    private LocalDate dateOfBirth;  // Төрсөн он, сар, өдөр

    @Column(nullable = false)
    private Integer age;  // Нас

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Gender gender;  // Хүйс

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

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Profession profession;  // Мэргэжил

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

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
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

    public Profession getProfession() {
        return profession;
    }

    public void setProfession(Profession profession) {
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