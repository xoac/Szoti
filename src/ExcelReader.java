import java.io.*;
import java.util.Iterator;

import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;

/**
 * Created by sylwek on 09.08.15.
 */
public class ExcelReader {
    private Iterator<Row> rowIterator;
    private Iterator<Cell> cellIterator;
    HSSFSheet sheet ;
    HSSFWorkbook workbook;
    private FileInputStream file;


    ExcelReader(String _pathToFileXls) {

        try {
            file = new FileInputStream(new File(_pathToFileXls));

            //Get the workbook instance for XLS file
            workbook = new HSSFWorkbook(file);

            //Get first sheet from the workbook
            sheet = workbook.getSheetAt(0);

            //Iterate through each rows from first sheet
            rowIterator = sheet.iterator();


        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void test(){

    }

    public Row nextRow() {
        return rowIterator.next();
    }

    public Cell nextCell() {
        return cellIterator.next();
    }

    public boolean hasNextRow() {
        return rowIterator.hasNext();
    }

    public boolean hasNextCell() {
        return cellIterator.hasNext();
    }

    public void closeFile() {
        try {
            file.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }



    public void print()
    {
        while(rowIterator.hasNext()) {
            Row row = rowIterator.next();
            //For each row, iterate through each columns
            cellIterator = row.cellIterator();
            while(cellIterator.hasNext()) {

                Cell cell = cellIterator.next();

                switch(cell.getCellType()) {
                    case Cell.CELL_TYPE_BOOLEAN:
                        System.out.print(cell.getBooleanCellValue() + "\t\t");
                        break;
                    case Cell.CELL_TYPE_NUMERIC:
                        System.out.print(cell.getNumericCellValue() + "\t\t");
                        break;
                    case Cell.CELL_TYPE_STRING:
                        System.out.print(cell.getStringCellValue() + "\t\t");
                        break;
                }
            }
            System.out.println("");
        }
    }



    public static void main(String[] args) {
        ExcelReader Poznan = new ExcelReader("/home/sylwek/git/Szoti/export.xls");
        Poznan.print();
        }

    }

