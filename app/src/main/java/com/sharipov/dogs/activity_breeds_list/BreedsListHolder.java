package com.sharipov.dogs.activity_breeds_list;

import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.sharipov.dogs.data.BreedObject;
import com.sharipov.dogs.R;
import com.squareup.picasso.Picasso;

public class BreedsListHolder extends RecyclerView.ViewHolder {

    private BreedsListAdapter.OnItemClickListener onItemClickListener;
    private TextView textView;
    private ImageView imageView;

    public BreedsListHolder(View itemView, BreedsListAdapter.OnItemClickListener onItemClickListener) {
        super(itemView);

        this.onItemClickListener = onItemClickListener;
        textView = itemView.findViewById(R.id.grid_sample_text_view);
        imageView = itemView.findViewById(R.id.grid_sample_image_view);
    }

    public void bindTo(BreedObject breedObject) {
        textView.setText(breedObject.getTitle());
        Picasso.get().load(breedObject.getImageUri()).into(imageView);
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onItemClickListener != null) {
                    onItemClickListener.onItemClick(breedObject);
                }
            }
        });
    }

}
