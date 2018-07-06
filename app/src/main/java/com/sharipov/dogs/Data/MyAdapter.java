package com.sharipov.dogs.Data;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.sharipov.dogs.R;

import java.util.List;

public class MyAdapter extends RecyclerView.Adapter<MyHolder>{

    private LayoutInflater layoutInflater;
    private List<BreedObject> breedObjects; //любой вид коллекции
    private OnItemClickListener onItemClickListener;

    public interface OnItemClickListener{
        void onItemClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public MyAdapter(Context context, List<BreedObject> breedObjects){
        layoutInflater = LayoutInflater.from(context);
        this.breedObjects = breedObjects;
    }

    @NonNull
    public MyHolder onCreateViewHolder (ViewGroup parent, int viewType){
        View view = layoutInflater.inflate(R.layout.grid_sample, parent, false);
        return new MyHolder(view, onItemClickListener);
    }

    public void onBindViewHolder(@NonNull MyHolder holder, int position){
        //int position - индекс элемента
        //получить данные по позиции
        //вызвать для холдера метод bindTo и передать ему данные, которые были получены
        holder.bindTo(breedObjects.get(position));
    }

    public int getItemCount(){
        return breedObjects.size(); //возвращает размер списка данных
    }
}