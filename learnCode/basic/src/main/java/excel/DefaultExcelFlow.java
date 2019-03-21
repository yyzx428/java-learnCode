package excel;

import excel.annotation.ExcelHead;
import excel.cellhandler.CellStrategyFactory;
import excel.regiester.ExcelHeadDefinition;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;

import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.Field;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class DefaultExcelFlow implements ExcelFlow {

    private static final Logger logger = LoggerFactory.getLogger(DefaultExcelFlow.class);

    private static Map<Class<?>, List<ExcelHeadDefinition>> heads = new ConcurrentHashMap<>();

    private static CellStrategyFactory strategyFactory = new CellStrategyFactory();


    private Workbook workbook;
    private List<Sheet> sheets;
    private Class<?> target;

    public DefaultExcelFlow(Class<?> target) {
        this.workbook = new SXSSFWorkbook(100);
        this.sheets = new ArrayList<>();
        this.target = target;
    }

    @Override
    public List<ExcelHeadDefinition> readClass() {
        List<ExcelHeadDefinition> definitions = heads.get(target);

        if (CollectionUtils.isEmpty(definitions)) {
            Field[] fields = target.getDeclaredFields();
            definitions = new LinkedList<>();
            heads.put(target, definitions);
            for (Field field : fields) {
                ExcelHead excelHead = field.getAnnotation(ExcelHead.class);
                if (ObjectUtils.isEmpty(excelHead)) {
                    continue;
                }
                definitions.add(ExcelHeadDefinition
                        .newBuilder()
                        .field(field)
                        .message(excelHead.message())
                        .builder());
            }
        }
        return definitions;
    }

    @Override
    public void generateHead() {
        Row headRow = getCurrentSheet().createRow(0);
        List<ExcelHeadDefinition> definitions = heads.get(target);
        ListIterator<ExcelHeadDefinition> iterable = definitions.listIterator();
        while (iterable.hasNext()) {
            Integer index = iterable.nextIndex();
            ExcelHeadDefinition definition = iterable.next();
            Cell cell = headRow.createCell(index);
            cell.setCellType(Cell.CELL_TYPE_STRING);
            cell.setCellValue(definition.getMessage());
            cell.setCellStyle(getBold());
            definition.getCellStrategy().createCellStyle(workbook);
            definition.getCellStrategy().initCellStyle();
        }
    }

    @Override
    public void write(OutputStream stream) {
        try {
            workbook.write(stream);
        } catch (IOException e) {
            logger.error("Excel写入异常:", e);
        }
    }

    public CellStyle getBold() {
        CellStyle cellStyle = workbook.createCellStyle();
        XSSFFont font = (XSSFFont) workbook.createFont();
        font.setBold(true);
        cellStyle.setFont(font);
        return cellStyle;
    }

    @Override
    public <T> void writeDate(List<T> list) {
        ListIterator<T> row = list.listIterator();
        while (row.hasNext()) {
            Integer rowIndex = row.nextIndex();
            T dto = row.next();
            Row headRow = getCurrentSheet().createRow(rowIndex + 1);
            List<ExcelHeadDefinition> definitions = heads.get(target);
            ListIterator<ExcelHeadDefinition> iterable = definitions.listIterator();
            while (iterable.hasNext()) {
                Integer index = iterable.nextIndex();
                ExcelHeadDefinition definition = iterable.next();
                Cell cell = headRow.createCell(index);
                strategyFactory.populate(cell, dto, definition);
            }
        }
    }

    @Override
    public Sheet getCurrentSheet() {
        if (CollectionUtils.isEmpty(sheets)) {
            sheets.add(workbook.createSheet());
        }
        return sheets.get(0);
    }
}
