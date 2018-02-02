package com.wxmimperio.excel;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ExcelMain {

    public static void main(String[] args) throws Exception {
        String filePath = "D:\\test.xlsx";
        List<List<Map<String, String>>> sheets = Reader.readExcelWithTitle(filePath);

        for (List<Map<String, String>> sheet : sheets) {
            for (Map<String, String> line : sheet) {
                System.out.println(line);
            }
        }

        Map<String, List<Person>> data = new HashMap<String, List<Person>>();
        List<Person> personList = new ArrayList<Person>();
        for (int i = 0; i < 20; i++) {
            personList.add(new Person("wxm" + i, i, "gender" + i, 100 * i));
        }
        data.put("sheet1", personList);

        Writer.writeExcel(new FileOutputStream(new File("D:/my.xlsx")), "xlsx", data);
    }
}
