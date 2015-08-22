import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.omg.CORBA.DynAnyPackage.InvalidValue;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Iterator;
import java.util.StringTokenizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by sylwek on 15.08.15.
 */
public class Importer {
    HSSFSheet sheet;
    HSSFWorkbook workbook;
    private int IDnr, NazwaNr, WojewodztwoNr, KodPocztowyNr, MiastoNr, UlicaNr, SektorNr, PodsektorNr, EtapNr, WartoscNr, Firama1Nr, Branza1Nr;
    private Iterator<Row> rowIterator;
    private Iterator<Cell> cellIterator;
    private FileInputStream file;
    private Company[] Companies;
    private int amountOfCompanies=0;

    public void print(){
        for(int i=0; i<amountOfCompanies;i++)
            Companies[i].printWithInvestments();
    }

    Importer(String _pathToFileXls) {
        try {
            file = new FileInputStream(new File(_pathToFileXls));

            //Get the workbook instance for XLS file
            workbook = new HSSFWorkbook(file);

            //Get first sheet from the workbook
            sheet = workbook.getSheetAt(0);

            //Iterate through each rows from first sheet
            rowIterator = sheet.iterator();

            Companies = new Company[sheet.getLastRowNum()]; // Wielkość jest zbyt duża bo nie uwzględnia powtórzeń firmy

            if (rowIterator.hasNext()) {
                Row row = rowIterator.next();
                cellIterator = row.cellIterator();
                for (int i = 0; cellIterator.hasNext(); i++) {
                    Cell cell = cellIterator.next();

                    String value = null;
                    switch (cell.getCellType()) {
                        case Cell.CELL_TYPE_BOOLEAN:
                            value = String.valueOf(cell.getBooleanCellValue());
                            break;
                        case Cell.CELL_TYPE_NUMERIC:
                            value = String.valueOf(cell.getNumericCellValue());
                            break;
                        case Cell.CELL_TYPE_STRING:
                            value = cell.getStringCellValue();
                            break;
                    }

                    if (value.equals("ID"))
                        IDnr = i;
                    else if (value.equals("Nazwa"))
                        NazwaNr = i;
                    else if (value.equals("Województwo"))
                        WojewodztwoNr = i;
                    else if (value.equals("Kod pocztowy"))
                        KodPocztowyNr = i;
                    else if (value.equals("Miasto"))
                        MiastoNr = i;
                    else if (value.equals("Ulica"))
                        UlicaNr = i;
                    else if (value.equals("Sektor"))
                        SektorNr = i;
                    else if (value.equals("Podsektor"))
                        PodsektorNr = i;
                    else if (value.equals("Etap"))
                        EtapNr = i;
                    else if (value.equals("Wartość inwestycji"))
                        WartoscNr = i;
                    else if (value.equals("Firma 1"))
                        Firama1Nr = i;
                    else if (value.equals("Branża 1"))
                        Branza1Nr = i;

                }

                fullFillCompanies();


            }


        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InvalidValue invalidValue) {
            invalidValue.printStackTrace();
        }
    }

    public static void main(String[] args) {

        Importer test = new Importer("/home/sylwek/git/Szoti/export.xls");
        test.print();
    }

    private void fullFillCompanies() throws InvalidValue {

        Company firama;
        while (rowIterator.hasNext()) {
            Investment inwestycja = new Investment();
            Row row = rowIterator.next();

            Cell cell = row.getCell(IDnr);
           inwestycja.setInvestmentId((int) cell.getNumericCellValue());

            inwestycja.setInvestmentName(getStringfromCell(row, NazwaNr));
            inwestycja.setVoivodeship(getStringfromCell(row, WojewodztwoNr));
            inwestycja.setPostCode(getStringfromCell(row, KodPocztowyNr));
            inwestycja.setCity(getStringfromCell(row, MiastoNr));
            inwestycja.setStreet(getStringfromCell(row, UlicaNr));
            inwestycja.setSector(getStringfromCell(row, SektorNr));
            inwestycja.setUnderSector(getStringfromCell(row, PodsektorNr));
            inwestycja.setValueOfInvestment(getStringfromCell(row, WartoscNr));

            firama = fulFillCompany(getStringfromCell(row,Firama1Nr));
            firama.addInvestment(inwestycja);
            boolean CompanyInTheBase = false;
            for(int j=0;j<amountOfCompanies;j++)
                if(Companies[j].getCompanyName().trim().equals(firama.getCompanyName().trim())) {
                    Companies[j].addInvestment(inwestycja);
                    CompanyInTheBase = true;
                }
            if(!CompanyInTheBase)
                Companies[amountOfCompanies++] = firama;

        }
    }

