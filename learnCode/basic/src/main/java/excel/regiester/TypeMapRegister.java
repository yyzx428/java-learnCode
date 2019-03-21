package excel.regiester;

import org.apache.poi.ss.usermodel.Cell;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class TypeMapRegister {
    public static Map<Class<?>, Integer> typeMap;

    static {
        Map<Class<?>, Integer> temp = new HashMap<>();
        temp.put(BigDecimal.class, Cell.CELL_TYPE_NUMERIC);
        temp.put(Integer.class, Cell.CELL_TYPE_NUMERIC);
        temp.put(Double.class, Cell.CELL_TYPE_NUMERIC);
        temp.put(Long.class, Cell.CELL_TYPE_NUMERIC);
        temp.put(Short.class, Cell.CELL_TYPE_NUMERIC);
        temp.put(int.class, Cell.CELL_TYPE_NUMERIC);
        temp.put(long.class, Cell.CELL_TYPE_NUMERIC);
        temp.put(double.class, Cell.CELL_TYPE_NUMERIC);
        typeMap = new ConcurrentHashMap<>(temp);
    }
}
