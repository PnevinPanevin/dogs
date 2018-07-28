package com.sharipov.dogs.activity_breeds_list;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.sharipov.dogs.data.BreedObject;
import com.sharipov.dogs.R;

import java.util.List;

public class BreedsListAdapter extends RecyclerView.Adapter<BreedsListHolder>{

    private LayoutInflater layoutInflater;
    private List<BreedObject> breedObjects;
    private OnItemClickListener onItemClickListener;

    public interface OnItemClickListener{
        void onItemClick(BreedObject breedObject);
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public BreedsListAdapter(Context context, List<BreedObject> breedObjects){
        layoutInflater = LayoutInflater.from(context);
        this.breedObjects = breedObjects;
    }

    @NonNull
    public BreedsListHolder onCreateViewHolder (ViewGroup parent, int viewType){
        View view = layoutInflater.inflate(R.layout.dog_grid_item, parent, false);
        return new BreedsListHolder(view, onItemClickListener);
    }

    public void onBindViewHolder(@NonNull BreedsListHolder holder, int position){
        holder.bindTo(breedObjects.get(position));
    }

    public int getItemCount(){
        return breedObjects.size();
    }
}