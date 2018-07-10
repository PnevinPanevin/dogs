package com.sharipov.dogs.activity_sub_breeds;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.sharipov.dogs.Data.SubBreedObject;
import com.sharipov.dogs.R;
import com.squareup.picasso.Picasso;

public class SubBreedsHolder extends RecyclerView.ViewHolder {

    private SubBreedsAdapter.OnItemClickListener onItemClickListener;
    private TextView textView;
    private ImageView imageView;

    public SubBreedsHolder(View itemView, SubBreedsAdapter.OnItemClickListener onItemClickListener) {
        super(itemView);

        textView = itemView.findViewById(R.id.grid_sample_text_view);
        imageView = itemView.findViewById(R.id.grid_sample_image_view);
    }

    public void bindTo(SubBreedObject subBreedObject) {
        //заполнение виджетов из объекта
        textView.setText(subBreedObject.getSubBreed());
        Picasso.get().load(subBreedObject.getImageUri()).into(imageView);

        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onItemClickListener != null) {
                    onItemClickListener.onItemClick(getAdapterPosition());
                }
            }
        });
    }
}
