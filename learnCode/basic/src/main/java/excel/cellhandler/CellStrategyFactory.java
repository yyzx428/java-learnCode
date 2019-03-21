package excel.cellhandler;

import excel.regiester.ExcelHeadDefinition;
import org.apache.poi.ss.usermodel.Cell;
import org.springframework.util.ReflectionUtils;

import java.math.BigDecimal;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;


public class CellStrategyFactory {
    private static Map<Class<?>, CellStrategy> strategys = new ConcurrentHashMap<>();

    static {
        strategys.put(BigDecimal.class, new BigDecimalStrategy());
        strategys.put(Integer.class, new IntegerStrategy());
        strategys.put(Double.class, new DoubleStrategy());
        strategys.put(String.class, new StringStrategy());
    }

    public static CellStrategy getCellStrategy(Class<?> target) {
        return Optional.ofNullable(strategys.get(target)).orElse(strategys.get(String.class));
    }

    @SuppressWarnings("unchecked")
    public <T> void populate(Cell cell, T dto, ExcelHeadDefinition definition) {
        cell.setCellType(definition.getCellType());
        Object value = ReflectionUtils.getField(definition.getField(), dto);
        CellStrategy strategy = definition.getCellStrategy();
        cell.setCellStyle(strategy.getCellStyle());
        strategy.setCellValue(value, cell,definition);
    }
}