    private String getStringfromCell(Row _wiersz, int _id){
        Cell cell = _wiersz.getCell(_id);
        if(cell == null || cell.getCellType() == Cell.CELL_TYPE_BLANK)
            return "00-000";
        else if (cell.getCellType() == Cell.CELL_TYPE_STRING) {
            if(cell.getStringCellValue().trim().equals(""))
                return "00-000";
           return cell.getStringCellValue().trim();
        }
        else
            return "00-000";


    }


    private Company fulFillCompany(String _rowWithInformation) throws InvalidValue {
        Company tmp = new Company();
        String PostCodeRegex = "[0-9]{2}-[0-9]{3}";
        int i=0;

        StringTokenizer str = new StringTokenizer(_rowWithInformation,",");

        while (str.hasMoreElements()) {
            if(i==0)
                tmp.setCompanyName(str.nextElement().toString());
            else if(i==1){
                String s = str.nextElement().toString();
                Matcher m = Pattern.compile(PostCodeRegex).matcher(s);
                    if(m.find()){
                        tmp.setPostCode(m.group().toString().trim());
                    }
                s=s.replaceAll(PostCodeRegex,"");
                tmp.setCity(s.trim());
            }
            else if(i==2) {
                String s = str.nextElement().toString().trim();
                s=s.replaceAll("ul.","").trim();
                tmp.setStreet(s.trim());
            }
            else if(i == 3){ //adding phone numbers
                String s = str.nextElement().toString().trim();
                while(!s.matches("tel:.*")) {
                    s = str.nextElement().toString().trim();
                }
                if(s.matches("tel:.*")) {
                    s=s.replaceAll("tel:","").trim();
                    tmp.addPhoneNumber(s);
                }
                s=str.nextElement().toString().trim();
                while(!s.matches("fax:.*")){ //adding phone numbers until find "fax:" into string
                    tmp.addPhoneNumber(s);
                    s=str.nextElement().toString().trim();
                }
            }
            else if(i == 4){ //adding mails
                String s = str.nextElement().toString().trim();
                while (s.matches("^\\d?-?(\\d{3})?-?\\d{3}-?\\d{4}$")) {
                    System.out.println(str.nextElement().toString().trim());
                }
                String EmailRegex =  "[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
                        + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})";
                Matcher m = Pattern.compile(EmailRegex,Pattern.CASE_INSENSITIVE).matcher(s);
                while(m.find())
                    tmp.addEmail(m.group().toString().trim());
               if(!str.hasMoreElements()) {
                   m = Pattern.compile("^((?!@).)*$").matcher(s);
                   if(m.find() && !m.group().equals(""))
                       tmp.addUrl(m.group().toString().trim()); //add urls if there was no emails
               }
                System.out.println();
            }
            else if(i==5){ //adding urls
                while (str.hasMoreElements()) {
                    String s = str.nextElement().toString().trim();
                    Matcher m = Pattern.compile("(@)?(href=')?(HREF=')?(HREF=\")?(href=\")?(http://)?[a-zA-Z_0-9\\-]+(\\.\\w[a-zA-Z_0-9\\-]+)+(/[#&\\n\\-=?\\+\\%/\\.\\w]+)?").matcher(s);
                    while (m.find())
                        tmp.addUrl(m.group().toString().trim());
                }
            }
            else {
               str.nextElement();
                System.out.println("niepoprawne działanie programu, niespodziewane elementy");
            }

        i++;
        }

        return tmp;
    }


}
