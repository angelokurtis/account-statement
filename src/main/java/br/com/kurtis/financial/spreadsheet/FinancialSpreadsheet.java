package br.com.kurtis.financial.spreadsheet;

import br.com.kurtis.financial.domain.Bank;
import br.com.kurtis.financial.infra.ConfigProperties;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class FinancialSpreadsheet {

    private static final String SHEETNAME = "Transactions";
    private final XSSFWorkbook workbook;

    public static FinancialSpreadsheet newInstance() {
        final XSSFWorkbook workbook = new XSSFWorkbook();
        final XSSFSheet sheet = workbook.createSheet(SHEETNAME);
        final XSSFRow row = sheet.createRow(1);
        int colNum = 0;
        final List<String> headers = Arrays.asList("Due Date", "Bank", "Description", "Code", "Value");
        for (String header : headers) {
            final Cell cell = row.createCell(colNum);
            cell.setCellValue(header);
            colNum++;
        }
        return new FinancialSpreadsheet(workbook);
    }

    @Builder(builderMethodName = "lineBuilder", builderClassName = "LineBuilder")
    public void addLine(LocalDate dueDate, Bank bank, String description, String code, BigDecimal value) {
        int colNum = 0;
        final int rowNum = getNextRow().getRowNum();
        addCell(dueDate, rowNum, colNum++);
        addCell(bank, rowNum, colNum++);
        addCell(description, rowNum, colNum++);
        addCell(code, rowNum, colNum++);
        addCell(value, rowNum, colNum);
    }

    public void writeOn(String path) throws IOException {
        final OutputStream outputStream = new FileOutputStream(path);
        workbook.write(outputStream);
        workbook.close();
    }

    private void addCell(BigDecimal value, int rowNum, int colNum) {
        final XSSFRow row = getSheet().getRow(rowNum);
        final Cell cell = row.createCell(colNum);
        final double doubleValue = value.doubleValue();
        cell.setCellValue(doubleValue);
    }

    private void addCell(String value, int rowNum, int colNum) {
        final XSSFRow row = getSheet().getRow(rowNum);
        final Cell cell = row.createCell(colNum);
        cell.setCellValue(value);
    }

    private void addCell(Bank value, int rowNum, int colNum) {
        final XSSFRow row = getSheet().getRow(rowNum);
        final Cell cell = row.createCell(colNum);
        cell.setCellValue(value.name());
    }

    private void addCell(LocalDate value, int rowNum, int colNum) {
        final String timeZoneId = ConfigProperties.getProperty("app.time_zone");
        final TimeZone zone = TimeZone.getTimeZone(timeZoneId);
        final Date dateValue = Date.from(value.atStartOfDay(zone.toZoneId()).toInstant());
        final XSSFRow row = getSheet().getRow(rowNum);
        final Cell cell = row.createCell(colNum);
        cell.setCellValue(dateValue);
    }

    private XSSFSheet getSheet() {
        return workbook.getSheet(SHEETNAME);
    }

    private XSSFRow getNextRow() {
        final XSSFSheet sheet = getSheet();
        final int lastRowNum = sheet.getLastRowNum() + 1;
        return sheet.createRow(lastRowNum);
    }
}