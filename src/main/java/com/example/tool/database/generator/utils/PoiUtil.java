package com.example.tool.database.generator.utils;


import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class PoiUtil {
    /**
     * Excel文件路径
     */
    private String excelPath = "";

    /**
     * 设定开始读取的位置，默认为0
     */
    private int startReadPos = 0;

    /**
     * 设定结束读取的位置，默认为0，用负数来表示倒数第n行
     */
    private int endReadPos = 0;

    /**
     * 设定是否打印消息
     */
    private boolean printMsg = true;


    private Workbook wb = null;



    public PoiUtil(String excelPath) {
        this.excelPath = excelPath;
    }

    /**
     * 还原设定（其实是重新new一个新的对象并返回）
     *
     * @return
     */
    public PoiUtil RestoreSettings() {
        PoiUtil instance = new PoiUtil(this.excelPath);
        return instance;
    }


    public Workbook getWorkbook() throws IOException {
        if (this.wb != null) {
            return this.wb;
        } else {
            String xlsPath = this.excelPath;
            //获取扩展名
            String ext = xlsPath.substring(xlsPath.lastIndexOf(".") + 1);
            File file = new File(xlsPath);
            if (!file.exists()) {
                throw new IOException("文件名为" + file.getName() + "Excel文件不存在！");
            }
            FileInputStream fis = new FileInputStream(file);
            if ("xls".equals(ext)) {              //使用xls方式读取
                wb = new XSSFWorkbook(fis);
            } else if ("xlsx".equals(ext)) {       //使用xlsx方式读取
                wb = new XSSFWorkbook(fis);
            } else {
                return null;
            }
            return wb;
        }

    }

    public List<String> getSheets() throws IOException {
        List<String> list = new ArrayList<String>();
        Workbook wb = this.getWorkbook();
        int i = wb.getNumberOfSheets();
        for (int j = 0; j < i; j++) {
            list.add(wb.getSheetName(j));
        }
        return list;

    }

    /**
     * 自动根据文件扩展名，调用对应的读取方法
     *
     * @throws IOException
     * @Title: writeExcel
     * @Date : 2014-9-11 下午01:50:38
     */
    public List<Row> readExcel() throws IOException {
        Workbook wb = this.getWorkbook();
        List<Row> rowList = new ArrayList<Row>();
        Sheet sheet = wb.getSheetAt(0);
        // 获取设定操作的sheet(如果设定了名称，按名称查，否则按索引值查)
//        sheet =selectedSheetName.equals("")? wb.getSheetAt(selectedSheetIdx):wb.getSheet(selectedSheetName);  


        //获取最后行号  
        int lastRowNum = sheet.getLastRowNum();

        if (lastRowNum > 0) {    //如果>0，表示有数据
            out("\n开始读取名为【" + sheet.getSheetName() + "】的内容：");
        }

        Row row = null;
        int start = startReadPos;
        int end;
        if (endReadPos == 0) {
            end = lastRowNum;
        } else {
            end = endReadPos;
        }
        for (int i = start; i <= end; i++) {
            row = sheet.getRow(i);
            if (row != null) {
                rowList.add(row);
            }
        }

        return rowList;
    }

    public List<Row> readExcel(int sheetIdx) throws IOException {
        Workbook wb = this.getWorkbook();
        List<Row> rowList = new ArrayList<Row>();
        Sheet sheet = wb.getSheetAt(sheetIdx);
        //获取最后行号  
        int lastRowNum = sheet.getLastRowNum();

        if (lastRowNum > 0) {    //如果>0，表示有数据
            out("\n开始读取名为【" + sheet.getSheetName() + "】的内容：");
        }

        Row row = null;
        int start = startReadPos;
        int end;
        if (endReadPos == 0) {
            end = lastRowNum;
        } else {
            end = endReadPos;
        }
        for (int i = start; i <= end; i++) {
            row = sheet.getRow(i);
            if (row != null) {
                rowList.add(row);
            }
        }

        return rowList;
    }

    public List<Row> readExcel(String sheetName) throws IOException {
        Workbook wb = this.getWorkbook();
        List<Row> rowList = new ArrayList<Row>();
        Sheet sheet = wb.getSheet(sheetName);
        //获取最后行号  
        int lastRowNum = sheet.getLastRowNum();

        if (lastRowNum > 0) {    //如果>0，表示有数据
            out("\n开始读取名为【" + sheet.getSheetName() + "】的内容：");
        }

        Row row = null;
        int start = startReadPos;
        int end;
        if (endReadPos == 0) {
            end = lastRowNum;
        } else {
            end = endReadPos;
        }
        for (int i = start; i <= end; i++) {
            row = sheet.getRow(i);
            if (row != null) {
                rowList.add(row);
            }
        }

        return rowList;
    }

    /*** 
     * 读取单元格的值 
     *
     * @Title: getCellValue
     * @Date : 2014-9-11 上午10:52:07 
     * @param cell
     * @return
     */
    public static Object getCellValue(Cell cell) {
        Object result = "";
        if (cell != null) {
            switch (cell.getCellType()) {
                case Cell.CELL_TYPE_STRING:
                    result = cell.getStringCellValue();
                    break;
                case Cell.CELL_TYPE_NUMERIC:
                    result = cell.getNumericCellValue();
                    break;
                case Cell.CELL_TYPE_BOOLEAN:
                    result = cell.getBooleanCellValue();
                    break;
                case Cell.CELL_TYPE_FORMULA:
                    result = cell.getCellFormula();
                    break;
                case Cell.CELL_TYPE_ERROR:
                    result = cell.getErrorCellValue();
                    break;
                case Cell.CELL_TYPE_BLANK:
                    break;
                default:
                    break;
            }
        }
        return result.toString();
    }

    /**
     * 打印消息，
     *
     * @param msg 消息内容
     */
    private void out(String msg) {
        if (printMsg) {
            out(msg, true);
        }
    }

    /**
     * 打印消息，
     *
     * @param msg 消息内容
     * @param tr  换行
     */
    private void out(String msg, boolean tr) {
        if (printMsg) {
            System.out.print(msg + (tr ? "\n" : ""));
        }
    }

    public void writeExcel(List<List<String>> rows, String fileName) {
        if (rows == null) {
            return;
        }
        XSSFWorkbook xwb = new XSSFWorkbook();
        XSSFSheet sheet = xwb.createSheet("column_info");
        for (int i = 0; i < rows.size(); i++) {
            XSSFRow row = sheet.createRow(i);
            for (int j = 0; j < rows.get(0).size(); j++) {
                XSSFCell cell = row.createCell(j);
                cell.setCellValue(rows.get(i).get(j).toString());
            }
        }
        exportExcel(fileName, xwb);
    }


    public void exportExcel(String fileName, XSSFWorkbook xwb) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        OutputStream os = null;
        File file = new File(excelPath + File.separator + fileName + ".xlsx");
        try {
            xwb.write(baos);
        } catch (IOException e) {
            e.printStackTrace();
        }
        byte[] content = baos.toByteArray();
        try {
            os = new FileOutputStream(file);
            xwb.write(os);

            os.close();
            baos.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String getExcelPath() {
        return this.excelPath;
    }

    public void setExcelPath(String excelPath) {
        this.excelPath = excelPath;
    }

    public int getStartReadPos() {
        return startReadPos;
    }

    public void setStartReadPos(int startReadPos) {
        this.startReadPos = startReadPos;
    }

    public int getEndReadPos() {
        return endReadPos;
    }

    public void setEndReadPos(int endReadPos) {
        this.endReadPos = endReadPos;
    }

    public boolean isPrintMsg() {
        return printMsg;
    }

    public void setPrintMsg(boolean printMsg) {
        this.printMsg = printMsg;
    }


}
