package restdemo.restservice.JPA;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import restdemo.restservice.entity.Employee;

import java.util.List;

public interface EmployeeJPARepository extends JpaRepository<Employee, Integer> {

    @Query(value = "SELECT * FROM employee", nativeQuery = true)
    Page<Employee> searchWithPage(Pageable pageable);

    @Query(value = "SELECT e FROM Employee e " +
            "where e.email like %?1% or e.firstName like %?1% or e.lastName like %?1%  ")
    List<Employee> search(String searchValue);

}
