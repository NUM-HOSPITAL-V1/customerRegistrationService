package mn.edu.userapi.repository;

import mn.edu.userapi.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, String> {

    @Query(value = "SELECT sp_create_newid()", nativeQuery = true)
    String createCustomerId();
    // [A-Z][000-999][A-Z][000-999][A-Z][000-999]

    @Query("SELECT c.customerId, c.surname, c.firstName, c.userId FROM Customer c")
    List<Object[]> fetchAllCustomers();

    @Query("SELECT c FROM Customer c WHERE c.customerId = :id OR c.userId = :id")
    Optional<Customer> fetchCustomer(@Param("id") String id);

    @Query("SELECT COUNT(c) > 0 FROM Customer c WHERE c.nationalId = :id")
    boolean existsByNationalId(@Param("id") String id);

    @Query("SELECT COUNT(c) > 0 FROM Customer c WHERE c.sisiId = :sisiId")
    boolean existsBySisiId(@Param("sisiId") String sisiId);

    @Query("""
        SELECT c FROM Customer c
        WHERE LOWER(c.addressDetail) LIKE LOWER(CONCAT('%', :param, '%'))
           OR LOWER(c.age) LIKE LOWER(CONCAT('%', :param, '%'))
           OR LOWER(c.district) LIKE LOWER(CONCAT('%', :param, '%'))
           OR LOWER(c.email) LIKE LOWER(CONCAT('%', :param, '%'))
           OR LOWER(c.firstName) LIKE LOWER(CONCAT('%', :param, '%'))
           OR LOWER(c.phoneNumber) LIKE LOWER(CONCAT('%', :param, '%'))
           OR LOWER(c.surname) LIKE LOWER(CONCAT('%', :param, '%'))
           OR c.nationalId LIKE CONCAT('%', :nat_id, '%')
           OR LOWER(c.gender) LIKE LOWER(CONCAT('%', :param, '%'))
           OR LOWER(c.province) LIKE LOWER(CONCAT('%', :param, '%'))
           OR LOWER(c.subdistrict) LIKE LOWER(CONCAT('%', :param, '%'))
           OR LOWER(c.profession) LIKE LOWER(CONCAT('%', :param, '%'))
    """)
    List<Customer> searchCustomers(@Param("param") String param, @Param("nat_id") String nat_id);

    @Query("SELECT c.userId FROM Customer c WHERE c.userId = :id OR c.customerId = :id")
    String getUserId(@Param("id") String id);
}