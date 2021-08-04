package restdemo.restservice.restController;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;
import restdemo.restservice.JPA.EmployeeJPARepository;
import restdemo.restservice.entity.Employee;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
public class EmployeeRestAPI {

    @Autowired
    private EmployeeJPARepository employeeJPARepository;

    @PostConstruct
    private void initData() {

//        for (int i = 0; i < 10; i++) {
//            Faker faker = new Faker();
//
//            employeeJPARepository.save(new Employee(faker.harryPotter().character(), faker.pokemon().name(), faker.pokemon().name() + "@gmail.com"));
//        }

    }

    @GetMapping("/employees")
    public List<Employee> showAllEmployee() {

        List<Employee> list = employeeJPARepository.findAll();

        return list;
    }

    @GetMapping("/employees/{id}")
    public Employee getEmployee(@PathVariable int id) {
        Optional<Employee> employee = employeeJPARepository.findById(id);
        return employee.get();
    }

    @GetMapping("/employees/search")
    public List<Employee> searchEmployee(@RequestParam(value = "searchValue") String searchValue) {

        List<Employee> list = employeeJPARepository.search(searchValue);

        return list;
    }

    @GetMapping("/employees/all")
    public Page<Employee> getEmployeeWithPage(
            @RequestParam Integer page,
            @RequestParam(defaultValue = "2") Integer size,
            @RequestParam(defaultValue = "ASC") String sortType,
            @RequestParam(defaultValue = "id") String sortBy) {

        if (sortBy.isEmpty()) sortBy = "first_name";

        Sort sortable = null;
        if (sortType.equals("ASC")) {
            sortable = Sort.by(sortBy).ascending();
        } else if (sortType.equals("DESC")) {
            sortable = Sort.by(sortBy).descending();
        }

        Pageable pageable = PageRequest.of(page, size, sortable);

        Page<Employee> pageEmployee = employeeJPARepository.searchWithPage(pageable);

        return pageEmployee;
    }

    @PutMapping("/employees")
    public Employee updateEmployee(@RequestBody Employee e) {

        employeeJPARepository.save(e);

        return e;
    }

    @PostMapping(value = "/employees")
    public Map<String, Object> addEmployee(@RequestBody Employee employee) {

        Map<String, Object> response = new HashMap<>();
        employeeJPARepository.save(new Employee(employee.getFirstName(), employee.getFirstName(), employee.getEmail()));
        response.put("status", "add successfully");
        response.put("list", employeeJPARepository.findAll());

        return response;
    }

    @DeleteMapping("/employees/{id}")
    public List<Employee> deleteProduct(@PathVariable int id) {

        employeeJPARepository.deleteById(id);

        return employeeJPARepository.findAll();
    }

}
