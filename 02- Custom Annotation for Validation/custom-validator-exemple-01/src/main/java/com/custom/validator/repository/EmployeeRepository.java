package com.custom.validator.repository;

import com.custom.validator.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @Created 01/10/2022 - 15:57
 * @Package com.custom.validator.repository
 * @Project custom-validator-exemple-01
 * @User LegendDZ
 * @Author Abdelaaziz Ouakala
 **/
@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {
}
