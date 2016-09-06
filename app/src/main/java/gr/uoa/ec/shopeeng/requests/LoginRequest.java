package gr.uoa.ec.shopeeng.requests;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;
import gr.uoa.ec.shopeeng.MainActivity;
import gr.uoa.ec.shopeeng.R;
import gr.uoa.ec.shopeeng.UserConverter;
import gr.uoa.ec.shopeeng.fragments.SearchFragment;
import gr.uoa.ec.shopeeng.models.Login;
import gr.uoa.ec.shopeeng.models.User;
import gr.uoa.ec.shopeeng.utils.Constants;
import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.PropertyInfo;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;

import static gr.uoa.ec.shopeeng.utils.Constants.*;


public class LoginRequest extends AsyncTask<String, Void, User> {

    private Context applicationContext;
    private FragmentManager fragmentManager;
    private String username;
    private String password;

    public LoginRequest(Context applicationContext, FragmentManager fragmentManager, String username, String password) {
        this.applicationContext = applicationContext;
        this.fragmentManager = fragmentManager;
        this.username = username;
        this.password = password;
    }


    @Override
    protected User doInBackground(String... params) {
        try {
            String methodName = "loginRequest";
            String soapAction = NAMESPACE + SERVICE + "/loginRequest";

            SoapObject request = new SoapObject(NAMESPACE, methodName);
            SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
            envelope.implicitTypes = true;

            PropertyInfo loginPropertyInfo = new PropertyInfo();
            loginPropertyInfo.setNamespace(NAMESPACE);
            loginPropertyInfo.setName("login");
            loginPropertyInfo.setValue(new Login(username, password));
            loginPropertyInfo.setType(Login.class);

            request.addProperty(loginPropertyInfo);
            envelope.setOutputSoapObject(request);
            envelope.dotNet = false;

            HttpTransportSE httpTransport = new HttpTransportSE(URL);
            httpTransport.debug = true;

            httpTransport.call(soapAction, envelope);
            SoapObject response = (SoapObject) envelope.getResponse();

            return UserConverter.ConvertFromSoap(response);
        } catch (Exception e) {
            Log.e("Login Request", e.getMessage(), e);
        }

        return null;
    }

    @Override
    protected void onPostExecute(final User user) {
        try {
            if (user == null) {
                Toast.makeText(applicationContext,
                        "Η σύνδεσή σου απέτυχε... Προσπάθησε ξανά!", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(applicationContext,
                        "Επιτυχής σύνδεση!", Toast.LENGTH_SHORT).show();

                SearchFragment searchFragment = new SearchFragment();
                fragmentManager
                        .beginTransaction()
                        .replace(R.id.fragment_container, searchFragment)
                        .addToBackStack(null)
                        .commit();
            }
        } catch (Exception e) {
            Log.e("Registration Error", e.toString());
        }
    }
}
