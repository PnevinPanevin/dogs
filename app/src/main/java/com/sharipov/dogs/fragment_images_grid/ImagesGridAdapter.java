package com.sharipov.dogs.fragment_images_grid;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

import com.sharipov.dogs.R;

import java.util.List;

public class ImagesGridAdapter extends RecyclerView.Adapter<ImagesGridHolder> {
    private LayoutInflater layoutInflater;
    private List<String> imagesList;
    private ImagesGridAdapter.OnItemClickListener onItemClickListener;

    public interface OnItemClickListener{
        void onItemClick(View parent, String imageUri);
    }

    public ImagesGridAdapter(Context context, List<String> imagesList) {
        this.layoutInflater = LayoutInflater.from(context);
        this.imagesList = imagesList;
    }

    public void setOnItemClickListener(ImagesGridAdapter.OnItemClickListener onItemClickListener){
        this.onItemClickListener = onItemClickListener;
    }

    @NonNull
    @Override
    public ImagesGridHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.grid_image_item, parent, false);
        return new ImagesGridHolder(view, onItemClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull ImagesGridHolder holder, int position) {
        holder.bindTo(imagesList.get(position));
    }

    @Override
    public int getItemCount() {
        return imagesList.size();
    }
}
