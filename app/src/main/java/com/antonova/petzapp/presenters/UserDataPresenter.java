package com.antonova.petzapp.presenters;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;

import com.antonova.petzapp.fragments.AddData;
import com.antonova.petzapp.connection.UserManageConnect;
import com.antonova.petzapp.services.UserDataSendService;
import com.antonova.petzapp.tools.UserData;

public class UserDataPresenter {
    AddData view;
    UserManageConnect repository;
    UserData ud;
    public UserDataPresenter(AddData view){
        this.view=view;
        repository=new UserManageConnect();
    }

    public void addUserData(String token){
        ud=new UserData();
        view.showProgressBar();
        if(view.getType().equals("Пользователь")){
            ud.setType("CLIENT");
        }
        else{
            ud.setType("OWNER");
        }
        ud.setCity(view.getCity());
        ud.setName(view.getName());
        ud.setPhoneNumber(view.getPhone());
        repository.sendUserData(token,view.getActivity(),ud);
        IntentFilter intentFilter = new IntentFilter(UserDataSendService.ACTION_SEND);
        intentFilter.addCategory(Intent.CATEGORY_DEFAULT);
        UserDataSendBroadcastReceiver myBroadcastReceiver=new UserDataSendBroadcastReceiver();
        view.getActivity().registerReceiver(myBroadcastReceiver, intentFilter);
    }

    public class UserDataSendBroadcastReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            String answer = intent.getStringExtra(UserDataSendService.EXTRA_KEY_OUT);
            if (answer.equals("Data added successfully!")) {
                if(ud.getType().equals("CLIENT")){
                    view.hideProgressBar();
                    view.changeFragment(4);
                }
                else{
                    view.hideProgressBar();
                    view.changeFragment(5);
                }
            }
            else{
                if(answer.equals("Connection lost")){
                    view.hideProgressBar();
                    view.showMessage("Соединение потеряно");
                }
                if(answer.equals("ERROR")){
                    view.hideProgressBar();
                    view.showMessage("Что-то пошло не так");
                }
            }
        }
    }

}
