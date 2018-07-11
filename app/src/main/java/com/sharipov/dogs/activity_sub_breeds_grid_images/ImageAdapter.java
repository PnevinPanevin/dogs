package com.sharipov.dogs.activity_sub_breeds_grid_images;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.sharipov.dogs.R;
import com.sharipov.dogs.activity_sub_breeds_list.SubBreedsAdapter;
import com.sharipov.dogs.data.SubBreedObject;

import java.util.List;

public class ImageAdapter extends RecyclerView.Adapter<ImageHolder> {

    private LayoutInflater layoutInflater;
    private List<String> imageList;
    private ImageAdapter.OnItemClickListener onItemClickListener;

    public interface OnItemClickListener{
        void onItemClick(String imageUri);
    }

    public void setOnItemClickListener(ImageAdapter.OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public ImageAdapter(Context context, List<String> imageList){
        layoutInflater = LayoutInflater.from(context);
        this.imageList = imageList;
    }

    @NonNull
    @Override
    public ImageHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.sub_breeds_grid_item, parent, false);
        return new ImageHolder(view, onItemClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull ImageHolder holder, int position) {
        holder.bindTo(imageList.get(position));
    }

    @Override
    public int getItemCount() {
        return imageList.size();
    }
}
