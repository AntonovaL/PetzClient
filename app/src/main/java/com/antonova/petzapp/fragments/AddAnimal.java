package com.antonova.petzapp.fragments;

import android.app.FragmentManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import com.antonova.petzapp.AnimalRepository;
import com.antonova.petzapp.MainActivity;
import com.antonova.petzapp.R;
import com.antonova.petzapp.presenters.AddAnimalPresenter;
import com.squareup.picasso.Picasso;

import java.io.IOException;

public class AddAnimal extends Fragment {
    private final int PICK_IMAGE = 1;
    private Button addImage;
    private EditText editName;
    private Spinner editType;
    private Spinner editAgeOne;
    private Spinner editAgeTwo;
    private EditText editInfo;
    public Button ok;
    public Button cancel;
    private ImageView imageView;
    private ProgressBar progressBar;
    private RadioButton male;
    private RadioButton female;
    private RadioGroup sex;
    int ageOne; String ageTwo; String type;
    private OnFragmentInteractionListener mListener;
    private AddAnimalPresenter presenter;
    public AddAnimal() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        Bundle bundle = this.getArguments();
        SharedPreferences preferences=getActivity().getSharedPreferences("MY_APP", Context.MODE_PRIVATE);
        final String token=preferences.getString("Token",null);
        presenter=new AddAnimalPresenter(this,token);
        final View view= inflater.inflate(R.layout.fragment_add_animal, container, false);
        addImage=(Button)view.findViewById(R.id.chooseImage);
        editName=(EditText)view.findViewById(R.id.editName);
        editInfo=(EditText)view.findViewById(R.id.edit_info);
        editType=(Spinner)view.findViewById(R.id.type);
        editAgeOne=(Spinner)view.findViewById(R.id.age_one);
        editAgeTwo=(Spinner)view.findViewById(R.id.age_two);
        ok=(Button)view.findViewById(R.id.ok);
        cancel=(Button)view.findViewById(R.id.cancel);
        imageView=(ImageView)view.findViewById(R.id.imageView);
        progressBar=(ProgressBar)view.findViewById(R.id.addanimal_progressBar);
        sex=(RadioGroup)view.findViewById(R.id.choose_sex);
        female=(RadioButton)view.findViewById(R.id.female);
        male=(RadioButton)view.findViewById(R.id.male);
        male.setChecked(true);
        addImage.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_PICK);
                startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE);
            }
        });
        FragmentManager fm=getFragmentManager();
        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.addAnimal();
            }
        });
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.removeFragment();
            }
        });
        return view;
    }

    public String getName(){
        return editName.getText().toString();
    }

    public String getType(){
        String type= editType.getSelectedItem().toString();
        String newType=null;
        if(type.equals("Кот"))
            newType="cat";
        if(type.equals("Собака"))
            newType="dog";
        if(type.equals("Енот"))
            newType="raccoon";
        if(type.equals("Ёж"))
            newType="hedgehog";
        return newType;
    }

    public float getAge(){
        int one=Integer.parseInt(editAgeOne.getSelectedItem().toString());
        if (editAgeTwo.getSelectedItem().toString().equals("год")){
            return one*12;
        }
        else{
            return one;
        }
    }
    public String getSex(){
        int checkedRadioButtonId = sex.getCheckedRadioButtonId();
        RadioButton myRadioButton = (RadioButton)getView().findViewById(checkedRadioButtonId);
        if (myRadioButton.getText().toString().equals("Мальчик")){
            return "male";
        }
        else{
            return "female";
        }
    }
    public String getInfo(){
        return editInfo.getText().toString();
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
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
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

    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(Uri uri);
    }


    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        Bitmap img = null;
        if (requestCode == 1 && data != null) {
            try {
            Uri selectedImage = data.getData();
                Picasso.get()
                        .load(selectedImage)
                        .into(imageView);
                imageView.setVisibility(View.VISIBLE);
                img= MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), selectedImage);
                presenter.setImage(img);
                 presenter.setImagePath(selectedImage);
                //showImage(img);
        } catch (IOException e) {
            e.printStackTrace();
        }
        }
    }

    public AnimalRepository getStorage(){
        MainActivity ma=(MainActivity)getActivity();
        return ma.getStorage();
    }
}
