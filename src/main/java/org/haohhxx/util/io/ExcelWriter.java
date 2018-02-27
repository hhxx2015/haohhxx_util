package org.haohhxx.util.io;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

/**
 * @author haozhenyuan
 */
public class ExcelWriter {

    private XSSFSheet sheet;
    private int rowId = 0;
    private int columnId = 0;
    private FileOutputStream fileOutputStream;
    private Workbook wb;

    private ExcelWriter(String path, XSSFWorkbook wb, int sheetNub) {
        sheet = wb.getSheetAt(sheetNub);
        this.wb = wb;
        try {
            fileOutputStream = new FileOutputStream(path);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static ExcelWriter getExcelXLSXWriter(String path, int sheet){
        FileInputStream fileInput = null;
        XSSFWorkbook wb = null;
        try {
            fileInput = new FileInputStream(path);
            wb = new XSSFWorkbook(fileInput);
        }catch (FileNotFoundException e){
            newExcel(path, sheet+1);
            return getExcelXLSXWriter(path, sheet);
        } catch (IOException e) {
            e.printStackTrace();
        }
        assert wb != null;
        return new ExcelWriter(path, wb, sheet);
    }

    public static void newExcel(String path,int sheetNub){
        try {
            Workbook wb=new XSSFWorkbook();
            for (int i = 0; i <sheetNub ; i++) {
                wb.createSheet(String.format("sheet%s",i+1));
            }
            FileOutputStream fileOutputStream = new FileOutputStream(path);
            wb.write(fileOutputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.err.println("create file :" + path);
    }

    public void writeLine(List<String> line){
        XSSFRow row = sheet.createRow(rowId);
        Cell cell;
        for (int i = 0; i <line.size() ; i++) {
            cell = row.createCell(i);
            cell.setCellType(CellType.STRING);
            cell.setCellValue(line.get(i));
        }
        columnId = line.size();
        rowId++;
    }

    public void writeLine(String[] line){
        XSSFRow row = sheet.getRow(rowId);
        Cell cell;
        for (int i = 0; i <line.length ; i++) {
            cell = row.createCell(i);
            cell.setCellType(CellType.STRING);
            cell.setCellValue(line[i]);
        }
        columnId = line.length;
        rowId++;
    }

    public void write(String value){
        XSSFRow row = sheet.getRow(rowId);
        Cell cell  = row.createCell(columnId);
        cell.setCellType(CellType.STRING);
        cell.setCellValue(value);
        columnId++;
    }

    public void close(){
        try {
            wb.write(fileOutputStream);
            fileOutputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
