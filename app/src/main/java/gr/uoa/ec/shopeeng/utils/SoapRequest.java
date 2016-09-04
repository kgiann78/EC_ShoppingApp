package gr.uoa.ec.shopeeng.utils;

import android.os.AsyncTask;
import android.util.Log;
import gr.uoa.ec.shopeeng.models.User;
import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.util.Map;
import java.util.Objects;

import static gr.uoa.ec.shopeeng.utils.Constants.NAMESPACE;
import static gr.uoa.ec.shopeeng.utils.Constants.URL;

public abstract class SoapRequest extends AsyncTask<String, Void, Object> {

    protected Object soapCallWithProperties(String methodName, String soapAction, Map<String, Object> properties) throws IOException, XmlPullParserException {
        SoapObject request = new SoapObject(NAMESPACE, methodName);


/*<x:Envelope xmlns:x="http://schemas.xmlsoap.org/soap/envelope/" xmlns:acc="http://shopeeng/account/">
    <x:Header/>
    <x:Body>
        <acc:loginRequest>
            <acc:username>test</acc:username>
            <acc:password>test</acc:password>
        </acc:loginRequest>
    </x:Body>
</x:Envelope>*/

        for (String key : properties.keySet()) {
            request.addProperty(key, properties.get(key));
        }

        Log.i("Soap request ", request.getName());
        Log.i("Soap request ", request.toString());

        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER10);
        envelope.setOutputSoapObject(request);
        envelope.dotNet = false;

        HttpTransportSE httpTransport = new HttpTransportSE(URL);
        httpTransport.debug = true;
        Log.i("Soap Action ", soapAction.toString());
        httpTransport.call(soapAction, envelope);

        return envelope.getResponse();
    }

    protected Object soapCall(String methodName, String soapAction) throws IOException, XmlPullParserException {
        SoapObject request = new SoapObject(NAMESPACE, methodName);

        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER10);
        envelope.setOutputSoapObject(request);
        envelope.dotNet = false;

        HttpTransportSE httpTransport = new HttpTransportSE(URL);
        httpTransport.debug = true;
        httpTransport.call(soapAction, envelope);
        return envelope.getResponse();
    }

    @Override
    protected void onPostExecute(Object object) {
        super.onPostExecute(object);
    }

    ;
}
