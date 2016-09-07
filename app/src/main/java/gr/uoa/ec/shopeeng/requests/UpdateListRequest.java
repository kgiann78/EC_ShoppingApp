package gr.uoa.ec.shopeeng.requests;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import gr.uoa.ec.shopeeng.utils.Util;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;


public class UpdateListRequest extends AsyncTask<Void, Void, ArrayList<Void>> {
    private String username;
    private String productId;
    private String storeId;
    private String price;
    private Context applicationContext;

    public UpdateListRequest(String username, String productId, String storeId, String price, Context applicationContext) {
        this.username = username;
        this.productId = productId;
        this.storeId = storeId;
        this.price = price;
        this.applicationContext = applicationContext;
    }

    @Override
    protected ArrayList<Void> doInBackground(Void... params) {
        try {
            RestTemplate restTemplate = new RestTemplate();
            restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
            restTemplate.postForObject(buildUrl(productId, storeId, username, price), null, null);
            Log.i("Updated item to list!:", productId);
        } catch (Exception e) {
            Log.e(UpdateListRequest.class.getName(), e.getMessage(), e);
        }
        return null;
    }


    private String buildUrl(String productId, String storeId, String username, String price) throws Exception {
        StringBuilder url = new StringBuilder();
        url.append(Util.getProperty("endpoint", applicationContext));
        url.append(Util.getProperty("updateItemList", applicationContext));

        Log.i(" buildUrl", String.format(url.toString(), username, productId, storeId, price));
        return String.format(url.toString(), username, productId, storeId, price);
    }

}
