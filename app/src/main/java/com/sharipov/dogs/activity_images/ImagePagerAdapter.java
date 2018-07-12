package com.sharipov.dogs.activity_images;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.sharipov.dogs.R;
import com.sharipov.dogs.activity_breeds_list.BreedsListAdapter;
import com.sharipov.dogs.data.BreedObject;
import com.squareup.picasso.Picasso;

import java.util.List;

public class ImagePagerAdapter extends PagerAdapter {

    private LayoutInflater layoutInflater;
    private List<String> imageList;
    private ImagePagerAdapter.OnItemClickListener onItemClickListener;

    public interface OnItemClickListener{
        void onItemClick();
    }

    public void setOnItemClickListener(ImagePagerAdapter.OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public ImagePagerAdapter(Context context, List<String> imageList){
        layoutInflater = LayoutInflater.from(context);
        this.imageList = imageList;
    }


    @Override
    public int getCount() {
        return imageList.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view==object;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        View view = layoutInflater.inflate(R.layout.image_item, container,false);
        ImageView imageView = view.findViewById(R.id.image_view);
        Picasso.get().load(imageList.get(position)).into(imageView);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onItemClickListener != null) {
                    onItemClickListener.onItemClick();
                }
            }
        });
        container.addView(view);
        return view;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((FrameLayout)object);
    }
}
