package suwu.daopuerp.util;

import jxl.Workbook;
import jxl.write.*;
import suwu.daopuerp.exception.ExcelCreateFailException;

import java.io.File;
import java.io.IOException;


public class ExcelOutput {
    private static WritableWorkbook book;
    private static WritableSheet sheet;
    private static WritableFont normalFont;
    private static WritableCellFormat normalFormat;


    //path为路径(无需带上文件名)
    //outputMessage为你需要输出Excel表格的字符串组
    //name为该表名字，例如为库存盘点就为库存盘点，什么单就是什么单
    public static void createExcel(String path, String[] outputMessage, String name) throws ExcelCreateFailException {
        createExcel(path, outputMessage, name, "-");
    }

    public static void createExcel(String path, String[] outputMessage, String name, String separater) throws ExcelCreateFailException {
        try {
            path = path + ".xls";
            book = Workbook.createWorkbook(new File(path));
            //页码
            sheet = book.createSheet(name, 0);
            normalFont = new WritableFont(WritableFont.createFont("宋体"), 11, WritableFont.NO_BOLD);
            // 设置字体为宋体,11号字,不加粗,颜色为红色
            normalFormat = new WritableCellFormat(normalFont);
            normalFormat.setAlignment(jxl.format.Alignment.CENTRE);
            normalFormat.setVerticalAlignment(jxl.format.VerticalAlignment.CENTRE);
            normalFormat.setBorder(jxl.format.Border.ALL, jxl.format.BorderLineStyle.THIN);

            for (int i = 0; i < outputMessage.length; i++) {
                String[] cells = outputMessage[i].split(separater);
                for (int j = 0; j < cells.length; j++) {
                    sheet.addCell(new Label(j, i, cells[j], normalFormat));
                }
            }

            book.write();
            book.close();
            System.out.println("创建文件成功!");
        } catch (Exception e) {
            e.printStackTrace();
            throw new ExcelCreateFailException();
        }

    }

    public static WritableSheet createBlankSheet(String path, String name) throws ExcelCreateFailException {
        try {
            path = path + ".xls";
            book = Workbook.createWorkbook(new File(path));
            //页码
            sheet = book.createSheet(name, 0);
            return sheet;
        } catch (IOException e) {
            e.printStackTrace();
            throw new ExcelCreateFailException();
        }
    }
}
