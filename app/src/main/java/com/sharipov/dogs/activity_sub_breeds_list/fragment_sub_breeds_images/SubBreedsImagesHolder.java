package com.sharipov.dogs.activity_sub_breeds_list.fragment_sub_breeds_images;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.sharipov.dogs.R;
import com.squareup.picasso.Picasso;

public class SubBreedsImagesHolder extends RecyclerView.ViewHolder {
    private SubBreedsImagesAdapter.OnItemClickListener onItemClickListener;
    private ImageView imageView;

    public SubBreedsImagesHolder(View itemView, SubBreedsImagesAdapter.OnItemClickListener onItemClickListener) {
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
