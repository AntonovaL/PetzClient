package com.antonova.petzapp.presenters;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;

import com.antonova.petzapp.interfaces.UserManageView;
import com.antonova.petzapp.connection.UserManageConnect;
import com.antonova.petzapp.services.auth.RegisterService;

public class RegistrationPresenter {
    UserManageView view;
    public RegistrationPresenter(UserManageView view){
        this.view=view;
    }

    public void tryRegistrateUser(){
        view.showProgressBar();
        UserManageConnect repository=new UserManageConnect();
        String username=view.getUsername();
        String password=view.getPassword();
        repository.register(username,password,view.getThis());
        RegBroadcastReceiver myBroadcastReceiver = new RegBroadcastReceiver();
        IntentFilter intentFilter = new IntentFilter(RegisterService.ACTION_REGISTRATIONSERVICE);
        intentFilter.addCategory(Intent.CATEGORY_DEFAULT);
        view.getThis().registerReceiver(myBroadcastReceiver, intentFilter);
    }
    public class RegBroadcastReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            view.hideProgressBar();
            String result = intent.getStringExtra(RegisterService.EXTRA_KEY_OUT);
            if(result.equals("User registered successfully!")){
                view.changeFragment(1);
                view.showMessage("Вы успешно зарегистрированы");
            }
            else{
                if (result.equals("Connection lost")){
                    view.showMessage("Соединение потеряно");
                }
                if (result.equals("Error")){
                    view.showMessage("Это имя пользователя уже занято");
                }
            }
        }
    }
}
