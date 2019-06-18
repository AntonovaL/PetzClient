package com.antonova.petzapp.tools;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;

public class UserData implements Serializable {
    private String name;
    private String phoneNumber;
    private String city;
    private String type;
    public  UserData(){

    }
    public String getCity() {
        return city;
    }

    public String getName() {
        return name;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getType() {
        return type;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public void setType(String type) {
        this.type = type;
    }

    public JSONObject toJson(){
        JSONObject json=new JSONObject();
        try {
            json.put("name",name);
            json.put("phoneNumber",phoneNumber);
            json.put("city",city);
            json.put("type",type);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return json;
    }
}
