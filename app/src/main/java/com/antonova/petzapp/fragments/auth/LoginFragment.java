package com.antonova.petzapp.fragments.auth;

import android.app.Activity;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.antonova.petzapp.MainActivity;
import com.antonova.petzapp.R;
import com.antonova.petzapp.presenters.LoginPresenter;
import com.antonova.petzapp.interfaces.UserManageView;

public class LoginFragment extends Fragment implements UserManageView
{
    Button signInButton;
    Button registerButton;
    EditText usernameEditText;
    EditText passwordEditText;
    LoginPresenter lp=new LoginPresenter(this);
    ProgressBar progressBar;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
      View view=inflater.inflate(R.layout.fragment_login, container,false);
        signInButton = (Button) view.findViewById(R.id.signin);
        registerButton=(Button)view.findViewById(R.id.createaccount);
         usernameEditText=(EditText)view.findViewById(R.id.username);
         passwordEditText=(EditText)view.findViewById(R.id.password);
         progressBar=(ProgressBar)view.findViewById(R.id.login_progressBar);
        signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                lp.trySignUp();
            }
        });
        registerButton.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {
              changeFragment(2);
          }
      });
      return view;
  }

  @Override
  public String getUsername(){
        return usernameEditText.getText().toString();
  }

  @Override
  public String getPassword(){
        return passwordEditText.getText().toString();
  }

  @Override
public Activity getThis(){
    return getActivity();
}

  @Override
  public void changeFragment(int num){
      MainActivity ma=(MainActivity)getActivity();
      ma.changeFragment(num);
  }

  @Override
    public void showMessage(String error){
      Toast.makeText(getActivity(), error, Toast.LENGTH_LONG).show();
  }

  @Override
    public void showProgressBar(){
        progressBar.setVisibility(View.VISIBLE);
  }
  @Override
    public void hideProgressBar(){
        progressBar.setVisibility(View.INVISIBLE);
  }
}



