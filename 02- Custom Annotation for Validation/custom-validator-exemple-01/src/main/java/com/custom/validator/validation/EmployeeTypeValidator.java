package com.custom.validator.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Arrays;
import java.util.List;

/**
 * @Created 01/10/2022 - 17:22
 * @Package com.custom.validator.validation
 * @Project custom-validator-exemple-01
 * @User LegendDZ
 * @Author Abdelaaziz Ouakala
 **/
public class EmployeeTypeValidator implements ConstraintValidator<ValidationEmployeeType, String> {

    @Override
    public boolean isValid(String employeeType, ConstraintValidatorContext constraintValidatorContext) {
        List<String> employeeTypes = Arrays.asList("Permanent", "Vendor");
        return employeeTypes.contains(employeeType);
    }
}
