package com.sharipov.dogs.activity_sub_breeds_list.fragment_sub_breeds_images;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.sharipov.dogs.R;

import java.util.List;

public class SubBreedsImagesAdapter extends RecyclerView.Adapter<SubBreedsImagesHolder> {
    private LayoutInflater layoutInflater;
    private List<String> imagesList;
    private SubBreedsImagesAdapter.OnItemClickListener onItemClickListener;

    public interface OnItemClickListener{
        void onItemClick(String imageUri);
    }

    public SubBreedsImagesAdapter(Context context, List<String> imagesList) {
        this.layoutInflater = LayoutInflater.from(context);
        this.imagesList = imagesList;
    }

    public void setOnItemClickListener(SubBreedsImagesAdapter.OnItemClickListener onItemClickListener){
        this.onItemClickListener = onItemClickListener;
    }

    @NonNull
    @Override
    public SubBreedsImagesHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.sub_breeds_image_item, parent, false);
        return new SubBreedsImagesHolder(view, onItemClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull SubBreedsImagesHolder holder, int position) {
        holder.bindTo(imagesList.get(position));
    }

    @Override
    public int getItemCount() {
        return imagesList.size();
    }
}
