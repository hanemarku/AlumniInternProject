package com.example.AlumniInternProject.Events.export;

import com.example.AlumniInternProject.user.DTOs.UserGetDto;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.xssf.usermodel.*;

import java.io.IOException;
import java.util.List;

public class EventExcelExporter extends AbstractExporter {
    private XSSFWorkbook workbook;
    private XSSFSheet sheet;

    public EventExcelExporter(){
        workbook = new XSSFWorkbook();
    }

    private void writeHeaderLine(){
        sheet = workbook.createSheet("Users");
        XSSFRow row = sheet.createRow(0);

        XSSFCellStyle cellStyle = workbook.createCellStyle();
        XSSFFont font = workbook.createFont();
        font.setBold(true);
        font.setFontHeight(16);
        cellStyle.setFont(font);

        createCell(row, 0, "First Name", cellStyle);
        createCell(row, 1, "Last Name", cellStyle);
    }

    private void createCell(XSSFRow row, int columnIndex, Object value, CellStyle style){
        XSSFCell cell = row.createCell(columnIndex);
        sheet.autoSizeColumn(columnIndex);

        if (value instanceof Integer){
            cell.setCellValue((Integer) value);
        } else if (value instanceof Boolean){
            cell.setCellValue((Boolean) value);
        } else {
            cell.setCellValue((String) value);
        }
        cell.setCellStyle(style);
    }

    public void export(List<UserGetDto> listUsers, HttpServletResponse response) throws IOException {
        super.setResponseHeader(response, "application/octet-stream", ".xlsx");

        writeHeaderLine();
        writeDataLines(listUsers);

        ServletOutputStream outputStream = response.getOutputStream();
        workbook.write(outputStream);
        workbook.close();
        outputStream.close();
    }

    private void writeDataLines(List<UserGetDto> listUsers) {
        int rowIndex = 1;

        XSSFCellStyle cellStyle = workbook.createCellStyle();
        XSSFFont font = workbook.createFont();
        font.setFontHeight(14);
        cellStyle.setFont(font);

        for (UserGetDto user : listUsers) {
            XSSFRow row = sheet.createRow(rowIndex++);
            int columnIndex = 0;

            createCell(row, columnIndex++, user.getFirstname(), cellStyle);
            createCell(row, columnIndex++, user.getLastname(), cellStyle);
        }
    }
}
