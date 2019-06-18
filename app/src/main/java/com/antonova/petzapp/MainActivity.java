package com.antonova.petzapp;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.view.KeyEvent;

import com.antonova.petzapp.fragments.AddData;
import com.antonova.petzapp.fragments.ClientFragment;
import com.antonova.petzapp.fragments.OwnerFragment;
import com.antonova.petzapp.fragments.auth.LoginFragment;
import com.antonova.petzapp.fragments.auth.RegisterFragment;
import com.antonova.petzapp.tools.Animal;


public class MainActivity extends Activity {
    private FragmentManager fm;
    public static final String APP_PREFERENCES = "MY_APP";
    public static final String APP_PREFERENCES_USER_NAME = "Username";
    public static final String APP_PREFERENCES_TOKEN = "Token";
    private String username;
    AnimalRepository repository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        fm=getFragmentManager();
        changeFragment(1);
    }

    public void changeFragment(int number) {
        Fragment fragment = null;
        if (number == 1) {
            fragment = new LoginFragment();
            fm.beginTransaction()
                    .replace(R.id.fragment_layout, fragment,"LOGIN_FRAGMENT")
                    .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                    .commit();
        }
        if (number == 2) {
            fragment = new RegisterFragment();
            fm.beginTransaction()
                    .replace(R.id.fragment_layout, fragment)
                    .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                    .commit();
        }
        if (number == 3) {
            fragment = new AddData();
            fm.beginTransaction()
                    .replace(R.id.fragment_layout, fragment)
                    .addToBackStack("")
                    .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                    .commit();
        }
        if(number==4){
            fragment = new ClientFragment();

            fm.beginTransaction()
                    .replace(R.id.fragment_layout, fragment)
                    .addToBackStack("")
                    .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                    .commit();
        }
        if(number==5){
            fragment = new OwnerFragment();
            fm.beginTransaction()
                    .replace(R.id.fragment_layout, fragment,"OWNER_FRAGMENT")
                    .addToBackStack("")
                    .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                    .commit();
        }
    }

    public void setToken(String token) {
        SharedPreferences preferences=this.getSharedPreferences(APP_PREFERENCES,Context.MODE_PRIVATE);
        preferences.edit().putString(APP_PREFERENCES_TOKEN,token).apply();
    }
    public void setUserName(String userName) {
        username=userName;
        SharedPreferences preferences=this.getSharedPreferences(APP_PREFERENCES,Context.MODE_PRIVATE);
        preferences.edit().putString(APP_PREFERENCES_USER_NAME,userName).apply();
    }

    public AnimalRepository getStorage(){
        return repository;
    }

    @Override
    public void onDestroy(){
        super.onDestroy();
    }



}




