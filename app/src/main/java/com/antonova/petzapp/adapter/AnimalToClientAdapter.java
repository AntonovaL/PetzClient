package com.antonova.petzapp.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.antonova.petzapp.R;
import com.antonova.petzapp.tools.Animal;
import com.antonova.petzapp.tools.AnimalToClient;
import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import java.util.List;

public class AnimalToClientAdapter extends RecyclerView.Adapter<AnimalToClientAdapter.AnimaltoClientViewHolder> {
    List<AnimalToClient> animals;

    public AnimalToClientAdapter(List<AnimalToClient> animals) {
        this.animals = animals;
    }

    public class AnimaltoClientViewHolder extends RecyclerView.ViewHolder {
        ImageView animalPhoto;
        TextView animalName;
        TextView animalInfo;
        TextView animalType;
        TextView animalAge;
        TextView ownerName;
        TextView ownerPhone;
        ImageView animalSex;

        public AnimaltoClientViewHolder(View view) {
            super(view);
            animalPhoto=(ImageView)view.findViewById(R.id.animal_photo);
            animalSex=(ImageView)view.findViewById(R.id.animal_sex);
            animalName=(TextView)view.findViewById(R.id.animal_name);
            animalInfo=(TextView)view.findViewById(R.id.animal_info);
            animalType=(TextView)view.findViewById(R.id.animal_type);
            animalAge=(TextView)view.findViewById(R.id.animal_age);
            ownerName=(TextView)view.findViewById(R.id.owner_name);
            ownerPhone=(TextView)view.findViewById(R.id.owner_phone);
        }
    }

    @Override
    public AnimaltoClientViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_to_client, parent, false);
        return new AnimaltoClientViewHolder(view);
    }

    @Override
    public void onBindViewHolder(AnimaltoClientViewHolder holder, int position) {
        AnimalToClient animal=animals.get(position);
        holder.animalName.setText(animal.getName());
        String path = "http://192.168.0.4:8080/image/middle/" + animal.getImagePath();
        Picasso.get()
                .load(path)
                .placeholder(R.drawable.empty_placeholder)
                .error(R.drawable.placeholder_error)
                .into(holder.animalPhoto);
        if (animal.getSex().equals("male")) {
            Picasso.get()
                    .load(R.drawable.ic_male)
                    .into(holder.animalSex);
        } else {
            Picasso.get()
                    .load(R.drawable.ic_female)
                    .into(holder.animalSex);
        }
        holder.animalType.setText(getRussianType(animal.getType()));
        holder.ownerPhone.setText(animal.getOwnerPhoneNumber());
        holder.ownerName.setText(animal.getOwnerName());
        holder.animalInfo.setText(animal.getInfo());
        holder.animalAge.setText(animal.getAge()+" месяцев");
    }

    public String getRussianType(String type){
        String russianType=null;
        if(type.equals("cat"))
            russianType="Кот";
        if(type.equals("dog"))
            russianType="Собака";
        if(type.equals("raccoon"))
            russianType="Енот";
        if(type.equals("hedgehog"))
            russianType="Ёж";
        return russianType;
    }

    @Override
    public int getItemCount() {
        return animals.size();
    }
    public void setData(List<AnimalToClient> newData) {
        this.animals = newData;
        notifyDataSetChanged();
    }
}
