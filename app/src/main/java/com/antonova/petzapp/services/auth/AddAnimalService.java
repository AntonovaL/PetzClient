package com.antonova.petzapp.services.auth;

import android.app.IntentService;
import android.content.Intent;

import com.antonova.petzapp.R;
import com.antonova.petzapp.tools.Animal;

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

import java.nio.charset.Charset;

public class AddAnimalService extends IntentService {
    final static public String EXTRA_KEY_OUT = "ANIMAL_ADD_ANSW";
    final static public String ACTION_ADD = "ANIMAL_ADD";

    public AddAnimalService() {
        super("AddAnimalService");
    }

    @Override
    public void onHandleIntent(Intent intent) {
        String token=(String)intent.getSerializableExtra("Token");
        Intent responseIntent = new Intent();;
        responseIntent.setAction(ACTION_ADD);
        responseIntent.addCategory(Intent.CATEGORY_DEFAULT);
        Animal animal=(Animal)intent.getSerializableExtra("Animal");
        JSONObject requestJson=animal.toJson();
        String answ=addAnimal(requestJson,token);
        responseIntent.putExtra(EXTRA_KEY_OUT, answ);
        sendBroadcast(responseIntent);
    }


    public void onDestroy() {
        System.out.println("AddAnimalService destroyed");
    }
    public String addAnimal(JSONObject jsonObject, String token){
        if(token!=null) {
            final String url = getString(R.string.base_uri) + "/animals/add";
            HttpHeaders headers = new HttpHeaders();
            headers.set("Authorization", token);
            headers.setContentType(MediaType.APPLICATION_JSON);
            RestTemplate restTemplate = new RestTemplate();
            HttpEntity<String> entity = new HttpEntity<String>(jsonObject.toString(), headers);
            restTemplate.getMessageConverters().add(new StringHttpMessageConverter(Charset.forName("UTF-8")));
            try {
                ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.POST, entity, String.class);
                return "Ok";
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