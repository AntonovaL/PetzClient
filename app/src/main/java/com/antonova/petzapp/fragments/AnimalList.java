package com.antonova.petzapp.fragments;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.antonova.petzapp.adapter.AnimalAdapter;
import com.antonova.petzapp.R;
import com.antonova.petzapp.presenters.AnimalListPresenter;


public class AnimalList extends Fragment {
    RecyclerView recyclerView;
    TextView empty;
    ProgressBar progressBar;
    AnimalListPresenter presenter;

    public AnimalList() {

    }
    public static AnimalList newInstance() {
        AnimalList fragment = new AnimalList();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_animal_list, container, false);
        recyclerView=(RecyclerView)view.findViewById(R.id.recycle_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        progressBar=(ProgressBar)view.findViewById(R.id.getAnimals_progressBar);
        empty=(TextView)view.findViewById(R.id.text_empty);
        empty.setVisibility(View.INVISIBLE);
        presenter=new AnimalListPresenter(this);
        SharedPreferences preferences=getActivity().getSharedPreferences("MY_APP", Context.MODE_PRIVATE);
        final String token=preferences.getString("Token",null);
        presenter.getAnimalList();
        return view;
    }
    public void showProgressBar(){
        progressBar.setVisibility(View.VISIBLE);
    }

    public void hideProgressBar(){
        progressBar.setVisibility(View.INVISIBLE);
    }

    public Activity getThis(){
        return getActivity();
    }

    public void setAdapter(AnimalAdapter adapter){
        recyclerView.setAdapter(adapter);
    }

    public void showEmpty(){
        empty.setVisibility(View.VISIBLE);
    }

    public AnimalListPresenter getPresenter(){return presenter;}



}
