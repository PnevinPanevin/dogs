package com.sharipov.dogs.activity_sub_breeds_grid_images;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.sharipov.dogs.R;
import com.sharipov.dogs.data.ImageDataProvider;

import java.util.List;

public class ImageActivity extends AppCompatActivity {

    public static final String TAG = "TAG";
    public static final String BREED = "BREED";
    public static final String SUBBREED = "SUBBREED";
    private String breed;
    private String subBreed;
    private List<String> imageList;

    private RecyclerView recyclerView;
    private ImageAdapter imageAdapter;

    public static void start(Context context, String breed, String subBreed) {
        Intent starter = new Intent(context, ImageActivity.class);
        starter.putExtra(BREED, breed);
        starter.putExtra(SUBBREED, subBreed);
        context.startActivity(starter);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_images);

        breed = getIntent().getStringExtra(BREED);
        subBreed = getIntent().getStringExtra(SUBBREED);

        recyclerView = findViewById(R.id.image_recycler_view);

        ImageDataProvider imageDataProvider = new ImageDataProvider(breed, subBreed);
        imageDataProvider.getImageList(new ImageDataProvider.OnGetList() {
            @Override
            public void onSuccess(List<String> list) {
                imageList = list;

                imageAdapter = new ImageAdapter(ImageActivity.this, imageList);
                imageAdapter.setOnItemClickListener(new ImageAdapter.OnItemClickListener() {
                    @Override
                    public void onItemClick(String imageUri) {

                    }
                });
                recyclerView.setAdapter(imageAdapter);
                recyclerView.setLayoutManager(new GridLayoutManager(ImageActivity.this, 1));
            }

            @Override
            public void onFail(Throwable t) {

            }
        });

    }
}
