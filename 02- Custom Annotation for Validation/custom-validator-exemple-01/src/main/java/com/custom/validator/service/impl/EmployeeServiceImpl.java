package com.custom.validator.service.impl;

import com.custom.validator.entity.Employee;
import com.custom.validator.repository.EmployeeRepository;
import com.custom.validator.service.IEmployeeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @Created 01/10/2022 - 16:00
 * @Package com.custom.validator.service.impl
 * @Project custom-validator-exemple-01
 * @User LegendDZ
 * @Author Abdelaaziz Ouakala
 **/
@Transactional
@Service
@Slf4j
public class EmployeeServiceImpl implements IEmployeeService {

    private EmployeeRepository employeeRepository;

    @Autowired
    public EmployeeServiceImpl(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    @Override
    public Employee save(Employee employee) {
        return employeeRepository.save(employee);
    }
}
