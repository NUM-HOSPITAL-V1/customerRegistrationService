package mn.edu.userapi.dto;

import io.swagger.v3.oas.annotations.media.Schema;



public class CustomerDtoo {
    @Schema(description = "Харилцагчийн ID", example = "AB123CD456")
    private String customerId;

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

    @Schema(description = "Регистрийн дугаар", example = "УД92010199")
    private String nationalId;

    public String getProfession() {
        return profession;
    }

    public void setProfession(String profession) {
        this.profession = profession;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
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

    public String getSisiId() {
        return sisiId;
    }

    public void setSisiId(String sisiId) {
        this.sisiId = sisiId;
    }

    public String getNationalId() {
        return nationalId;
    }

    public void setNationalId(String nationalId) {
        this.nationalId = nationalId;
    }
}
