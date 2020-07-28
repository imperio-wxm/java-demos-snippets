package com.wxmimperio.excel;

import cn.afterturn.easypoi.excel.ExcelExportUtil;
import cn.afterturn.easypoi.excel.entity.ExportParams;
import com.google.common.collect.Lists;
import org.apache.poi.ss.usermodel.Workbook;

import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ExcelMain {

    public static void main(String[] args) throws Exception {
        ExportParams params = new ExportParams();
        params.setSheetName("借阅单");//设置sheet名
        Workbook workbook = ExcelExportUtil.exportExcel(params, MyDate.class, getMyData());
        //返回头设置下载，并设置文件名，返回
        setExportExcelFormat(workbook, "借阅单导出");
    }

    public static List<MyDate> getMyData() {
        List<MyDate> myDates = new ArrayList<>();
        MyDate myDate = new MyDate();
        myDate.setLogId("123");
        myDate.setDbName("wxm_db");
        myDate.setTableName("wxm_table01");

        List<Model> models = Lists.newArrayList();
        for(int i = 0; i <10;i++) {
            Model model = new Model();
            model.setC1("col_" + i);
            model.setC2("col_" + i);
            model.setCost(1000 * i);
            model.setPre(0.1F * i);
            models.add(model);
        }

        myDate.setData(models);

        myDates.add(myDate);
        return myDates;
    }

    public static void setExportExcelFormat(Workbook workbook, String fileName) throws Exception {
        fileName = fileName + new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()) + ".xls";
        try (FileOutputStream outputStream = new FileOutputStream(fileName, true)) {
            workbook.write(outputStream);
        }
    }
}
