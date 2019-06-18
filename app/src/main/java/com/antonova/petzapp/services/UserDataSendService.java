package com.antonova.petzapp.services;

import android.app.IntentService;
import android.content.Intent;

import com.antonova.petzapp.R;
import com.antonova.petzapp.tools.UserData;

import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.FormHttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;

import java.nio.charset.Charset;

public class UserDataSendService extends IntentService {
    static final public String EXTRA_KEY_OUT = "DATA";
    static final  public String ACTION_SEND = "USER_DATA_SEND_SERVICE";

    public UserDataSendService() {
        super("UserDataSendService");
    }

    @Override
    public void onHandleIntent(Intent intent) {
        String token=(String) intent.getSerializableExtra("Token");
        Intent responseIntent = new Intent();;
        responseIntent.setAction(ACTION_SEND);
        responseIntent.addCategory(Intent.CATEGORY_DEFAULT);
        UserData ud=(UserData)intent.getSerializableExtra("UserData");
        String answ=sendUserData(ud.toJson(),token);
        responseIntent.putExtra(EXTRA_KEY_OUT, answ);
        sendBroadcast(responseIntent);
    }


    public void onDestroy() {
        System.out.println("UserDataRecieveService destroyed");
    }

    public String sendUserData(JSONObject jsonObject, String token){
        if(token!=null) {
            final String url = getString(R.string.base_uri) + "/data/addUserData";
            HttpHeaders headers = new HttpHeaders();
            headers.set("Authorization", token);
            headers.setContentType(MediaType.APPLICATION_JSON);
            RestTemplate restTemplate = new RestTemplate();
            HttpEntity<String> entity = new HttpEntity<String>(jsonObject.toString(), headers);
            restTemplate.getMessageConverters().add(new StringHttpMessageConverter(Charset.forName("UTF-8")));
            try {
                ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.POST, entity, String.class);
                return "Data added successfully!";
            } catch (HttpClientErrorException e) {
                return "ERROR";
            } catch (ResourceAccessException e) {
                return  "Connection lost";
            }
        }
        else{
            return "ERROR";
        }
    }
}