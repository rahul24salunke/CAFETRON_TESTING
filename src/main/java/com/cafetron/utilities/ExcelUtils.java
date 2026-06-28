package com.cafetron.utilities;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;

public final class ExcelUtils {

    private ExcelUtils() {
    }

    public static Object[][] readSheet(String filePath, String sheetName) {
        Path path = Path.of(filePath);
        DataFormatter formatter = new DataFormatter();

        try (InputStream inputStream = Files.newInputStream(path);
             Workbook workbook = WorkbookFactory.create(inputStream)) {
            Sheet sheet = workbook.getSheet(sheetName);
            if (sheet == null) {
                throw new IllegalArgumentException("Sheet not found: " + sheetName);
            }

            int lastRow = sheet.getLastRowNum();
            Row headerRow = sheet.getRow(0);
            if (headerRow == null || lastRow < 1) {
                return new Object[0][0];
            }

            int columnCount = headerRow.getLastCellNum();
            Object[][] data = new Object[lastRow][columnCount];

            for (int rowIndex = 1; rowIndex <= lastRow; rowIndex++) {
                Row row = sheet.getRow(rowIndex);
                for (int columnIndex = 0; columnIndex < columnCount; columnIndex++) {
                    Cell cell = row == null ? null : row.getCell(columnIndex);
                    data[rowIndex - 1][columnIndex] = formatter.formatCellValue(cell);
                }
            }
            return data;
        } catch (IOException exception) {
            throw new IllegalStateException("Unable to read Excel file: " + filePath, exception);
        }
    }
}
