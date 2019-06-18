package com.antonova.petzapp.fragments;

import android.app.FragmentManager;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.support.v7.widget.Toolbar;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.antonova.petzapp.R;
import com.antonova.petzapp.adapter.AnimalAdapter;
import com.antonova.petzapp.adapter.AnimalToClientAdapter;
import com.antonova.petzapp.presenters.ClientPresenter;

public class ClientFragment extends Fragment {
    private ClientPresenter presenter;
    private FragmentManager fm;
    private Toolbar toolbar;
    private ProgressBar progressBar;
    private TextView empty;
    private RecyclerView recyclerView;
    public ClientFragment() {
    }

    public static ClientFragment newInstance(String param1, String param2) {
        ClientFragment fragment = new ClientFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        presenter=new ClientPresenter(this);

        fm = getFragmentManager();
        final View view=inflater.inflate(R.layout.fragment_client, container, false);
        SharedPreferences preferences=getActivity().getSharedPreferences("MY_APP", Context.MODE_PRIVATE);
        recyclerView=(RecyclerView)view.findViewById(R.id.recycle_view_toclient);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        final String token=preferences.getString("Token",null);
        final String userName=preferences.getString("Username",null);
        Toolbar toolbar = (Toolbar) view.findViewById(R.id.toolbar_client);
        toolbar.setNavigationIcon(getResources().getDrawable(R.drawable.ic_person_info));
        toolbar.setTitle(userName);
        empty=(TextView)view.findViewById(R.id.text_empty);
        progressBar=(ProgressBar)view.findViewById(R.id.getAnimalstoClient_progressBar);
        presenter.getAnimalList();
        return view;
    }
    public void showEmpty(){empty.setVisibility(View.VISIBLE);}

    public void hideEmpty(){empty.setVisibility(View.INVISIBLE);}
    public void showProgressBar(){
        progressBar.setVisibility(View.VISIBLE);
    }

    public void hideProgressBar(){
        progressBar.setVisibility(View.INVISIBLE);
    }
    public void setAdapter(AnimalToClientAdapter adapter){
        recyclerView.setAdapter(adapter);
    }

}
