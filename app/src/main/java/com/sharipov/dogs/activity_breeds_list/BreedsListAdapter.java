package com.sharipov.dogs.activity_breeds_list;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.sharipov.dogs.model.data.BreedObject;
import com.sharipov.dogs.R;

import java.util.ArrayList;
import java.util.List;

public class BreedsListAdapter extends RecyclerView.Adapter<BreedsListHolder>{

    private LayoutInflater layoutInflater;
    private List<BreedObject> breedObjects;
    private List<BreedObject> breedObjectsFiltered;
    private OnItemClickListener onItemClickListener;

    public interface OnItemClickListener{
        void onItemClick(View parent, BreedObject breedObject);
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public BreedsListAdapter(Context context, List<BreedObject> breedObjects){
        layoutInflater = LayoutInflater.from(context);
        this.breedObjects = breedObjects;
        this.breedObjectsFiltered = breedObjects;
    }

    @NonNull
    public BreedsListHolder onCreateViewHolder (@NonNull ViewGroup parent, int viewType){
        View view = layoutInflater.inflate(R.layout.dog_grid_item, parent, false);
        return new BreedsListHolder(view, onItemClickListener);
    }

    public void onBindViewHolder(@NonNull BreedsListHolder holder, int position){
        holder.bindTo(breedObjectsFiltered.get(position));
    }

    public int getItemCount(){
        return breedObjectsFiltered.size();
    }

    public void filter(String query){
        if (query.isEmpty()){
            breedObjectsFiltered = breedObjects;
        }
        List<BreedObject> newList = new ArrayList<>();
        for (BreedObject b : breedObjects) {
            if (b.getBreed().contains(query)) {
                newList.add(b);
            }
        }
        breedObjectsFiltered = newList;
        notifyDataSetChanged();
    }
}