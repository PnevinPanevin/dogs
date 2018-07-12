package com.sharipov.dogs.activity_sub_breeds_grid_images;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.widget.ProgressBar;

import com.sharipov.dogs.R;
import com.sharipov.dogs.data.ImageDataProvider;

import java.util.ArrayList;
import java.util.List;

public class ImageActivity extends AppCompatActivity {

    public static final String TAG = "TAG";
    public static final String BREED = "BREED";
    public static final String SUBBREED = "SUBBREED";
    private String breed;
    private String subBreed;
    private List<String> imageList = new ArrayList<>();

    private ProgressBar progressBar;
    private ViewPager viewPager;
    private ImagePagerAdapter pagerAdapter;

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

        viewPager = findViewById(R.id.view_pager);

        ImageDataProvider imageDataProvider = new ImageDataProvider(breed, subBreed);
        imageDataProvider.getImageList(new ImageDataProvider.OnGetList() {
            @Override
            public void onSuccess(List<String> list) {
                imageList = list;

                pagerAdapter = new ImagePagerAdapter(ImageActivity.this, imageList);
                viewPager.setAdapter(pagerAdapter);
            }

            @Override
            public void onFail(Throwable t) {

            }
        });
        showLoadingProgress();
    }

    private void showLoadingProgress() {
        progressBar = findViewById(R.id.progress_bar);
        Handler handler = new Handler();
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (imageList.size() == 0) {}
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        progressBar.setVisibility(ProgressBar.GONE);
                    }
                });
            }
        }).start();
    }
}
