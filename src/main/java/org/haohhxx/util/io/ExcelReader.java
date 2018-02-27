package org.haohhxx.util.io;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * @author haozhenyuan
 *
 */
public class ExcelReader implements Iterable<List<String>>, Iterator<List<String>> {

    private int rowNub;
    private XSSFSheet sheet;
    private int rowId = 0;

    private ExcelReader(XSSFWorkbook wb, int sheetNub){
        sheet = wb.getSheetAt(sheetNub);
        rowNub = sheet.getLastRowNum();
    }

    public static ExcelReader getExcelXLSXReader(FileInputStream fileInput, int sheet){
        XSSFWorkbook wb = null;
        try {
            wb = new XSSFWorkbook(fileInput);
        } catch (IOException e) {
            e.printStackTrace();
        }
        assert wb != null;
        return new ExcelReader(wb, sheet);
    }

    public static ExcelReader getExcelXLSXReader(XSSFWorkbook wb, int sheet){
        return new ExcelReader(wb, sheet);
    }

    public static ExcelReader getExcelXLSXReader(String path, int sheet){
        FileInputStream fileInput;
        XSSFWorkbook wb = null;
        try {
            fileInput = new FileInputStream(path);
            wb = new XSSFWorkbook(fileInput);
        } catch (IOException e){
            e.printStackTrace();
        }
        assert wb != null;
        return new ExcelReader(wb, sheet);
    }

    @Override
    public Iterator<List<String>> iterator() {
        return this;
    }

    @Override
    public boolean hasNext(){
        return rowNub>=rowId;
    }

    @Override
    public List<String> next(){
        List<String> cells = new ArrayList<>();
        XSSFRow row = sheet.getRow(rowId);
        for(Cell cell:row){
//            System.out.println(cell.toString());
//            System.out.println(cell.getCellTypeEnum());
            String cellValue = cell.toString();
            cells.add(cellValue);
        }
        rowId ++;
        return cells;
    }

}



