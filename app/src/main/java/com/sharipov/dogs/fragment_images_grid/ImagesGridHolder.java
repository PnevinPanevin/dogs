package com.sharipov.dogs.fragment_images_grid;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.sharipov.dogs.R;
import com.squareup.picasso.Picasso;

public class ImagesGridHolder extends RecyclerView.ViewHolder {
    private ImagesGridAdapter.OnItemClickListener onItemClickListener;
    private ImageView imageView;

    public ImagesGridHolder(View itemView, ImagesGridAdapter.OnItemClickListener onItemClickListener) {
        super(itemView);

        imageView = itemView.findViewById(R.id.image_view);
        this.onItemClickListener = onItemClickListener;
    }

    public void bindTo(String imageUri) {
        Picasso.get().load(imageUri).into(imageView);
        itemView.setOnClickListener((View v) -> {
            if (onItemClickListener != null) {
                onItemClickListener.onItemClick(imageUri);
            }
        });
    }
}
