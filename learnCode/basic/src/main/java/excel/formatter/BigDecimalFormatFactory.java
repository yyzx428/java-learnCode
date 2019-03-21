package excel.formatter;

import excel.annotation.BigDecimalFormat;
import org.springframework.format.AnnotationFormatterFactory;
import org.springframework.format.Parser;
import org.springframework.format.Printer;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class BigDecimalFormatFactory implements AnnotationFormatterFactory<BigDecimalFormat> {

    private static final Set<Class<?>> FIELD_TYPES;

    static {
        Set<Class<?>> fieldTypes = new HashSet<Class<?>>(2);
        fieldTypes.add(BigDecimal.class);
        FIELD_TYPES = Collections.unmodifiableSet(fieldTypes);
    }

    @Override
    public Set<Class<?>> getFieldTypes() {
        return FIELD_TYPES;
    }

    @Override
    public Printer<?> getPrinter(BigDecimalFormat annotation, Class<?> fieldType) {
        return (Printer<Object>) (object, locale) -> ((BigDecimal) object).setScale(annotation.scale(), annotation.mode()).toString();
    }

    @Override
    public Parser<?> getParser(BigDecimalFormat annotation, Class<?> fieldType) {
        return (Parser<Object>) (text, locale) -> new BigDecimal(text);
    }
}
