package gr.uoa.ec.shopeeng.requests;

import android.content.Context;
import android.util.Log;
import gr.uoa.ec.shopeeng.UserConverter;
import gr.uoa.ec.shopeeng.models.User;
import gr.uoa.ec.shopeeng.utils.SoapRequest;
import org.ksoap2.serialization.SoapObject;

import java.util.HashMap;
import java.util.Map;

import static gr.uoa.ec.shopeeng.utils.Constants.URL;


public class LoginRequest extends SoapRequest {

    private Context applicationContext;
    private String username;
    private String password;

    public LoginRequest(String username, String password) {
        this.username = username;
        this.password = password;
    }


    @Override
    protected User doInBackground(String... params) {
        try {
            Map requestParameters = new HashMap<>();
            requestParameters.put("username", username);
            requestParameters.put("password", password);
            SoapObject response = (SoapObject) super.soapCallWithProperties("loginRequest", URL, requestParameters);

            return UserConverter.ConvertFromSoap(response);
        } catch (Exception e) {
            Log.e("Login Request", e.getMessage(), e);
        }

        return null;
    }

    @Override
    protected void onPostExecute(final Object user) {
/*add error here*/
        /*
        if (user != null) {
            finish();
        } else {
            mPasswordView.setError(getString(R.string.error_incorrect_password));
            mPasswordView.requestFocus();
        }*/

    }
}
