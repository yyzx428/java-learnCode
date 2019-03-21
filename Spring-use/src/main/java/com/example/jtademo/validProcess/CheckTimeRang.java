package com.example.jtademo.validProcess;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

import static java.lang.annotation.ElementType.*;

@Target({TYPE, FIELD, METHOD, PARAMETER, ANNOTATION_TYPE, TYPE_USE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Repeatable(CheckTimeRang.List.class)
@Constraint(validatedBy = {CheckTimeRangValidator.class})
public @interface CheckTimeRang {

    String startTime() default "from";

    String endTime() default "end";

    String message() default "{org.hibernate.validator.referenceguide.chapter06.checkCase.message}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    @Retention(RetentionPolicy.RUNTIME)
    @Documented
    @Target({TYPE, FIELD, METHOD, PARAMETER, ANNOTATION_TYPE})
    @interface List {
        CheckTimeRang[] value();
    }
}
