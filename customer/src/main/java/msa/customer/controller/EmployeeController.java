package msa.customer.controller;

import msa.customer.DAO.Employee;
import msa.customer.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class EmployeeController {

    @Autowired
    private EmployeeRepository employeeRepository;

    @PostMapping("/employee")
    public Employee saveEmployee(Employee employee){
        return employeeRepository.save(employee);
    }

    @GetMapping("/employee/{id}")
    public Employee getEmployee(@PathVariable("id") String employeeId){
        return employeeRepository.getEmployById(employeeId);
    }

    @DeleteMapping("/employee/{id}")
    public String deleteEmployee(@PathVariable("id") String employeeId){
        return employeeRepository.delete(employeeId);
    }

}
