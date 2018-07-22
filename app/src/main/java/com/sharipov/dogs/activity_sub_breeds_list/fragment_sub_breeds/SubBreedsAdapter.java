package com.sharipov.dogs.activity_sub_breeds_list.fragment_sub_breeds;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.sharipov.dogs.data.SubBreedObject;
import com.sharipov.dogs.R;

import java.util.List;

public class SubBreedsAdapter extends RecyclerView.Adapter<SubBreedsHolder> {

    private LayoutInflater layoutInflater;
    private List<SubBreedObject> subBreedObjects;
    private SubBreedsAdapter.OnItemClickListener onItemClickListener;

    public interface OnItemClickListener{
        void onItemClick(SubBreedObject subBreedObject);
    }

    public void setOnItemClickListener(SubBreedsAdapter.OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public SubBreedsAdapter(Context context, List<SubBreedObject> subBreedObjects){
        layoutInflater = LayoutInflater.from(context);
        this.subBreedObjects = subBreedObjects;
    }

    @NonNull
    @Override
    public SubBreedsHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.sub_breeds_grid_item, parent, false);
        return new SubBreedsHolder(view, onItemClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull SubBreedsHolder holder, int position) {
        holder.bindTo(subBreedObjects.get(position));
    }

    @Override
    public int getItemCount() {
        return subBreedObjects.size();
    }

    public void setFilter(List<SubBreedObject> list){
        subBreedObjects.clear();
        subBreedObjects.addAll(list);
        notifyDataSetChanged();
    }
}
