package excel.cellhandler;

import excel.regiester.ExcelHeadDefinition;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;

import java.math.BigDecimal;

public class BigDecimalStrategy extends AbstractStrategy<BigDecimal> {

    @Override
    public void initCellStyle() {
        this.style.setDataFormat(this.dataFormat.getFormat("#,##0.00"));
    }

    @Override
    public CellStyle getCellStyle() {
        return style;
    }

    @Override
    public void setCellValue(BigDecimal value, Cell cell, ExcelHeadDefinition definition) {
        cell.setCellValue(value.doubleValue());
    }

}
