package gr.uoa.ec.shopeeng.requests;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import gr.uoa.ec.shopeeng.utils.Util;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;


public class DeleteItemListRequest extends AsyncTask<Void, Void, ArrayList<Void>> {
    private String username;
    private String productId;
    private Context applicationContext;

    public DeleteItemListRequest(String username, String productId, Context applicationContext) {
        this.username = username;
        this.productId = productId;

        this.applicationContext = applicationContext;
    }

    @Override
    protected ArrayList<Void> doInBackground(Void... params) {
        try {
            RestTemplate restTemplate = new RestTemplate();
            restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
            restTemplate.postForObject(buildUrl(productId, username),Void.class,null);
            Log.i("Deleted item ", productId);
        } catch (Exception e) {
            Log.e(DeleteItemListRequest.class.getName(), e.getMessage(), e);
        }
        return null;
    }


    private String buildUrl(String productId, String username) throws Exception {
        StringBuilder url = new StringBuilder();
        url.append(Util.getProperty("endpoint", applicationContext));
        url.append(Util.getProperty("deleteItemList", applicationContext));

        Log.i(" buildUrl", String.format(url.toString(), username, productId));
        return String.format(url.toString(), username, productId);
    }

}
