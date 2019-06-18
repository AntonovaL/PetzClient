package com.antonova.petzapp;

import android.arch.lifecycle.LifecycleOwner;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Observer;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.antonova.petzapp.db.AnimalsDBHelper;
import com.antonova.petzapp.db.AnimalsDb;
import com.antonova.petzapp.tools.Animal;

import java.util.ArrayList;
import java.util.List;


public class AnimalRepository {
    AnimalsDBHelper dbHelper;
    private List<Animal> allAnimals;
    private  String username;
    public AnimalRepository(Context context,String username) {
        dbHelper=new AnimalsDBHelper(context);
        this.username=username;
        allAnimals=getAll();
    }

    public long insert(Animal animal){
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(AnimalsDb.AnimalsEntry.COLUMN_ID, animal.getId());
        values.put(AnimalsDb.AnimalsEntry.COLUMN_NAME, animal.getName());
        values.put(AnimalsDb.AnimalsEntry.COLUMN_AGE,animal.getAge());
        values.put(AnimalsDb.AnimalsEntry.COLUMN_SEX,animal.getSex());
        values.put(AnimalsDb.AnimalsEntry.COLUMN_TYPE, animal.getType());
        values.put(AnimalsDb.AnimalsEntry.COLUMN_IMG,animal.getImagePath());
        values.put(AnimalsDb.AnimalsEntry.COLUMN_INFO,animal.getInfo());
        values.put(AnimalsDb.AnimalsEntry.COLUMN_OWNER, username);
        long newRowId = db.insert(AnimalsDb.AnimalsEntry.TABLE_NAME, null, values);
    return newRowId;
    }

    public void delete(int id){

    }
    public List<Animal> getAll() {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        Cursor cursor = db.query(AnimalsDb.AnimalsEntry.TABLE_NAME,
                null,
                AnimalsDb.AnimalsEntry.COLUMN_NAME + "= ?",
                new String[]{username},
                null, null,
                null);
        final List<Animal> animals = new ArrayList<>();
        if (cursor.moveToFirst()) {
            int idAnimalId = cursor.getColumnIndex(AnimalsDb.AnimalsEntry.COLUMN_ID);
            int idName = cursor.getColumnIndex(AnimalsDb.AnimalsEntry.COLUMN_NAME);
            int idAge = cursor.getColumnIndex(AnimalsDb.AnimalsEntry.COLUMN_AGE);
            int idSex = cursor.getColumnIndex(AnimalsDb.AnimalsEntry.COLUMN_SEX);
            int idType = cursor.getColumnIndex(AnimalsDb.AnimalsEntry.COLUMN_TYPE);
            int idImg = cursor.getColumnIndex(AnimalsDb.AnimalsEntry.COLUMN_IMG);
            int idInfo = cursor.getColumnIndex(AnimalsDb.AnimalsEntry.COLUMN_INFO);
            do {
                Animal animal = new Animal();
                animal.setId(cursor.getInt(idAnimalId));
                animal.setName(cursor.getString(idName));
                animal.setImagePath(cursor.getString(idImg));
                animal.setType(cursor.getString(idType));
                animal.setSex(cursor.getString(idSex));
                animal.setInfo(cursor.getString(idInfo));
                animal.setAge(cursor.getFloat(idAge));
                animals.add(animal);

            } while (cursor.moveToNext());
            cursor.close();
        }
        return animals;
    }

    public void close(){
        dbHelper.close();
    }
}
