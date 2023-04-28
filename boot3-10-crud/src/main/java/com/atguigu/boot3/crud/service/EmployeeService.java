package com.atguigu.boot3.crud.service;

import com.atguigu.boot3.crud.entity.Employee;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * @author lfy
 * @Description
 * @create 2023-04-28 16:41
 */
@Service
public class EmployeeService {

    Map<Long,Employee> data = new ConcurrentHashMap<>();

    public Employee getEmployeeById(Long id) {
        return data.get(id);
    }

    public List<Employee> getEmployees() {
        return data.values().stream().toList();
    }

    public void saveEmployee(Employee employee) {
        data.put(employee.getId(),employee);
    }

    public void deleteEmployee(Long id) {
        data.remove(id);
    }
}
