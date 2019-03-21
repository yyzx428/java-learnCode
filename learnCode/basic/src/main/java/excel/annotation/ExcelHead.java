package excel.annotation;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD})
@Documented
public @interface ExcelHead {
    /**
     * 表头名
     */
    String message() default "";
}
