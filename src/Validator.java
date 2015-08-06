import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by sylwek on 06.08.15.
 */
public class Validator {

    private static final String EmailRegex =  "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
            + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
    private static final String UrlRegex = "(@)?(href=')?(HREF=')?(HREF=\")?(href=\")?(http://)?[a-zA-Z_0-9\\-]+(\\.\\w[a-zA-Z_0-9\\-]+)+(/[#&\\n\\-=?\\+\\%/\\.\\w]+)?"; //"[-a-zA-Z0-9@:%._\\+~#=]{2,256}\\.[a-z]{2,6}\\b([-a-zA-Z0-9@:%_\\+.~#?&//=]*)";
    private static final String PostCodeRegex = "[0-9]{2}-[0-9]{3}";
    public Validator() { }

    public static  boolean isEmailValid(String _email){
        Pattern p = Pattern.compile(EmailRegex);
        Matcher m = p.matcher(_email);

        return m.matches();
    }

    public static boolean isUrlValid(String _url){
        Pattern p = Pattern.compile(UrlRegex);
        Matcher m = p.matcher(_url);

        return m.matches();
    }

    public static boolean isPostCodeValid(String _postcode){
        Pattern p = Pattern.compile(PostCodeRegex);
        Matcher m = p.matcher(_postcode);

        return m.matches();
    }
}
