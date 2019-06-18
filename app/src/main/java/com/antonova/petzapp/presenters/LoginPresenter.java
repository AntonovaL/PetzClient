package com.antonova.petzapp.presenters;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;

import com.antonova.petzapp.MainActivity;
import com.antonova.petzapp.interfaces.UserManageView;
import com.antonova.petzapp.connection.UserManageConnect;
import com.antonova.petzapp.services.UserDataRecieveService;
import com.antonova.petzapp.services.auth.LoginService;
import com.antonova.petzapp.tools.JsonHelper;
import com.antonova.petzapp.tools.UserData;

import org.json.JSONException;
import org.json.JSONObject;

public class LoginPresenter {
    UserManageView view;
    UserManageConnect repository;
    UserDataRecieveBroadcastReceiver dataBroadcastReceiver;
    LogBroadcastReceiver logBroadcastReceiver;
    String username;
    public LoginPresenter(UserManageView userManageView){
        this.view = userManageView;
    }
    public  void trySignUp(){
        view.showProgressBar();
        username= view.getUsername();
        String password= view.getPassword();
        repository=new UserManageConnect();
        logBroadcastReceiver=new LogBroadcastReceiver();
        IntentFilter intentFilter = new IntentFilter(LoginService.ACTION_LOGINSERVICE);
        intentFilter.addCategory(Intent.CATEGORY_DEFAULT);
        view.getThis().registerReceiver(logBroadcastReceiver, intentFilter);
        repository.login(username,password, view.getThis());
    }

    public void checkUserData(String t){
        view.showProgressBar();
        repository.getUserData(t,view.getThis());
        IntentFilter intentFilter = new IntentFilter(UserDataRecieveService.ACTION_GETDATA);
        intentFilter.addCategory(Intent.CATEGORY_DEFAULT);
        dataBroadcastReceiver=new UserDataRecieveBroadcastReceiver();
        view.getThis().registerReceiver(dataBroadcastReceiver, intentFilter);
    }
    public class LogBroadcastReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            String result = intent.getStringExtra(LoginService.EXTRA_KEY_OUT);
            if (result.equals("Error")){
                view.hideProgressBar();
                view.showMessage("Неверное имя пользователя или пароль");
            }
            else {
                if (result.equals("Connection lost")) {
                    view.hideProgressBar();
                    view.showMessage("Соединение потеряно");
                }
                else{
                    try {
                        JSONObject jsonObject=new JSONObject(result);
                        String token = jsonObject.get("tokenType")+" "+jsonObject.get("accessToken");
                        MainActivity ma=(MainActivity) view.getThis();
                        ma.setToken(token);
                        ma.setUserName(username);
                        view.hideProgressBar();
                        checkUserData(token.toString());
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
           // view.getThis().unregisterReceiver(logBroadcastReceiver);
        }
    }


    public class UserDataRecieveBroadcastReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            String answer = intent.getStringExtra(UserDataRecieveService.EXTRA_KEY_OUT);
            if (answer.equals("NOT EXISTS")) {
                view.hideProgressBar();
                view.changeFragment(3);
            }
            else{
                try {
                    JsonHelper jsonHelper = new JsonHelper(answer);
                    UserData ud=jsonHelper.toUserData();
                    view.hideProgressBar();
                    if (ud.getType().equals("CLIENT")){
                        view.changeFragment(4);
                    }
                    else{
                        view.changeFragment(5);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
           // view.getThis().unregisterReceiver(dataBroadcastReceiver);
        }

    }
}
