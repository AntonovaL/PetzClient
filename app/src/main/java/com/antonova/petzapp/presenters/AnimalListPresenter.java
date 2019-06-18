package com.antonova.petzapp.presenters;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;

import com.antonova.petzapp.adapter.AnimalAdapter;
import com.antonova.petzapp.connection.AnimalConnect;
import com.antonova.petzapp.fragments.AnimalList;
import com.antonova.petzapp.services.GetAllAnimalsToOwner;
import com.antonova.petzapp.services.GetAnimalById;
import com.antonova.petzapp.tools.Animal;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class AnimalListPresenter {
    AnimalList view;
    AnimalConnect repository;
    String token;
    List<Animal> animals;
    AnimalAdapter adapter;

    public AnimalListPresenter(AnimalList view){
        this.view=view;
        animals=new ArrayList<Animal>();
        repository=new AnimalConnect();
        SharedPreferences preferences=view.getActivity().getSharedPreferences("MY_APP", Context.MODE_PRIVATE);
        token=preferences.getString("Token",null);
    }


    public void getAnimalList(){
        view.showProgressBar();
        IntentFilter intentFilter = new IntentFilter(GetAllAnimalsToOwner.ACTION_GETANIMALLIST);
        intentFilter.addCategory(Intent.CATEGORY_DEFAULT);
        AnimalsBrodcastReciver animalsBroadcastReceiver=new AnimalsBrodcastReciver();
        repository.getAllAnimals(token,view.getThis());
        view.getThis().registerReceiver(animalsBroadcastReceiver, intentFilter);
    }
    public List<Animal> getArrayFromString(String jsonString){
        List<Animal> list=new ArrayList<Animal>();
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
            Animal a=new Animal();
            a.setId(id); a.setAge(age); a.setInfo(info); a.setSex(sex);a.setType(type); a.setImagePath(img); a.setName(name);
            list.add(a);
        }
        return list;
    }

    public Animal getAnimalFromString(String jsonString){
        Gson gson = new Gson();
        Type typeOfList = new TypeToken<Map<String, String>>() {}.getType();
        Map<String, String> gsonList = gson.fromJson(jsonString, typeOfList);
            int id=Integer.parseInt(gsonList.get("id"));
            String name=gsonList.get("name");
            float age=Float.parseFloat(gsonList.get("age"));
            String sex=gsonList.get("sex");
            String type=gsonList.get("type");
            String img=gsonList.get("img");
            String info=gsonList.get("info");
            Animal a=new Animal();
            a.setId(id); a.setAge(age); a.setInfo(info); a.setSex(sex);a.setType(type); a.setImagePath(img); a.setName(name);
        return a;
    }

    public void addNewAnimal(int animalId){
        view.showProgressBar();
        IntentFilter intentFilter = new IntentFilter(GetAnimalById.ACTION_GETANIMAL);
        intentFilter.addCategory(Intent.CATEGORY_DEFAULT);
        AnimalBrodcastReciver animalBroadcastReceiver=new AnimalBrodcastReciver();
        repository.getAnimal(token,animalId,view.getThis());
        view.getThis().registerReceiver(animalBroadcastReceiver, intentFilter);
    }

    public class AnimalsBrodcastReciver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            String answer = intent.getStringExtra(GetAllAnimalsToOwner.EXTRA_KEY_OUT);
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
                    adapter=new AnimalAdapter(animals);
                    view.setAdapter(adapter);
                }
            }
        }

    }

    public class AnimalBrodcastReciver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            String answer = intent.getStringExtra(GetAnimalById.EXTRA_KEY_OUT);
            if (answer.equals("NOT EXISTS")) {
                view.hideProgressBar();
            }
            else{
                view.hideProgressBar();
                Animal animal=getAnimalFromString(answer);
                animals.add(animal);
                adapter.setData(animals);
            }
        }

    }
}
