package com.sharipov.dogs.activity_images;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.sharipov.dogs.R;
import com.sharipov.dogs.data.ImageDataProvider;

import java.util.ArrayList;
import java.util.List;

public class ImageActivity extends AppCompatActivity {

    public static final String TAG = "TAG";
    public static final String BREED = "BREED";
    public static final String SUBBREED = "SUBBREED";
    public static final String LIGHTS_TURNED_OFF = "LIGHTS_TURNED_OFF";
    private String breed;
    private String subBreed;
    private List<String> imageList = new ArrayList<>();
    private boolean lightsTurnedOff = false;

    private Toolbar toolbar;
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
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_images);

        if (savedInstanceState != null){
            lightsTurnedOff = savedInstanceState.getBoolean(LIGHTS_TURNED_OFF, false);
        }
        breed = getIntent().getStringExtra(BREED);
        subBreed = getIntent().getStringExtra(SUBBREED);

        toolbar = findViewById(R.id.toolbar_actionbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        if (breed.equals(subBreed)) {
            setTitle(breed);
        } else {
            setTitle(breed);
            toolbar.setSubtitle(subBreed);
        }
        viewPager = findViewById(R.id.view_pager);

        ImageDataProvider imageDataProvider = new ImageDataProvider(breed, subBreed);
        imageDataProvider.getImageList(new ImageDataProvider.OnGetList() {
            @Override
            public void onSuccess(List<String> list) {
                imageList = list;

                pagerAdapter = new ImagePagerAdapter(ImageActivity.this, imageList);
                pagerAdapter.setOnItemClickListener(new ImagePagerAdapter.OnItemClickListener() {
                    @Override
                    public void onItemClick() {
                        lightsTurnedOff = !lightsTurnedOff;
                        turnOffTheLights();
                    }
                });
                viewPager.setAdapter(pagerAdapter);

            }

            @Override
            public void onFail(Throwable t) {
                Toast.makeText(ImageActivity.this, t.toString(), Toast.LENGTH_SHORT).show();
            }
        });
        showLoadingProgress();

        if (lightsTurnedOff)turnOffTheLights();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putBoolean(LIGHTS_TURNED_OFF, lightsTurnedOff);
        super.onSaveInstanceState(outState);
    }

    private void showLoadingProgress() {
        progressBar = findViewById(R.id.progress_bar);
        Handler handler = new Handler();
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (imageList.size() == 0) {
                }
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        progressBar.setVisibility(ProgressBar.GONE);
                    }
                });
            }
        }).start();
    }

    private void turnOffTheLights(){
        Window window = this.getWindow();
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        if (lightsTurnedOff){
            int color = getColor(R.color.colorBlack);
            window.setStatusBarColor(color);
            toolbar.setBackgroundColor(color);
            viewPager.setBackgroundColor(color);
        } else {
            window.setStatusBarColor(getColor(R.color.colorPrimaryDark));
            toolbar.setBackgroundColor(getColor(R.color.colorPrimary));
            viewPager.setBackgroundColor(getColor(R.color.colorWhite));
        }
    }
}
