package excel.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.math.RoundingMode;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface BigDecimalFormat {
    int scale() default 2;

    RoundingMode mode() default RoundingMode.CEILING;
}
