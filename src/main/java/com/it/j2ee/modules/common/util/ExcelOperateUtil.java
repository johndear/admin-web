package com.it.j2ee.modules.common.util;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFDataFormat;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

/**
 * 
 * @author leihong
 *
 */
public class ExcelOperateUtil {

    /**
     * 读取Excel的内容，第一维数组存储的是一行中行的值，二维数组存储的是列值
     * @param file 读取数据的源Excel
     * @param ignoreRows 读取数据忽略的行数，比喻行头不需要读入 忽略的行数为1
     * @return 读出的Excel中数据的内容
     * @throws FileNotFoundException
     * @throws IOException
     */
    public static String[][] getData(File file, int ignoreRows) throws FileNotFoundException, IOException {

       List<String[]> result = new ArrayList<String[]>();
       int rowSize = 0;
       BufferedInputStream in = new BufferedInputStream(new FileInputStream( file));

       // 打开HSSFWorkbook
       POIFSFileSystem fs = new POIFSFileSystem(in);
       HSSFWorkbook wb = new HSSFWorkbook(fs);
       HSSFCell cell = null;
       for (int sheetIndex = 0; sheetIndex < wb.getNumberOfSheets(); sheetIndex++) {

           HSSFSheet st = wb.getSheetAt(sheetIndex);
          
           for (int rowIndex = ignoreRows; rowIndex <= st.getLastRowNum(); rowIndex++) {
              HSSFRow row = st.getRow(rowIndex);
              if (row == null) {
                  continue;
              }

              int tempRowSize = row.getLastCellNum() + 1;
              if (tempRowSize > rowSize) {
                  rowSize = tempRowSize;
              }

              String[] values = new String[rowSize];
              Arrays.fill(values, "");
              boolean hasValue = false;
              for (short columnIndex = 0; columnIndex <= row.getLastCellNum(); columnIndex++) {
                 
                  String value = "";

                  cell = row.getCell(columnIndex);

                  if (cell != null) {

                     // 注意：一定要设成这个，否则可能会出现乱码
                 //    cell.setEncoding(HSSFCell.ENCODING_UTF_16);

                     switch (cell.getCellType()) {

                     case HSSFCell.CELL_TYPE_STRING:

                         value = cell.getStringCellValue();

                         break;  

                     case HSSFCell.CELL_TYPE_NUMERIC:

                         if (HSSFDateUtil.isCellDateFormatted(cell)) {

                            Date date = cell.getDateCellValue();

                            if (date != null) {
                            	 value = new SimpleDateFormat("yyyy-MM-dd")

                                       .format(date);

                            } else {

                                value = "";

                            }

                         } else {
                        	cell.setCellType(Cell.CELL_TYPE_STRING);
                        	 String temp = cell.getStringCellValue(); 
                        	 //value =cell.getNumericCellValue()+"";
                        	 //判断是否包含小数点，如果不含小数点，则以字符串读取，如果含小数点，则转换为Double类型的字符串  
                             if(temp.indexOf(".")>-1){  
                            	 DecimalFormat df = new DecimalFormat("0.0000");
                                value = String.valueOf(df.format(new Double(temp))).trim();
                            	
                             }else{  
                            	 value = temp.trim();  
                             }  
                        	/* value = String.valueOf(cell.getNumericCellValue());  */
                            /*value = new DecimalFormat("0.00").format(cell

                                   .getNumericCellValue());*/

                         }

                         break;

                     case HSSFCell.CELL_TYPE_FORMULA:

                         // 导入时如果为公式生成的数据则无值

                         if (!cell.getStringCellValue().equals("")) {

                            value = cell.getStringCellValue();

                         } else {

                            value = cell.getNumericCellValue() + "";

                         }

                         break;

                     case HSSFCell.CELL_TYPE_BLANK:

                         break;

                     case HSSFCell.CELL_TYPE_ERROR:

                         value = "";

                         break;

                     case HSSFCell.CELL_TYPE_BOOLEAN:

                         value = (cell.getBooleanCellValue() == true ? "Y"

                                : "N");

                         break;

                     default:

                         value = "";

                     }

                  }

                  if (columnIndex == 0 && value.trim().equals("")) {                	 
                     continue;
                  }
                  values[columnIndex] = rightTrim(value);
                  System.out.println(values[columnIndex]);
                  hasValue = true;
              }

              if (hasValue) {
                 result.add(values);
              }
           }

       }

       in.close();
       String[][] returnArray = new String[result.size()][rowSize];
       for (int i = 0; i < returnArray.length; i++) {

           returnArray[i] = (String[]) result.get(i);
       }
       return returnArray;
    }

    
    /**
     * 读取Excel的内容，第一维数组存储的是一行中行的值，二维数组存储的是列值
     * @param file 读取数据的源Excel
     * @param ignoreRows 读取数据忽略的行数，比喻行头不需要读入 忽略的行数为1
     * @return 读出的Excel中数据的内容
     * @throws FileNotFoundException
     * @throws IOException
     */
    public static String[][] getSheetData(File file, int ignoreRows,int sheetIndex) throws FileNotFoundException, IOException {

       List<String[]> result = new ArrayList<String[]>();
       int rowSize = 0;
       BufferedInputStream in = new BufferedInputStream(new FileInputStream( file));

       // 打开HSSFWorkbook
       POIFSFileSystem fs = new POIFSFileSystem(in);
       HSSFWorkbook wb = new HSSFWorkbook(fs);
       HSSFCell cell = null;
      

           HSSFSheet st = wb.getSheetAt(sheetIndex);
          
           for (int rowIndex = ignoreRows; rowIndex <= st.getLastRowNum(); rowIndex++) {
              HSSFRow row = st.getRow(rowIndex);
              if (row == null) {
                  continue;
              }

              int tempRowSize = row.getLastCellNum() + 1;
              if (tempRowSize > rowSize) {
                  rowSize = tempRowSize;
              }

              String[] values = new String[rowSize];
              Arrays.fill(values, "");
              boolean hasValue = false;
              for (short columnIndex = 0; columnIndex <= row.getLastCellNum(); columnIndex++) {
                 
                  String value = "";

                  cell = row.getCell(columnIndex);

                  if (cell != null) {

                     // 注意：一定要设成这个，否则可能会出现乱码
                 //    cell.setEncoding(HSSFCell.ENCODING_UTF_16);

                     switch (cell.getCellType()) {

                     case HSSFCell.CELL_TYPE_STRING:

                         value = cell.getStringCellValue();

                         break;

                     case HSSFCell.CELL_TYPE_NUMERIC:

                         if (HSSFDateUtil.isCellDateFormatted(cell)) {

                            Date date = cell.getDateCellValue();

                            if (date != null) {

                                value = new SimpleDateFormat("yyyy-MM-dd")

                                       .format(date);

                            } else {

                                value = "";

                            }

                         } else {

                            value = new DecimalFormat("0").format(cell

                                   .getNumericCellValue());

                         }

                         break;

                     case HSSFCell.CELL_TYPE_FORMULA:

                         // 导入时如果为公式生成的数据则无值

                         if (!cell.getStringCellValue().equals("")) {

                            value = cell.getStringCellValue();

                         } else {

                            value = cell.getNumericCellValue() + "";

                         }

                         break;

                     case HSSFCell.CELL_TYPE_BLANK:

                         break;

                     case HSSFCell.CELL_TYPE_ERROR:

                         value = "";

                         break;

                     case HSSFCell.CELL_TYPE_BOOLEAN:

                         value = (cell.getBooleanCellValue() == true ? "Y"

                                : "N");

                         break;

                     default:

                         value = "";

                     }

                  }

                  if (columnIndex == 0 && value.trim().equals("")) {                	 
                     continue;
                  }
                  values[columnIndex] = rightTrim(value);
                  System.out.println(values[columnIndex]);
                  hasValue = true;
              }

              if (hasValue) {
                 result.add(values);
              }
           }

       

       in.close();
       String[][] returnArray = new String[result.size()][rowSize];
       for (int i = 0; i < returnArray.length; i++) {

           returnArray[i] = (String[]) result.get(i);
       }
       return returnArray;
    }
   

