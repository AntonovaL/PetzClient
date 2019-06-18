package com.antonova.petzapp.presenters;

import android.app.FragmentManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.net.Uri;
import android.support.v4.app.ListFragment;

import com.antonova.petzapp.AnimalRepository;
import com.antonova.petzapp.fragments.AddAnimal;
import com.antonova.petzapp.fragments.AnimalList;
import com.antonova.petzapp.fragments.OwnerFragment;
import com.antonova.petzapp.connection.AnimalConnect;
import com.antonova.petzapp.services.AddAnimalService;
import com.antonova.petzapp.tools.Animal;

import java.io.IOException;
import java.util.List;

public class AddAnimalPresenter {
    private final int PICK_IMAGE = 1;
    String token;
    AddAnimal view;
    Animal animal;
    Bitmap image;
    AnimalConnect repository;
    AnimalRepository storage;
    public AddAnimalPresenter(AddAnimal view, String t){
        this.view=view;
        this.token=t;
        repository=new AnimalConnect();
        animal=new Animal();
        storage=view.getStorage();
    }
    public void addAnimal(){
        view.showProgressBar();
        //animal=new Animal();
        try {
            animal.setImg(image);
            animal.setType(view.getType());
            animal.setAge(view.getAge());
            animal.setInfo(view.getInfo());
            animal.setName(view.getName());
            animal.setSex(view.getSex());
            repository.addAnimal(token,view.getActivity(),animal);
            IntentFilter intentFilter = new IntentFilter(AddAnimalService.ACTION_ADD);
            intentFilter.addCategory(Intent.CATEGORY_DEFAULT);
            AddAnimalBroadcastReceiver addAnimalBroadcastReceiver=new AddAnimalBroadcastReceiver();
            view.getActivity().registerReceiver(addAnimalBroadcastReceiver, intentFilter);
        }
        catch (NullPointerException e){
            view.hideProgressBar();
            view.showMessage("Необходимо добавить изображение");
        }
    }
    public void setImage(Bitmap image){
        this.image=image;
    }

    public  void removeFragment(){
       FragmentManager fm = view.getFragmentManager();
        OwnerFragment of=(OwnerFragment)fm.findFragmentByTag("OWNER_FRAGMENT");
       fm.beginTransaction()
                .detach(view)
                .commit();
       of.showList();
       of.showAddButton();
    }
    public void setImagePath(Uri uri){
        animal.setImagePath(uri.toString());
    }

    public class AddAnimalBroadcastReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            String answer = intent.getStringExtra(AddAnimalService.EXTRA_KEY_OUT);
            if(answer.equals("Connection lost")){
                view.hideProgressBar();
                view.showMessage("Соединение потеряно");
                return;
            }
            if(answer.equals("ERROR")){
                view.hideProgressBar();
                view.showMessage("Что-то пошло не так");
                return;
            }
                view.hideProgressBar();
                view.showMessage("Запись добавлена");
                int animalId=Integer.parseInt(answer);
                animal.setId(animalId);
                FragmentManager fm = view.getFragmentManager();
                AnimalList al=(AnimalList)fm.findFragmentByTag("ANIMAL_LIST");
                al.getPresenter().addNewAnimal(animalId);
                removeFragment();
        }
    }

}
