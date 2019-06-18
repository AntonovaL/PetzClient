package com.antonova.petzapp.presenters;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;

import com.antonova.petzapp.adapter.AnimalToClientAdapter;
import com.antonova.petzapp.connection.AnimalConnect;
import com.antonova.petzapp.fragments.ClientFragment;
import com.antonova.petzapp.services.GetAllAnimalsToOwner;
import com.antonova.petzapp.services.GetAnimalsToClient;
import com.antonova.petzapp.tools.AnimalToClient;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ClientPresenter {
    ClientFragment view;
    AnimalConnect repository;
    String token;
    List<AnimalToClient> animals;
    AnimalToClientAdapter adapter;


    public ClientPresenter(ClientFragment view){
        this.view=view;
        animals=new ArrayList<AnimalToClient>();
        repository=new AnimalConnect();
        SharedPreferences preferences=view.getActivity().getSharedPreferences("MY_APP", Context.MODE_PRIVATE);
        token=preferences.getString("Token",null);
    }


    public void getAnimalList(){
        view.showProgressBar();
        IntentFilter intentFilter = new IntentFilter(GetAnimalsToClient.ACTION_ANIMALTOCLIENT);
        intentFilter.addCategory(Intent.CATEGORY_DEFAULT);
        AnimalsBrodcastReciver animalsBroadcastReceiver=new AnimalsBrodcastReciver();
        repository.getAnimalToClient(token,view.getActivity());
        view.getActivity().registerReceiver(animalsBroadcastReceiver, intentFilter);
    }

    public List<AnimalToClient> getArrayFromString(String jsonString){
        List<AnimalToClient> list=new ArrayList<AnimalToClient>();
        Gson gson = new Gson();
        Type typeOfList = new TypeToken<List<Map<String, String>>>() {}.getType();
        List<Map<String, String>> gsonList = gson.fromJson(jsonString, typeOfList);
        for(int i = 0; i < gsonList.size(); i++) {
            int id=Integer.parseInt(gsonList.get(i).get("id"));
            String name=gsonList.get(i).get("name");
            float age=Float.parseFloat(gsonList.get(i).get("age"));
            String sex=gsonList.get(i).get("sex");
            String type=gsonList.get(i).get("type");
            String img=gsonList.get(i).get("img");
            String info=gsonList.get(i).get("info");
            String ownerName=gsonList.get(i).get("ownerName");
            String ownerPhone=gsonList.get(i).get("phoneNumber");
            AnimalToClient a=new AnimalToClient();
            a.setId(id); a.setAge(age); a.setInfo(info); a.setSex(sex);a.setType(type); a.setImagePath(img); a.setName(name);
            a.setOwnerName(ownerName); a.setOwnerPhoneNumber(ownerPhone);
            list.add(a);
        }
        return list;
    }

    public class AnimalsBrodcastReciver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            String answer = intent.getStringExtra(GetAnimalsToClient.EXTRA_KEY_OUT);
            if (answer.equals("NOT EXISTS")) {
                view.hideProgressBar();
            }
            else{
                view.hideProgressBar();
                animals=getArrayFromString(answer);
                if (animals.size()==0){
                    view.showEmpty();
                }
                else{
                    adapter=new AnimalToClientAdapter(animals);
                    view.setAdapter(adapter);
                }
            }
        }

    }


}
