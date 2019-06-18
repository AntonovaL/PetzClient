package com.antonova.petzapp.fragments;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.app.Fragment;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.antonova.petzapp.AnimalRepository;
import com.antonova.petzapp.MainActivity;
import com.antonova.petzapp.R;
import com.antonova.petzapp.presenters.AddAnimalPresenter;
import com.antonova.petzapp.presenters.AnimalListPresenter;

public class OwnerFragment extends Fragment {
    private FloatingActionButton add;
    private FragmentManager fm;
    private Toolbar toolbar;
    private AnimalList animalList;
    AddAnimal newAnimalFragment;
    AddAnimalPresenter presenter;
    public OwnerFragment() {
    }

    public static OwnerFragment newInstance(String param1, String param2) {
        OwnerFragment fragment = new OwnerFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        fm = getFragmentManager();
         final View view=inflater.inflate(R.layout.fragment_owner, container, false);
        SharedPreferences preferences=getActivity().getSharedPreferences("MY_APP", Context.MODE_PRIVATE);
        final String token=preferences.getString("Token",null);
        addList();
        final String userName=preferences.getString("Username",null);
        Toolbar toolbar = (Toolbar) view.findViewById(R.id.toolbar_owner);
        toolbar.setNavigationIcon(getResources().getDrawable(R.drawable.ic_person_info));
        toolbar.setTitle(userName);
         add=(FloatingActionButton)view.findViewById(R.id.add_animal);
         add.setOnClickListener(new View.OnClickListener(){
            @Override
             public void onClick(View v) {
                add.setVisibility(View.INVISIBLE);
                addNewAnimalFragment();

             }
         });
         return view;
    }

    public void addNewAnimalFragment(){
        newAnimalFragment=new AddAnimal();
        fm.beginTransaction()
                .hide(animalList)
                .add(R.id.container, newAnimalFragment)
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                .commit();
    }

    public void addList(){
        animalList=new AnimalList();
        fm.beginTransaction()
                .add(R.id.container,animalList,"ANIMAL_LIST")
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                .commit();
    }

    public void showList(){
        fm.beginTransaction()
                .show(animalList)
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                .commit();
    }
    public void showAddButton(){
        add.setVisibility(View.VISIBLE);
    }

    public AnimalRepository getStorage(){
        MainActivity ma=(MainActivity)getActivity();
        return ma.getStorage();
    }

}
