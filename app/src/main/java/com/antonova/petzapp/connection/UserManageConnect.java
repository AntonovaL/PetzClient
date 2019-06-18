package com.antonova.petzapp.connection;

import android.app.Activity;
import android.content.Intent;

import com.antonova.petzapp.services.UserDataRecieveService;
import com.antonova.petzapp.services.UserDataSendService;
import com.antonova.petzapp.services.auth.LoginService;
import com.antonova.petzapp.services.auth.RegisterService;
import com.antonova.petzapp.tools.UserData;


public class UserManageConnect {

    public void login(String username, String password, Activity activity){
        Intent login = new Intent(activity, LoginService.class);
        activity.startService(login.putExtra("username", username).putExtra("password", password));
    }

    public void register(String username,String password, Activity activity){
        Intent register = new Intent(activity, RegisterService.class);
        activity.startService(register.putExtra("username", username).putExtra("password", password));

    }

    public void getUserData(String t,Activity activity){
       Intent recieveData = new Intent(activity, UserDataRecieveService.class);
        activity.startService(recieveData.putExtra("Token", t));
    }

    public void sendUserData(String t,Activity activity, UserData ud){
         Intent sendData = new Intent(activity, UserDataSendService.class);
         activity.startService(sendData.putExtra("Token", t).putExtra("UserData",ud));
    }


}
