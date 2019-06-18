package com.antonova.petzapp.tools;

import org.json.JSONException;
import org.json.JSONObject;

public class JsonHelper {
     private JSONObject json;
     public JsonHelper(String json) throws JSONException {
         this.json=new JSONObject(json);
     }


     public UserData toUserData(){
         try{
             String name=json.get("name").toString();
             String phoneNumber=json.get("phoneNumber").toString();
             String city=json.get("city").toString();
             String type=json.get("type").toString();
             UserData ud=new UserData();
             ud.setCity(city);ud.setName(name);ud.setPhoneNumber(phoneNumber);ud.setType(type);
             return ud;
         }
         catch (JSONException e){
             return null;
         }
     }
}
