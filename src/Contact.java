import org.omg.CORBA.DynAnyPackage.InvalidValue;

//set remove repiting items;

/**
 * Created by sylwek on 06.08.15.
 */
public class Contact {
    private String Id;
    private String FirstName, LastName;
    private String City, PostCode, Adress;
    private String PhoneNumber, EmailAdress, Url;
    private String AdditionalInformation, Status;



    public Contact() {

    }

    public static void main(String[] args) throws InvalidValue {

    }

    public String getId() {
        return this.Id;
    }

    public void setId(String id) {
        this.Id = id;
    }

    public String getFirstName() {
        return this.FirstName;
    }

    public void setFirstName(String FN) {
        this.FirstName = FN;
    }

    public String getLastName() {
        return this.LastName;
    }

    public void setLastName(String LN) {
        this.LastName = LN;
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

    public String getAdress() {
        return this.Adress;
    }

    public void setAdress(String A) {
        Adress = A;
    }

    public String getPhoneNumber() {
        return this.PhoneNumber;
    }

    public void setPhoneNumber(String PN) {
        PhoneNumber = PN;
    }

    public String getEmailAdress() {
        return this.EmailAdress;
    }

    public void setEmailAdress(String _email) throws InvalidValue {
        if (Validator.isEmailValid(_email))
            EmailAdress = _email;
        else
            throw new InvalidValue("Nieprawidłowy adres email: \t\'" + _email + "\'");
    }

    public String getUrl() {
        return this.Url;
    }

    public void setUrl(String _url) throws InvalidValue {
        if (Validator.isUrlValid(_url))
            Url = _url;
        else
            throw new InvalidValue("Nieprawidłowy adres url: \t \' " + _url + "\'");
    }

}
