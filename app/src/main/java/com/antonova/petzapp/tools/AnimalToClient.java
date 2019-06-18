package com.antonova.petzapp.tools;

import android.graphics.Bitmap;
import android.util.Base64;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;

public class AnimalToClient {
    private int id;
    private String name;
    private String type;
    private float age;
    private String info;
    private String sex;
    private String imagePath;
    private String ownerName;
    private String ownerPhoneNumber;


    public AnimalToClient(){

    }
    public AnimalToClient(int id, String name,   float age, String sex, String type,String imagePath, String info,String ownerName, String ownerPhoneNumber){
        this.id=id;
        this.name=name;
        this.type=type;
        this.age=age;
        this.info=info;
        this.imagePath=imagePath;
        this.sex=sex;
        this.ownerName=ownerName;
        this.ownerPhoneNumber=ownerPhoneNumber;
    }

    public AnimalToClient(AnimalToClient animal){
        this.id=animal.id;
        this.name=animal.name;
        this.imagePath=animal.imagePath;
        this.age=animal.age;
        this.info=animal.info;
        this.sex=animal.sex;
        this.type=animal.type;
        this.ownerName=animal.ownerName;
        this.ownerPhoneNumber=animal.ownerPhoneNumber;
    }

    public String getImagePath(){
        return imagePath;
    }

    public void setImagePath(String imagePath){
        this.imagePath=imagePath;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public String getName() {
        return name;
    }


    public float getAge() {
        return age;
    }

    public String getInfo() {
        return info;
    }

    public void setAge(float age) {
        this.age = age;
    }

    public void setSex(String sex){
        this.sex=sex;
    }

    public String getSex(){
        return sex;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public int getId(){
        return id;
    }

    public void setId(int id){
        this.id=id;
    }

    public void setOwnerName(String ownerName){
        this.ownerName=ownerName;
    }

    public void setOwnerPhoneNumber(String ownerPhoneNumber){
        this.ownerPhoneNumber=ownerPhoneNumber;
    }

    public String getOwnerName(){
        return ownerName;
    }

    public String getOwnerPhoneNumber(){
        return ownerPhoneNumber;
    }
}
