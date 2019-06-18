package com.antonova.petzapp.connection;

import android.app.Activity;
import android.content.Intent;

import com.antonova.petzapp.services.AddAnimalService;
import com.antonova.petzapp.services.GetAnimalsToClient;
import com.antonova.petzapp.services.GetAllAnimalsToOwner;
import com.antonova.petzapp.services.GetAnimalById;
import com.antonova.petzapp.tools.Animal;

public class AnimalConnect {
    public void addAnimal(String token, Activity activity, Animal animal){
        Intent addAnimal = new Intent(activity, AddAnimalService.class);
        activity.startService(addAnimal.putExtra("Token", token).putExtra("Animal",animal));
    }

    public void getAllAnimals(String token, Activity activity){
        Intent getAllAnimals = new Intent(activity, GetAllAnimalsToOwner.class);
        activity.startService(getAllAnimals.putExtra("Token", token));
    }

    public void getAnimal(String token, int id, Activity activity){
        Intent getAllAnimals = new Intent(activity, GetAnimalById.class);
        activity.startService(getAllAnimals.putExtra("Token", token).putExtra("id",id));
    }

    public void getAnimalToClient(String token,Activity activity){
        Intent getAllAnimals = new Intent(activity, GetAnimalsToClient.class);
        activity.startService(getAllAnimals.putExtra("Token", token));
    }
}
