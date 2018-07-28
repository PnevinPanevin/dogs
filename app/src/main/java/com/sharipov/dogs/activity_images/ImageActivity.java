package com.sharipov.dogs.activity_images;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
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
import com.sharipov.dogs.fragment_images_grid.ImagesGridFragment;

import java.util.ArrayList;
import java.util.List;

public class ImageActivity extends AppCompatActivity {

    public static final String TAG = "TAG";
    public static final String BREED = "BREED";
    public static final String SUBBREED = "SUBBREED";

    private String breed;
    private String subBreed;

    private Toolbar toolbar;
    private FragmentManager fragmentManager;
    private Fragment fragment;

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

        breed = getIntent().getStringExtra(BREED);
        subBreed = getIntent().getStringExtra(SUBBREED);

        toolbar = findViewById(R.id.toolbar_actionbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        setTitle(breed);
        toolbar.setSubtitle(subBreed);

        fragmentManager = getSupportFragmentManager();
        fragment = fragmentManager.findFragmentById(R.id.fragment_container);
        if (fragment == null) {
            fragment = ImagesGridFragment.newInstance(breed, subBreed);
            fragmentManager
                    .beginTransaction()
                    .add(R.id.fragment_container, fragment)
                    .commit();
        }
    }
}