package com.sharipov.dogs.activity_sub_breeds_list;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.sharipov.dogs.data.SubBreedObject;
import com.sharipov.dogs.R;
import com.squareup.picasso.Picasso;

public class SubBreedsHolder extends RecyclerView.ViewHolder {

    private SubBreedsAdapter.OnItemClickListener onItemClickListener;
    private TextView textView;
    private ImageView imageView;

    public SubBreedsHolder(View itemView, SubBreedsAdapter.OnItemClickListener onItemClickListener) {
        super(itemView);

        this.onItemClickListener = onItemClickListener;
        textView = itemView.findViewById(R.id.grid_sample_text_view);
        imageView = itemView.findViewById(R.id.grid_sample_image_view);
    }

    public void bindTo(SubBreedObject subBreedObject) {
        textView.setText(subBreedObject.getTitle());
        Picasso.get().load(subBreedObject.getImageUri()).into(imageView);

        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onItemClickListener != null) {
                    onItemClickListener.onItemClick(subBreedObject);
                }
            }
        });
    }
}
