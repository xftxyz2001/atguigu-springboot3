package com.atguigu.boot3.crud.controller;


import com.atguigu.boot3.crud.entity.Employee;
import com.atguigu.boot3.crud.service.EmployeeService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author lfy
 * @Description
 * @create 2023-04-28 16:41
 */
@Tag(name = "员工",description = "员工CRUD")
@RestController
public class EmployeeController {

    @Autowired
    EmployeeService employeeService;

    @GetMapping("/emp/{id}")
    public Employee getEmployee(@PathVariable("id") Long id){
        return employeeService.getEmployeeById(id);
    }

    @GetMapping("/emps")
    public List<Employee> getEmployee(){
        return employeeService.getEmployees();
    }

    @PostMapping("/emp")
    public String saveEmployee(@RequestBody Employee employee){
        employeeService.saveEmployee(employee);
        return "ok";
    }

    @DeleteMapping("/emp/{id}")
    public String deleteEmployee(@PathVariable("id") Long id){
        employeeService.deleteEmployee(id);
        return "ok";
    }
}
