package gr.uoa.ec.shopeeng;

import android.util.Log;
import gr.uoa.ec.shopeeng.models.User;
import org.ksoap2.serialization.SoapObject;

public class UserConverter {

    public static User ConvertFromSoap(SoapObject soapObject) {
        if (soapObject == null) return null;
        Log.i("soap_response", soapObject.toString());
        User user = new User();
        user.setUsername(soapObject.getProperty("username").toString());
        user.setPassword(soapObject.getProperty("password").toString());
        user.setName(soapObject.getProperty("name").toString());
        user.setLastname(soapObject.getProperty("lastname").toString());
        return user;
    }
}
