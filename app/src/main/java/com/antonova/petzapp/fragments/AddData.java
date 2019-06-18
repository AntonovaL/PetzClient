package com.antonova.petzapp.fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.antonova.petzapp.MainActivity;
import com.antonova.petzapp.R;
import com.antonova.petzapp.presenters.UserDataPresenter;
import com.antonova.petzapp.tools.UserData;


public class AddData extends Fragment {
    UserData ud;
    private LayoutInflater mInflater;
    private ViewGroup mContainer;
    private RadioGroup type;
    private EditText editName;
    private EditText editPhone;
    private EditText editCity;
    private Button okButton;
    private UserDataPresenter presenter;
    private RadioButton owner;
    private RadioButton client;
    private ProgressBar progressBar;
    private AddAnimal.OnFragmentInteractionListener mListener;
    public AddData() {
    }

    public static AddData newInstance(String param1, String param2) {
        AddData fragment = new AddData();
        Bundle args = new Bundle();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        presenter=new UserDataPresenter(this);
        View view=inflater.inflate(R.layout.adddata_fragment, container,false);
        SharedPreferences preferences=getActivity().getSharedPreferences("MY_APP",Context.MODE_PRIVATE);
        final String  token=preferences.getString("Token",null);
         type = (RadioGroup) view.findViewById(R.id.editType);
         editName=(EditText) view.findViewById(R.id.editName);
         editPhone=(EditText)view.findViewById(R.id.editPhone);
         editCity=(EditText)view.findViewById(R.id.editCity);
         okButton=(Button) view.findViewById(R.id.ok_button);
         owner=(RadioButton)view.findViewById(R.id.owner_button);
         owner.setChecked(true);
         client=(RadioButton)view.findViewById(R.id.client_button);
         progressBar=(ProgressBar)view.findViewById(R.id.data_progressBar);
        okButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                presenter.addUserData(token);
            }
        });
        return view;
    }

    public String getName(){
        return editName.getText().toString();
    }

    public String getPhone(){
        return  editPhone.getText().toString();
    }

    public String getCity(){
        return editCity.getText().toString();
    }

    public String getType(){
        int checkedRadioButtonId = type.getCheckedRadioButtonId();
        RadioButton myRadioButton = (RadioButton)getView().findViewById(checkedRadioButtonId);
        return myRadioButton.getText().toString();
    }
    public void changeFragment(int num){
        MainActivity ma=(MainActivity)getActivity();
        ma.changeFragment(num);
    }
    public void showMessage(String s){
        Toast.makeText(getActivity(), s, Toast.LENGTH_LONG).show();
    }

    public void showProgressBar(){
        progressBar.setVisibility(View.VISIBLE);
    }
    public void hideProgressBar(){
        progressBar.setVisibility(View.INVISIBLE);
    }
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof AddAnimal.OnFragmentInteractionListener) {
            mListener = (AddAnimal.OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }
    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }
}
