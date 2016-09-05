package gr.uoa.ec.shopeeng.requests;

import android.support.v4.app.FragmentManager;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;
import gr.uoa.ec.shopeeng.R;
import gr.uoa.ec.shopeeng.UserConverter;
import gr.uoa.ec.shopeeng.fragments.SearchFragment;
import gr.uoa.ec.shopeeng.models.User;
import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.PropertyInfo;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import static gr.uoa.ec.shopeeng.utils.Constants.NAMESPACE;
import static gr.uoa.ec.shopeeng.utils.Constants.SERVICE;
import static gr.uoa.ec.shopeeng.utils.Constants.URL;


public class RegisterRequest extends AsyncTask<String, Void, User> {
    private final String name;
    private final String lastname;
    private String username;
    private String password;
    private Context applicationContext;
    private FragmentManager fragmentManager;

    public RegisterRequest(Context applicationContext, FragmentManager fragmentManager, String username, String password, String name, String lastname) {
        this.applicationContext = applicationContext;
        this.fragmentManager = fragmentManager;
        this.username = username;
        this.password = password;
        this.name = name;
        this.lastname = lastname;
    }


    @Override
    protected User doInBackground(String... params) {
        try {
            String methodName = "registerRequest";
            String soapAction = NAMESPACE + SERVICE + "/registerRequest";

            SoapObject request = new SoapObject(NAMESPACE, methodName);
            SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
            envelope.implicitTypes = true;

            PropertyInfo userPropertyInfo = new PropertyInfo();
            userPropertyInfo.setNamespace(NAMESPACE);
            userPropertyInfo.setName("user");
            userPropertyInfo.setValue(new User(username, password, name, lastname));
            userPropertyInfo.setType(User.class);

            request.addProperty(userPropertyInfo);
            envelope.setOutputSoapObject(request);
            envelope.dotNet = false;

            HttpTransportSE httpTransport = new HttpTransportSE(URL);
            httpTransport.debug = true;

            httpTransport.call(soapAction, envelope);

            SoapObject response = (SoapObject) envelope.getResponse();

            return UserConverter.ConvertFromSoap(response);
        } catch (Exception e) {
            Log.e("Register Request", e.getMessage(), e);
        }

        return null;
    }

    @Override
    protected void onPostExecute(final User user) {
        try {
            if (user == null) {
                Toast.makeText(applicationContext,
                        "Η εγγραφή σου απέτυχε... Προσπάθησε ξανά!", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(applicationContext,
                        "Επιτυχής εγγραφή!", Toast.LENGTH_SHORT).show();

                SearchFragment searchFragment = new SearchFragment();
                Bundle args = new Bundle();
                args.putSerializable("USER", user);
                searchFragment.setArguments(args);

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
