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
}