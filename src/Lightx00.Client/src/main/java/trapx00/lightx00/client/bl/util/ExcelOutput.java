package trapx00.lightx00.client.bl.util;

import jxl.Workbook;
import jxl.write.*;
import trapx00.lightx00.shared.po.ResultMessage;

import java.io.File;


public class ExcelOutput {

    private static WritableWorkbook book;
    private static WritableSheet sheet;
    private static WritableFont normalFont;
    private static WritableCellFormat normalFormat;


    //path为路径(无需带上文件名)
    //outputMessage为你需要输出Excel表格的字符串组
    //name为该表名字，例如为库存盘点就为库存盘点，什么单就是什么单
    public static ResultMessage createExcel(String path, String[]outputMessage,String name) {
        return createExcel(path, outputMessage, name, "-");

    }

    public static ResultMessage createExcel(String path, String[]outputMessage,String name, String seperator) {
        try {
            path=path+"\\"+name+".xls";
            book = Workbook.createWorkbook(new File(path));
            //页码
            sheet = book.createSheet( name, 0);
            normalFont = new WritableFont(WritableFont.createFont("宋体"), 11, WritableFont.NO_BOLD);
            // 设置字体为宋体,11号字,不加粗,颜色为红色
            normalFormat = new WritableCellFormat(normalFont);
            normalFormat.setAlignment(jxl.format.Alignment.CENTRE);
            normalFormat.setVerticalAlignment(jxl.format.VerticalAlignment.CENTRE);

            for(int i=0;i<outputMessage[0].split(seperator).length;i++){
                for(int j=0;j<outputMessage.length;j++){
                    sheet.addCell(new Label(i,j,outputMessage[j].split(seperator)[i],normalFormat));
                }
            }

            book.write();
            book.close();
            System.out.println("创建文件成功!");
            return ResultMessage.Success;
        } catch(Exception e){
            return ResultMessage.Failure;
        }

    }


}
