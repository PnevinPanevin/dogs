package com.sharipov.dogs.Data;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.sharipov.dogs.R;
import com.squareup.picasso.Picasso;


public class MyHolder extends RecyclerView.ViewHolder {
    //объявляем виджеты в виде полей класса

    private MyAdapter.OnItemClickListener onItemClickListener;
    private TextView textView;
    private ImageView imageView;

    public MyHolder(View itemView, MyAdapter.OnItemClickListener onItemClickListener) {
        super(itemView);

        //findViewById из itemView извлечь виджеты
        this.onItemClickListener = onItemClickListener;
        textView = itemView.findViewById(R.id.textView);
        imageView = itemView.findViewById(R.id.imageView);
    }

    public void bindTo(BreedObject breedObject) {
        //заполнение виджетов из объекта
        textView.setText(breedObject.getBreed());
        Picasso.get().load(breedObject.getImageUri()).into(imageView);
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
