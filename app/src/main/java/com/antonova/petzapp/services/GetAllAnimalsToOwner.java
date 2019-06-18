package com.antonova.petzapp.services;

import android.app.IntentService;
import android.content.Intent;
import android.content.Context;

import com.antonova.petzapp.R;
import com.antonova.petzapp.tools.Animal;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * An {@link IntentService} subclass for handling asynchronous task requests in
 * a service on a separate handler thread.
 * <p>
 * TODO: Customize class - update intent actions, extra parameters and static
 * helper methods.
 */
public class GetAllAnimalsToOwner extends IntentService {
    final static public String EXTRA_KEY_OUT = "ANIMALS";
    final static public String ACTION_GETANIMALLIST = "GET_ANIMALS";

    public GetAllAnimalsToOwner() {
        super("GetAllAnimalsToOwnerService");
    }

    @Override
    public void onHandleIntent(Intent intent) {
        String token=(String) intent.getSerializableExtra("Token");
        Intent responseIntent = new Intent();
        String mode=intent.getStringExtra("mode");
        responseIntent.setAction(ACTION_GETANIMALLIST);
        responseIntent.addCategory(Intent.CATEGORY_DEFAULT);
        String answ = getAnimals(token);
        responseIntent.putExtra(EXTRA_KEY_OUT, answ);
        sendBroadcast(responseIntent);
    }


    public void onDestroy() {
        System.out.println("GetAnimalstoOwner destroyed");
    }

    public String getAnimals(String token) {
        if(token!=null) {
            final String url = getString(R.string.base_uri) + "/animal/getAll";
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
