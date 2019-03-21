package excel.cellhandler;

import excel.regiester.ExcelHeadDefinition;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Workbook;

public interface CellStrategy<V> {

    void createCellStyle(Workbook workbook);

    void initCellStyle();

    CellStyle getCellStyle();

    void setCellValue(V value, Cell cell, ExcelHeadDefinition definition);
}
