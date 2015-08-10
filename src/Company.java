import org.omg.CORBA.DynAnyPackage.InvalidValue;

/**
 * Created by sylwek on 09.08.15.
 */
public class Company {


    private String CompanyName;
    private String PostCode, City, Street;
    private String[] PhoneNumber, Emails, Urls;
    private Investment[] Investments;

    Company() {
        PhoneNumber = new String[0];
        Emails      = new String[0];
        Urls        = new String[0];
        Investments = new Investment[0];
    }

    public static void main(String[] args) throws InvalidValue {
        Company X = new Company();
        X.addEmail("andel@andel.pl");
        X.setCompanyName("Andel-Polska s. z o.o");
        X.setCity("Kraków");
        X.setPostCode("00-333");
        X.addUrl("www.andel.pl");
        X.addPhoneNumber("732 - 255 - 467");
        X.addPhoneNumber("(022) 122 144 14");
        X.print();
        X.printWithInvestments();
    }

    public void print(){

        System.out.printf("%-15s %15s %n", "----------", "------------------------------------------------------------------");
        System.out.printf("%-12s %-30s %10s %-25s %n", "Nazwa firmy", "\033[1m" + CompanyName + "\033[0m", "Miasto: ", "\033[1m" + PostCode + " " + City + "\033[0m");
        System.out.printf("%-12s %-30s %10s %-25s %-5s %-20s %n", "Telefon: ", "\033[1m" + PhoneNumber[0] + "\033[0m", "E-mail: ", "\033[1m" + Emails[0] +
                "\033[0m", "WWW: ", "\033[1m" + Urls[0] + "\033[0m");
        System.out.printf("%-15s %15s %n", "----------", "------------------------------------------------------------------");
    }

    public void printWithInvestments() {

        System.out.printf("%-15s %15s %n", "----------", "------------------------------------------------------------------");
        System.out.printf("%-12s %-30s %10s %-25s %n",  "Nazwa firmy", "\033[1m" + CompanyName+ "\033[0m", "Miasto: ", "\033[1m" + PostCode +" " + City +  "\033[0m" );
        System.out.printf("%-12s %-30s %10s %-25s %-5s %-20s %n","Telefon: ","\033[1m" + PhoneNumber[0]+ "\033[0m","E-mail: ", "\033[1m" + Emails[0]+
                "\033[0m", "WWW: ", "\033[1m" + Urls[0]+ "\033[0m");
        System.out.println("\t*********************************************************");
        System.out.printf("\033[1m" + "\tInwestycje: " + "\033[0m" + "%n");
        System.out.printf("%-15s %15s %n", "----------", "------------------------------------------------------------------");
    }

    public Investment[] getInvestments() { return Investments; }

    public void addInvestment(Investment _investment){
        Investment[] tmp = new Investment[Investments.length+1];
        for(int i =0; i<Investments.length;i++)
            tmp[i] = Investments[i];

        tmp[tmp.length-1] = _investment;

        Investments = tmp;
    }

    public String[] getUrls() { return Urls; }

    public void addUrl(String _url) throws InvalidValue {
        if(Validator.isUrlValid(_url)) {
            String[] tmp = new String[Urls.length + 1];
            for (int i = 0; i < Urls.length; i++)
                tmp[i] = Urls[i];

            tmp[tmp.length - 1] = _url;

            Urls = tmp;
        }
        else
            throw new InvalidValue("Nieprawidłowy adres url: \t" + _url);
    }


    public String[] getEmails() { return Emails; }

    public void addEmail(String _email) throws InvalidValue {
        if(Validator.isEmailValid(_email)) {
            String[] tmp = new String[Emails.length + 1];
            for (int i = 0; i < Emails.length; i++)
                tmp[i] = Emails[i];

            tmp[tmp.length - 1] = _email;

            Emails = tmp;
        }
        else
            throw new InvalidValue("Nieprawidłowy adres email: \t" + _email );
    }

    public String[] getPhoneNumber() { return PhoneNumber; }

    public void addPhoneNumber(String _phonenumber ) {
        String[] tmp = new String[PhoneNumber.length+1];
        for(int i =0; i<PhoneNumber.length;i++)
            tmp[i] = PhoneNumber[i];

        tmp[tmp.length-1] = _phonenumber;

        PhoneNumber = tmp;
    }

    public String getCompanyName() {return CompanyName; }

    public void setCompanyName(String _company_name) { CompanyName = _company_name; }


    public String getStreet() { return Street; }

    public void setStreet(String _street) { Street = _street; }

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
