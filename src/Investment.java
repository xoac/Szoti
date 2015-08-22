import org.omg.CORBA.DynAnyPackage.InvalidValue;

import java.util.Iterator;
import java.util.Scanner;

/**
 * Created by sylwek on 09.08.15.
 */
public class Investment  {
    public static int idcreator=0;
    private int id;
    private int InvestmentId;
    private String InvestmentName;
    private String PostCode, City, Street, Voivodeship;
    private String Sector, UnderSector;
    private String ValueOfInvestment;

    public void setInvestmentId(int _investmentid){
        InvestmentId = _investmentid;
    }

    public String getInvestmentName() {
        return InvestmentName;
    }

    public Investment() {
        id = ++idcreator;
        ValueOfInvestment = null;
    }

    public Investment Investment(Investment _i){
        return _i;
    }

    public void setInvestmentName(String _investmentname) {
        InvestmentName = _investmentname;
    }

    public String getStreet() {
        return Street;
    }

    public void setStreet(String _street) {
        Street = _street;
    }

    public String getValueOfInvestment() {
        return ValueOfInvestment;
    }

    public void setValueOfInvestment(String _valueofinvestment) {
        ValueOfInvestment = _valueofinvestment;
    }

    public String getVoivodeship() {
        return Voivodeship;
    }

    public void setVoivodeship(String _voivodeship) {
        Voivodeship = _voivodeship;
    }

    public String getSector() {
        return Sector;
    }

    public void setSector(String _sector) {
        Sector = _sector;
    }

    public String getUnderSector() {
        return UnderSector;
    }

    public void setUnderSector(String _undersector) {
        UnderSector = _undersector;
    }


    public String getCity() {
        return this.City;
    }

    public void setCity(String C) {
        City = C;
    }

    public String getPostCode() {
        return this.PostCode;
    }

    public void setPostCode(String _postcode) throws InvalidValue {
        if (Validator.isPostCodeValid(_postcode))
            PostCode = _postcode;
        else
            throw new InvalidValue("Nieprawidłowa wartność PostCode\t\\'" + _postcode + "\'");
    }

    public void print() {
        System.out.printf("%-12s %-30s %10s %-25s %n",  "\tInwestycja", "\033[1m" + InvestmentName+ "\033[0m", "Miasto: ", "\033[1m" + PostCode +" " + City +"\033[0m ul. \033[1m"+ Street + "\033[0m" );
        System.out.printf("%-12s %-30s %10s %-25s %-5s %-20s %n","\tSektor: ","\033[1m" + Sector + "\033[0m","E-mail: ", "\033[1m" + UnderSector
                + "\033[0m", "Wartość: ", "\033[1m" + ValueOfInvestment+ "\033[0m");
    }
}
