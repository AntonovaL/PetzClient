package com.antonova.petzapp.interfaces;

import android.app.Activity;


public interface UserManageView {
    String getUsername();
    String getPassword();
    Activity getThis();
    void changeFragment(int num);
    void showMessage(String error);
    void showProgressBar();
    void hideProgressBar();
}
