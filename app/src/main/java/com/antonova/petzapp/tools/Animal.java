package com.antonova.petzapp.tools;

import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.util.Base64;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.nio.ByteBuffer;

public class Animal implements Serializable {
    private int id;
    private String name;
    private String type;
    private float age;
    private String info;
    private String img;
    private String sex;
    private String imagePath;


    public Animal(){

    }
    public Animal(int id, String name,   float age, String sex, String type,String imagePath, String info){
        this.id=id;
        this.name=name;
        this.type=type;
        this.age=age;
        this.info=info;
        this.imagePath=imagePath;
        this.sex=sex;
    }

    public Animal(Animal animal){
        this.id=animal.id;
        this.name=animal.name;
        this.imagePath=animal.imagePath;
        this.age=animal.age;
        this.info=animal.info;
        this.sex=animal.sex;
        this.type=animal.type;
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

    public String getImg() {
        return img;
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

    public void setImg(Bitmap img) {
        ByteArrayOutputStream baos=new ByteArrayOutputStream();
        img.compress(Bitmap.CompressFormat.JPEG,50,baos);
        byte[] b=baos.toByteArray();
        this.img=Base64.encodeToString(b,Base64.DEFAULT);
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

    public JSONObject toJson() {
        JSONObject jsonObject=new JSONObject();
        try {
            jsonObject.put("name",name);
            jsonObject.put("age",age);
            jsonObject.put("type",type);
            jsonObject.put("img",img);
            jsonObject.put("info",info);
            jsonObject.put("sex",sex);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonObject;
    }
}
