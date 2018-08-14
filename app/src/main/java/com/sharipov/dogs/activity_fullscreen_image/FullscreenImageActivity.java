package com.sharipov.dogs.activity_fullscreen_image;

import android.content.Intent;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.ContextCompat;
import android.os.Bundle;
import android.support.v4.util.Pair;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;

import com.sharipov.dogs.R;
import com.squareup.picasso.Picasso;

public class FullscreenImageActivity extends AppCompatActivity {

    public static final String IMAGE_URI = "IMAGE_URI";
    public static final String IMAGE_VIEW_TRANSITION = "IMAGE_VIEW_TRANSITION";

    private ImageView imageView;

    private String imageUri;

    public static void start(FragmentActivity activity, View transitionView, String imageUri) {

        ActivityOptionsCompat options = ActivityOptionsCompat
                .makeSceneTransitionAnimation(activity,
//                        transitionView, IMAGE_VIEW_TRANSITION
                        new Pair<>(transitionView, IMAGE_VIEW_TRANSITION)
                );
        Intent intent = new Intent(activity, FullscreenImageActivity.class);
        intent.putExtra(IMAGE_URI, imageUri);
        ActivityCompat.startActivity(activity, intent, options.toBundle());
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fullscreen_image);

        Toolbar toolbar = findViewById(R.id.toolbar_actionbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        setTitle("");

        Window window = getWindow();
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(ContextCompat.getColor(this , R.color.colorBlack));

        imageView = findViewById(R.id.image_view);
        imageUri = getIntent().getStringExtra(IMAGE_URI);
        imageView.setTransitionName(IMAGE_VIEW_TRANSITION);
        Picasso.get().load(imageUri).into(imageView);
    }
}
