package excel.cellhandler;

import excel.regiester.ExcelHeadDefinition;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;

public class DoubleStrategy extends AbstractStrategy<Double> {

    @Override
    public void initCellStyle() {
        this.style.setDataFormat(this.dataFormat.getFormat("#,##0.00"));
    }

    @Override
    public CellStyle getCellStyle() {
        return style;
    }

    @Override
    public void setCellValue(Double value, Cell cell, ExcelHeadDefinition definition) {
        cell.setCellValue(value);
    }
}