    /**
     * 去掉字符串右边的空格
     * @param str 要处理的字符串
     * @return 处理后的字符串
     */
     public static String rightTrim(String str) {
       if (str == null) {
           return "";
       }

       int length = str.length();
       for (int i = length - 1; i >= 0; i--) {
           if (str.charAt(i) != 0x20) {
              break;
           }
           length--;
       }
       return str.substring(0, length);
    }
  
    /** 
     * 设置字体 
     *  
     * @param wb 
     * @return 
     */  
    public static Font createFonts(Workbook wb, short bold, String fontName,  
            boolean isItalic, short hight) {  
        Font font = wb.createFont();  
        font.setFontName(fontName);  
        font.setBoldweight(bold);  
        font.setItalic(isItalic);  
        font.setFontHeight(hight);  
        return font;  
    }  
    
    /** 
     * 创建单元格并设置样式,值 
     *  
     * @param wb 
     * @param row 
     * @param column 
     * @param 
     * @param 
     * @param value 
     */  
    public static void createCell(Workbook wb, Row row, int column,  
            String value, Font font) {  
        Cell cell = row.createCell(column);  
        if("null".equals(value)){// 替换成空字符串
        	cell.setCellValue("");
        }else{
        	cell.setCellValue(value);  
        }
        CellStyle cellStyle = wb.createCellStyle();  
//        cellStyle.setAlignment(XSSFCellStyle.ALIGN_CENTER);  
//        cellStyle.setVerticalAlignment(XSSFCellStyle.VERTICAL_BOTTOM);  
        cellStyle.setFont(font);  
        cell.setCellStyle(cellStyle);  
    } 
    
     public static void main(String[] args) throws Exception {

        File file = new File("C:\\GZ-ORGInfoExport.xls");

        String[][] result = getData(file, 1);

        int rowLength = result.length;

        for(int i=0;i<10;i++) {

            for(int j=0;j<result[i].length;j++) {

               System.out.print(result[i][j]+"\t\t");

            }

            System.out.println();

        }

     }
}
