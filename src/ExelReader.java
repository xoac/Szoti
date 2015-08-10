import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Iterator;

import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;

/**
 * Created by sylwek on 09.08.15.
 */
public class ExelReader {
    private String filePath;
    private HSSFWorkbook workbook;
    private HSSFSheet sheet;
    private Iterator<Row> rowIterator;
    private Iterator<Cell> cellIterator;
    private Row row;
    private Cell cel;

    ExelReader (String _filePath) {
        try {
            filePath = _filePath;
            FileInputStream file = new FileInputStream(filePath);

            workbook = new HSSFWorkbook(file);
            sheet    = workbook.getSheetAt(0);
            rowIterator = sheet.iterator();
            if(rowIterator.hasNext())
                row = rowIterator.next();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public boolean hasNextRow()
    {
        return rowIterator.hasNext();
    }


    public static void main(String[] args) {
        ExelReader Poznan = new ExelReader("a.txt");


        }

    }

