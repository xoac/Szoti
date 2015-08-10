import org.omg.CORBA.DynAnyPackage.InvalidValue;

/**
 * Created by sylwek on 09.08.15.
 */
public class Investment {
    private String InvestmentName;
    private String PostCode, City, Street, Voivodeship;
    private String Sector, UnderSector;
    private String ValueOfInvestment;

    public String getInvestmentName() {
        return InvestmentName;
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
}
