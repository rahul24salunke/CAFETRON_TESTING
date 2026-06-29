package com.cafetron.utilities;

import com.cafetron.data.TestUser;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public final class ExcelUtils {
    private static final Path REGISTERED_USERS_PATH = Path.of("test-output", "RegisteredUsers.xlsx");
    private static final String REGISTERED_USERS_SHEET = "RegisteredUsers";
    private static final DateTimeFormatter REGISTERED_AT_FORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    private static final String[] REGISTERED_USERS_HEADERS = {
            "RegisteredAt",
            "TestName",
            "Name",
            "Email",
            "Password",
            "EmployeeId",
            "Department",
            "Role"
    };

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

    public static synchronized void writeRegisteredUser(String testName, TestUser user) {
        try {
            Files.createDirectories(REGISTERED_USERS_PATH.getParent());

            try (Workbook workbook = openOrCreateWorkbook(REGISTERED_USERS_PATH)) {
                Sheet sheet = getOrCreateSheet(workbook, REGISTERED_USERS_SHEET);
                ensureHeaderRow(sheet);
                Row row = sheet.createRow(sheet.getLastRowNum() + 1);

                writeCell(row, 0, LocalDateTime.now().format(REGISTERED_AT_FORMAT));
                writeCell(row, 1, testName);
                writeCell(row, 2, user.name());
                writeCell(row, 3, user.email());
                writeCell(row, 4, user.password());
                writeCell(row, 5, user.employeeId());
                writeCell(row, 6, user.department());
                writeCell(row, 7, user.role().name());

                autosizeColumns(sheet);

                try (OutputStream outputStream = Files.newOutputStream(REGISTERED_USERS_PATH)) {
                    workbook.write(outputStream);
                }
            }
        } catch (IOException exception) {
            throw new IllegalStateException("Unable to write registered user Excel file: "
                    + REGISTERED_USERS_PATH, exception);
        }
    }

    private static Workbook openOrCreateWorkbook(Path path) throws IOException {
        if (Files.exists(path) && Files.size(path) > 0) {
            try (InputStream inputStream = Files.newInputStream(path)) {
                return WorkbookFactory.create(inputStream);
            }
        }
        return new XSSFWorkbook();
    }

    private static Sheet getOrCreateSheet(Workbook workbook, String sheetName) {
        Sheet sheet = workbook.getSheet(sheetName);
        if (sheet == null) {
            sheet = workbook.createSheet(sheetName);
        }
        return sheet;
    }

    private static void ensureHeaderRow(Sheet sheet) {
        Row headerRow = sheet.getRow(0);
        if (headerRow == null) {
            headerRow = sheet.createRow(0);
        }

        for (int columnIndex = 0; columnIndex < REGISTERED_USERS_HEADERS.length; columnIndex++) {
            writeCell(headerRow, columnIndex, REGISTERED_USERS_HEADERS[columnIndex]);
        }
    }

    private static void writeCell(Row row, int columnIndex, String value) {
        row.createCell(columnIndex).setCellValue(value);
    }

    private static void autosizeColumns(Sheet sheet) {
        for (int columnIndex = 0; columnIndex < REGISTERED_USERS_HEADERS.length; columnIndex++) {
            sheet.autoSizeColumn(columnIndex);
        }
    }
}
