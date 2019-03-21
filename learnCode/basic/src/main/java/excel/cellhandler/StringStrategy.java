package excel.cellhandler;

import excel.formatter.BigDecimalFormatFactory;
import excel.regiester.ExcelHeadDefinition;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.springframework.core.convert.TypeDescriptor;
import org.springframework.format.support.DefaultFormattingConversionService;

import java.lang.reflect.Field;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class StringStrategy extends AbstractStrategy<Object> {
    private static DefaultFormattingConversionService formatterService;
    private static Map<Field, TypeDescriptor> fields = new ConcurrentHashMap<>();

    static {
        formatterService = new DefaultFormattingConversionService();
        formatterService.addFormatterForFieldAnnotation(new BigDecimalFormatFactory());
    }

    @Override
    public void initCellStyle() {

    }

    @Override
    public CellStyle getCellStyle() {
        return style;
    }

    @Override
    public void setCellValue(Object value, Cell cell, ExcelHeadDefinition definition) {
        cell.setCellValue((String) formatterService.convert(value,
                fields.computeIfAbsent(definition.getField(), TypeDescriptor::new),
                TypeDescriptor.valueOf(String.class)));
    }
}
