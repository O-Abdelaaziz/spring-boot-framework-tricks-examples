package com.custom.validator.validation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

/**
 * @Created 01/10/2022 - 17:20
 * @Package com.custom.validator.validation
 * @Project custom-validator-exemple-01
 * @User LegendDZ
 * @Author Abdelaaziz Ouakala
 **/
@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Constraint(validatedBy = EmployeeTypeValidator.class)
public @interface ValidationEmployeeType {
    String message() default "Invalid employee type: It should be either Permanent or Vendor";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}
