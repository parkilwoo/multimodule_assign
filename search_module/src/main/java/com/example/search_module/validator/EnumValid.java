package com.example.search_module.validator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;

@Documented
@Target({ FIELD })
@Constraint(validatedBy = {EnumValidator.class})
public @interface EnumValid {
    String message() default "유효하지 않은 Enum 값입니다.";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
    Class<? extends Enum<?>> enumClass();
}
