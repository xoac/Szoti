import java.sql.*;

/**
 * Created by sylwek on 22.08.15.
 */
public class ExportToDB {

    private static final String DB_DRIVER = "org.h2.Driver";
    private static final String DB_CONNECTION = "jdbc:h2:~/git/Szoti/h2/h2";
    private static final String DB_USER = "";
    private static final String DB_PASSWORD = "";

    //SQL
    private static final String DB_CREATE_CONNECTION_CP_IT = "CREATE TABLE IF NOT EXISTS CP_IT" +
            "(" +
            "id int AUTO_INCREMENT NOT NULL," +
            "IdCompany int NOT NULL," +
            "IdInvestment int NOT NULL," +
            "PRIMARY KEY(id)" +
            ")" ;

    private static final String DB_CREATE_COMPANY = "CREATE TABLE IF NOT EXISTS COMPANY" +
            "(" +
            "id int AUTO_INCREMENT NOT NULL," +
            "CompanyName varchar(255) NOT NULL," +
            "PostCode varchar(255)," +
            "City varchar(255)," +
            "Street varchar(255)," +
            "PhoneNumber1 varchar(255)," +
            "PhoneNumber2 varchar(255)," +
            "Email1 varchar(255)," +
            "Eamil2 varchar(255)," +
            "Url1 varchar(255)," +
            "Url2 varchar(255)," +
            "PRIMARY KEY(id)" +
            ")";

    private static final String DB_CREATE_INVESTMENT = "CREATE TABLE IF NOT EXISTS INVESTMENT" +
            "(" +
            "id int AUTO_INCREMENT NOT NULL," +
            "InvestmentId int,"+
            "InvestmentName varchar(255) NOT NULL," +
            "PostCode varchar(255)," +
            "City varchar(255)," +
            "Street varchar(255)," +
            "Voivodeship varchar(255)," +
            "Sektor varchar(255)," +
            "UnderSector varchar(255)," +
            "ValueOfInvestment varchar(255)," +
            "PRIMARY KEY(id)" +
            ")";


    private static Connection getDBConnection() {
        Connection dbConnection = null;
        try {
            Class.forName(DB_DRIVER);
        } catch (ClassNotFoundException e) {
            System.out.println(e.getMessage());
        }
        try {
            dbConnection = DriverManager.getConnection(DB_CONNECTION, DB_USER,
                    DB_PASSWORD);
            return dbConnection;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return dbConnection;
    }

    ExportToDB(Company[] _companies) throws SQLException {
        insertWithStatementCompany(_companies);
    }

    private static void insertWithStatementCompany(Company[] _companies) throws SQLException {
        Connection connection = getDBConnection();
        Statement stmt = null;
        try {
            connection.setAutoCommit(false);
            stmt = connection.createStatement();
            stmt.execute(DB_CREATE_COMPANY);
            stmt.execute(DB_CREATE_INVESTMENT);
            stmt.execute(DB_CREATE_CONNECTION_CP_IT);
            final String DB_INSERT_INTO_COMPANY = "INSERT INTO COMPANY " +
                    "(CompanyName,PostCode,City,Street,PhoneNumber1,PhoneNumber2,Email1 ,Eamil2,Url1 ,Url2) VALUES ";

            for(int i=0;_companies[i]!=null ;i++) {
                String ph1 = _companies[i].getPhoneNumber().length > 0 ? _companies[i].getPhoneNumber()[0]:"";
                String ph2 = _companies[i].getPhoneNumber().length > 1 ? _companies[i].getPhoneNumber()[1]:"";
                String em1 = _companies[i].getEmails().length > 0 ? _companies[i].getEmails()[0]:"";
                String em2 = _companies[i].getEmails().length > 1 ? _companies[i].getEmails()[1]:"";
                String ur1 = _companies[i].getUrls().length > 0 ? _companies[i].getUrls()[0]:"";
                String ur2 = _companies[i].getUrls().length > 1 ? _companies[i].getUrls()[1]:"";


                stmt.execute(DB_INSERT_INTO_COMPANY +"(" +
                        " ' " +_companies[i].getCompanyName()    +" ', " +
                        " ' " + _companies[i].getPostCode()      +" ', " +
                        " ' " +_companies[i].getCity()           +" ', " +
                        " ' " +_companies[i].getStreet()         +" ', " +
                        " ' " +ph1        +" ', " +
                        " ' " +ph2       +" ', " +
                        " ' " +em1      +" ', " +
                        " ' " +em2         +" ', " +
                        " ' " +ur1       +" ', " +
                        " ' " +ur2      +" ') " );

                Investment[] inw = _companies[i].getInvestments();
                final String DB_INSERT_INTO_INVESTMENT = "INSERT INTO INVESTMENT " +
                        "(InvestmentId,InvestmentName,PostCode,City,Street,Voivodeship,Sektor,UnderSector ,ValueOfInvestment) VALUES ";
                for(int j=0;j<inw.length;j++) {

                    stmt.execute(DB_INSERT_INTO_INVESTMENT + "(" +
                            "  " + inw[j].getInvestmentId() + " , " +
                            " ' " + inw[j].getInvestmentName() + " ', " +
                            " ' " + inw[j].getPostCode() + " ', " +
                            " ' " + inw[j].getCity() + " ', " +
                            " ' " + inw[j].getStreet() + " ', " +
                            " ' " + inw[j].getVoivodeship() + " ', " +
                            " ' " + inw[j].getSector() + " ', " +
                            " ' " + inw[j].getUnderSector() + " ', " +
                            " ' " + inw[j].getValueOfInvestment() + " ') ");
                    //TODO warunek na to że takie połączenie w bazie już istnieje

                    stmt.execute("INSERT INTO CP_IT" +
                            "(IdCompany,IdInvestment) VALUES" + "(" +
                            "  " + _companies[i].getId() + " , " +
                            "  " + inw[j].getId() + " ) ");

                }

            }



            stmt.close();
            connection.commit();
        } catch (SQLException e) {
            System.out.println("Exception Message " + e.getLocalizedMessage());
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            connection.close();
        }
    }
}
