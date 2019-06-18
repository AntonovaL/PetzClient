package com.antonova.petzapp.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.antonova.petzapp.R;
import com.antonova.petzapp.tools.Animal;
import com.squareup.picasso.Picasso;

import java.util.List;

public class AnimalAdapter extends RecyclerView.Adapter<AnimalAdapter.AnimalViewHolder> {
  List<Animal> animals;
    public AnimalAdapter(List<Animal> animals){
        this.animals=animals;
    }

    public class AnimalViewHolder extends RecyclerView.ViewHolder{
        ImageView image;
        TextView animalName;
        ImageView sex;
        public AnimalViewHolder(View view){
            super(view);
            image=(ImageView)view.findViewById(R.id.animal_photo);
            animalName=(TextView)view.findViewById(R.id.animal_name);
            sex=(ImageView)view.findViewById(R.id.animal_sex);

        }

    }

    @Override
    public AnimalViewHolder onCreateViewHolder( ViewGroup parent, int viewType){
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item,parent,false);
        return new AnimalViewHolder(view);
    }

    @Override
    public void onBindViewHolder(AnimalViewHolder holder,int position){
        holder.animalName.setText(animals.get(position).getName());
       String path="http://192.168.0.4:8080/image/small/"+animals.get(position).getImagePath();
        Picasso.get()
               .load(path)
                .placeholder(R.drawable.empty_placeholder)
                .error(R.drawable.placeholder_error)
                .into(holder.image);
        //new DownloadImageTask(holder.image)
                //.execute("http://192.168.0.4:8080/image/small/3_E0O19u613w.jpeg");
            if(animals.get(position).getSex().equals("male")){
                Picasso.get()
                        .load(R.drawable.ic_male)
                        .into(holder.sex);
            }
            else {
                Picasso.get()
                        .load(R.drawable.ic_female)
                        .into(holder.sex);
            }
    }

    @Override
    public int getItemCount(){
        return animals.size();
    }

    public void setData(List<Animal> newData) {
        this.animals = newData;
        notifyDataSetChanged();
    }
}
