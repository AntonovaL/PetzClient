package com.antonova.petzapp.services;

import android.app.IntentService;
import android.content.Intent;
import android.content.Context;

import com.antonova.petzapp.R;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;

public class GetAnimalsToClient extends IntentService {
    final static public String EXTRA_KEY_OUT = "ANIMALSTOCLIENT";
    final static public String ACTION_ANIMALTOCLIENT= "GET_ANIMALS";

    public GetAnimalsToClient() {
        super("AnimalToClient");
    }

    @Override
    public void onHandleIntent(Intent intent) {
        String token=(String) intent.getSerializableExtra("Token");
        Intent responseIntent = new Intent();
        responseIntent.setAction(ACTION_ANIMALTOCLIENT);
        responseIntent.addCategory(Intent.CATEGORY_DEFAULT);
        String answ = getAnimals(token);
        responseIntent.putExtra(EXTRA_KEY_OUT, answ);
        sendBroadcast(responseIntent);
    }


    public void onDestroy() {
        System.out.println("AnimalToClient destroyed");
    }

    public String getAnimals(String token) {
        if(token!=null) {
            final String url = getString(R.string.base_uri) + "/animal/getByRating";
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
