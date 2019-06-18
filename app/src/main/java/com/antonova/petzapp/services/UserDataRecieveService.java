package com.antonova.petzapp.services;

import android.app.IntentService;
import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.provider.ContactsContract;

import com.antonova.petzapp.R;
import com.antonova.petzapp.tools.UserData;

import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;

public class UserDataRecieveService extends IntentService {
    final static public String EXTRA_KEY_OUT = "DATA";
    final static public String ACTION_GETDATA = "";

    public UserDataRecieveService() {
        super("UserDataRecieveService");
    }

    @Override
    public void onHandleIntent(Intent intent) {
        String token=(String) intent.getSerializableExtra("Token");
        Intent responseIntent = new Intent();
        String mode=intent.getStringExtra("mode");
        responseIntent.setAction(ACTION_GETDATA);
        responseIntent.addCategory(Intent.CATEGORY_DEFAULT);
        String answ = getUserData(token);
        responseIntent.putExtra(EXTRA_KEY_OUT, answ);
        sendBroadcast(responseIntent);
    }


    public void onDestroy() {
        System.out.println("UserDataRecieveService destroyed");
    }

    public String getUserData(String token) {
        if(token!=null) {
            final String url = getString(R.string.base_uri) + "/data/getUserData";
            HttpHeaders headers = new HttpHeaders();
            headers.set("Authorization", token);
            RestTemplate restTemplate = new RestTemplate();
            HttpEntity<String> entity = new HttpEntity<String>("", headers);
            restTemplate.getMessageConverters().add(new StringHttpMessageConverter());
            try {
                ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, entity, String.class);
                return response.getBody();
            } catch (HttpClientErrorException e) {
                return "NOT EXISTS";
            } catch (ResourceAccessException e) {
                return "Connection lost";
            }
        }
        else{
            return "NOT EXISTS";
        }
    }

}
