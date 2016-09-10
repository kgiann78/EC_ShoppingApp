package gr.uoa.ec.shopeeng.requests;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import gr.uoa.ec.shopeeng.models.ListItem;
import gr.uoa.ec.shopeeng.utils.Util;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Arrays;


public class GetListRequest extends AsyncTask<Void, Void, ArrayList<ListItem>> {
    private String username;
    private Context applicationContext;

    public GetListRequest(String username, Context applicationContext) {
        this.username = username;
        this.applicationContext = applicationContext;
    }

    @Override
    protected ArrayList<ListItem> doInBackground(Void... params) {
        ArrayList results = new ArrayList();
        try {
            RestTemplate restTemplate = new RestTemplate();
            restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
            results.addAll(Arrays.asList(restTemplate.getForObject(buildUrl(username), ListItem[].class)));
        } catch (Exception e) {
            Log.e(GetListRequest.class.getName(), e.getMessage(), e);
        }
        return results;
    }


    private String buildUrl(String username) throws Exception {
        StringBuilder url = new StringBuilder();
        url.append(Util.getProperty("endpoint", applicationContext));
        url.append(Util.getProperty("getItemList", applicationContext));
        return String.format(url.toString(), username);
    }
}
