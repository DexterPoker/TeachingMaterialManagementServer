/**
 * 
 */
package common;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Random;

import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.alibaba.fastjson.JSONObject;

/**
 * @author zql
 *
 * @version 创建时间：2015年3月17日 
 */
public class Excels {

    private Logger logger;
    private File xlsFile;
    private Workbook workbook; //
    private Sheet sheet;

    /**
     * 构造函数
     * 
     * @param filePath
     *            需要读取的Excel文件的文件路径
     */
    public Excels(String filePath) {
//        PropertyConfigurator.configure("Log4j.properties");
        logger = Logger.getLogger(Excels.class.getName());

        xlsFile = new File(filePath);
        if (!xlsFile.exists())
            logger.error(filePath + " can't find!");
        else {
            try {
                if (is2007(filePath))
                    workbook = new XSSFWorkbook(new FileInputStream(filePath));
                else
                    workbook = new HSSFWorkbook(new FileInputStream(filePath));
            } catch (FileNotFoundException e) {

                e.printStackTrace();
            } catch (IOException e) {

                e.printStackTrace();
            }
        }
    }

    public Excels(String filePath, String sheetName) {
        try {
            File file = new File(filePath);
            if (file.exists()) {
                if (is2007(filePath))
                    workbook = new XSSFWorkbook(new FileInputStream(filePath));
                else
                    workbook = new HSSFWorkbook(new FileInputStream(filePath));
            } else {
                if (is2007(filePath))
                    workbook = new XSSFWorkbook();
                else
                    workbook = new HSSFWorkbook();
            }
            // HSSFSheet
           if(workbook.getSheet(sheetName)==null){
               sheet = workbook.createSheet(sheetName);// 建立新的sheet对象
           }else{
               sheet= workbook.getSheet(sheetName); 
           }
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
    public void CreateSheet(String sheetName){
        if(workbook.getSheet(sheetName)==null){
            sheet = workbook.createSheet(sheetName);// 建立新的sheet对象
        }else{
            sheet= workbook.getSheet(sheetName); 
        }
    }
    private boolean is2007(String filePath) {
        return filePath.matches("^.+\\.(?i)(xlsx)$");
    }

    public int getRowNum(String sheetname) {
        sheet = workbook.getSheet(sheetname);
        return sheet.getLastRowNum();
    }
    public Workbook getWorkbook(String sheetname) {
        return workbook;
    }
    public JSONObject getParams(int nRow, String sheetname) {
        Double d;
        int j;
        String param;
        sheet = workbook.getSheet(sheetname);
        JSONObject res = new JSONObject();
        if (sheet == null)
            return null;
        // System.out.println(sheetname);
        Row row0 = sheet.getRow(0);
        Row row1 = sheet.getRow(nRow);
        int n = row0.getLastCellNum();
        for (int i = row0.getFirstCellNum(); i < n; i++) {
            Cell cell0 = row0.getCell(i);
            Cell cell1 = row1.getCell(i);
            if (cell1 != null && cell1.getCellType() != Cell.CELL_TYPE_BLANK) {
                param = cell0.getStringCellValue();
                // temp = temp + param + "\":\"";
                switch (cell1.getCellType()) {
                case Cell.CELL_TYPE_STRING:
                    res.put(param, cell1.getStringCellValue());
                    // temp = temp + cell1.getStringCellValue() + "\",\"";
                    break;
                case Cell.CELL_TYPE_NUMERIC: {
                    d = cell1.getNumericCellValue();
                    j = (int) cell1.getNumericCellValue();
                    if (d == j)
                        res.put(param, j);// temp = temp + j + "\",\"";
                    else
                        res.put(param, d);// temp = temp + d + "\",\"";
                }
                    break;
                case Cell.CELL_TYPE_BOOLEAN:
                    if (cell1.getBooleanCellValue())
                        res.put(param, "true");
                    else
                        res.put(param, "false");
                    break;
                default:
                }
            }
        }
        return res;
    }
    /**
     * get id and string map
     * @param sheetname
     * @param idx1 ids' index
     * @param idx2 strings' index
     * @return
     */
        public HashMap<String,String> getColumnMap(String sheetname, int idx1,int idx2, int beginRowIndex) {
            LinkedHashMap<String,String> result = new LinkedHashMap<String,String>();
            sheet = workbook.getSheet(sheetname);
            if (sheet == null)
                return null;
            for (int i = beginRowIndex; i <= sheet.getLastRowNum(); i++) {
                Row row = sheet.getRow(i);
                if (row == null) {
                    if (result != null)
                        result.clear();
                    return null;
                }
                
                Cell cell0 = row.getCell(idx1);
                Cell cell1= row.getCell(idx2);
                String strCell0=cell0.getStringCellValue();
                String strCell1="";
                if(cell1!=null){
                    if(cell1.getCellType()==0){
                        cell1.setCellType(Cell.CELL_TYPE_STRING);
                    }
                    strCell1=cell1.getStringCellValue();
                }else{
                    strCell1="";
                }
                result.put(strCell0,strCell1);
            }
            return result;
        }
        
    public void addParam(int nRow, String sheetname, String filename,
            String key, String value) {
        sheet = workbook.getSheet(sheetname);
        Row row0 = sheet.getRow(0);
        Row row1 = sheet.getRow(nRow);
        int n = row0.getLastCellNum();
        for (int i = row0.getFirstCellNum(); i < n; i++) {
            Cell cell0 = row0.getCell(i);
            if (cell0.getStringCellValue().equals(key)) {
                row1.createCell(i).setCellValue(value);
            }
        }
        OutputStream outputStream = null;
        try {
            outputStream = new FileOutputStream(filename);
            workbook.write(outputStream);
            outputStream.flush();
            outputStream.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (outputStream != null) {
                try {
                    outputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void writeExcel(String sheetName, String buf, int rowNum, int colNum) {
        sheet = workbook.getSheet(sheetName);
        HSSFRow row = (HSSFRow) sheet.createRow(rowNum);
        String temp[] = buf.split(";");
        HSSFCell cell = row.createCell((int) colNum);
        // cell.setCellType(Cell.CELL_TYPE_NUMERIC);
        HSSFCell cell2 = row.createCell((int) (colNum + 1));
        cell.setCellValue(temp[0]);

        if (temp.length > 1)
            cell2.setCellValue(temp[1]);
        else
            cell2.setCellValue("did not detect");

    }

    public void write2Excel(String sheetName, String buf, int rowNum, int colNum) {
        sheet = workbook.getSheet(sheetName);
        HSSFRow row = (HSSFRow) sheet.createRow(rowNum);
        String temp[] = buf.split(";");
        for (int i = 0; i < colNum; i++) {
            HSSFCell cell = row.createCell(i);
            if (i <= temp.length)
                cell.setCellValue(temp[i]);
            else
                cell.setCellValue("");
        }
    }

    public void writeToFile(String filePath) {
        FileOutputStream fileOut;

        try {
            fileOut = new FileOutputStream(filePath);
            workbook.write(fileOut);
            fileOut.close();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    int createRandom(int min, int max) {
        Random random = new Random();
        return random.nextInt(max - min + 1) + min;
    }
    
//    public static List<Object[]> getRows(String method) {
//		Excels xls = new Excels(Constant.xlsPath);
//		List<Object[]> ls = new ArrayList<Object[]>();
//		for (int i = 1; i <= xls.getRowNum(method); i++) {
//			Object[] o = new Object[1];
//			o[0] = i;
//			// result.add(i) ;
//			ls.add(o);
//		}
//		return ls;
//	}
}