package com.wxmimperio.excel;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Writer {

    public static void writeExcel(OutputStream os, String excelExtName, Map<String, List<Person>> data) throws IOException {
        Workbook wb = null;

        String[] titleArray = {"姓名", "年龄", "性别", "编号"};
        try {
            if ("xls".equals(excelExtName)) {
                wb = new HSSFWorkbook();
            } else if ("xlsx".equals(excelExtName)) {
                wb = new XSSFWorkbook();
            } else {
                throw new Exception("当前文件不是excel文件");
            }

            for (String sheetName : data.keySet()) {
                Sheet sheet = wb.createSheet(sheetName);
                Row titleRow = sheet.createRow(0);
                for (int j = 0; j < titleArray.length; j++) {
                    Cell cell = titleRow.createCell(j);
                    cell.setCellValue(titleArray[j]);
                }
                List<Person> rowList = data.get(sheetName);
                for (int i = 1; i < rowList.size() + 1; i++) {
                    Row row = sheet.createRow(i);
                    List<String> person = getPersonList(rowList.get(i - 1));
                    for (int j = 0; j < titleArray.length; j++) {
                        Cell cell = row.createCell(j);
                        cell.setCellValue(person.get(j));
                    }
                }
            }
            wb.write(os);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (wb != null) {
                wb.close();
            }
        }
    }

    private static List<String> getPersonList(Person person) {
        List<String> result = new ArrayList<String>();
        result.add(person.getName());
        result.add(String.valueOf(person.getAge()));
        result.add(person.getGender());
        result.add(String.valueOf(person.getNumber()));
        return result;
    }
}
