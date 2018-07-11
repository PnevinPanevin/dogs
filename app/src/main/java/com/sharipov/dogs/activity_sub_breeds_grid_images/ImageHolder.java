package com.sharipov.dogs.activity_sub_breeds_grid_images;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.sharipov.dogs.R;
import com.sharipov.dogs.data.SubBreedObject;
import com.squareup.picasso.Picasso;

public class ImageHolder extends RecyclerView.ViewHolder {

    private ImageAdapter.OnItemClickListener onItemClickListener;
    private ImageView imageView;

    public ImageHolder(View itemView, ImageAdapter.OnItemClickListener onItemClickListener) {
        super(itemView);

        imageView = itemView.findViewById(R.id.grid_sample_image_view);
    }

    public void bindTo(String imageUri) {
        Picasso.get().load(imageUri).into(imageView);

        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onItemClickListener != null) {
                    onItemClickListener.onItemClick(imageUri);
                }
            }
        });
    }
}
