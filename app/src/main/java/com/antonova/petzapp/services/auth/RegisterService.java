package com.antonova.petzapp.services.auth;

import android.app.IntentService;
import android.content.Intent;

import com.antonova.petzapp.R;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;

public class RegisterService extends IntentService {
    private String username;
    private String password;
    public static final String ACTION_REGISTRATIONSERVICE="Signup successfull";
    public static final String EXTRA_KEY_OUT="ANSWER";
    public RegisterService() {
        super("RegisterService");
    }
    @Override
    protected void onHandleIntent(Intent intent) {
        username =  intent.getStringExtra("username");
        password= intent.getStringExtra("password");
        final String url = getString(R.string.base_uri)+"/auth/signup";
        String requestBody="{\n" +
                "\t\"username\": \""+username+"\",\n" +
                "\t\"role\":[\"user\"],\n" +
                "\t\"password\":\""+password+"\"\n" +
                "}";
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        RestTemplate restTemplate = new RestTemplate();
        HttpEntity<String> entity=new HttpEntity<String>(requestBody,headers);
        restTemplate.getMessageConverters().add(new StringHttpMessageConverter());
        Intent responseIntent = new Intent();
        responseIntent.setAction(ACTION_REGISTRATIONSERVICE);
        responseIntent.addCategory(Intent.CATEGORY_DEFAULT);
        try {
            ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.POST, entity, String.class);
            responseIntent.putExtra(EXTRA_KEY_OUT, response.getBody());

        } catch (HttpClientErrorException e) {
            responseIntent.putExtra(EXTRA_KEY_OUT, "Error");
        } catch (ResourceAccessException e) {
            responseIntent.putExtra(EXTRA_KEY_OUT, "Connection lost");
        }
        sendBroadcast(responseIntent);
    }
}


