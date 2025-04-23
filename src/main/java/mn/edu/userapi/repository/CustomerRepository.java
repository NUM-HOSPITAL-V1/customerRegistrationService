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

    @Procedure(name = "create_customer_id")
    String createCustomerId();
    // [A-Z][000-999][A-Z][000-999][A-Z][000-999]

    @Query("SELECT c.customerId, c.surname, c.firstName FROM Customer c")
    List<Object[]> fetchAllCustomers();

    @Query("SELECT c FROM Customer c WHERE c.customerId = :id")
    Optional<Customer> fetchCustomer(@Param("id") String id);
}