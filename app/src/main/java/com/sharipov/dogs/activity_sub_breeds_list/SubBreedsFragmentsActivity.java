package com.sharipov.dogs.activity_sub_breeds_list;

import android.content.Context;
import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentManager;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.WindowManager;
import android.widget.ImageView;

import com.sharipov.dogs.R;
import com.sharipov.dogs.activity_sub_breeds_list.fragment_sub_breeds.SubBreedsFragment;
import com.sharipov.dogs.fragment_images_grid.ImagesGridFragment;
import com.squareup.picasso.Picasso;

public class SubBreedsFragmentsActivity extends AppCompatActivity {
    private static final String TAG = "qqq";
    public static final String BREED = "BREED";
    public static final String TITLE = "TITLE";
    public static final String IMAGE_URI = "IMAGE_URI";

    private String breed;
    private String title;
    private String imageUri;

    private TabLayout tabLayout;
    private ViewPager viewPager;
    private ImageView imageView;
    private FragmentManager fragmentManager;

    public static void start(Context context, String breed, String title, String imageUri) {
        Intent starter = new Intent(context, SubBreedsFragmentsActivity.class);
        starter.putExtra(BREED, breed);
        starter.putExtra(TITLE, title);
        starter.putExtra(IMAGE_URI, imageUri);
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
        setContentView(R.layout.activity_sub_breeds_fragments);

        getExtrasFromIntent();

        Toolbar toolbar = findViewById(R.id.toolbar_actionbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(title);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        tabLayout = findViewById(R.id.tab_layout);
        viewPager = findViewById(R.id.view_pager);
        imageView = findViewById(R.id.image_view);
        Picasso.get().load(imageUri).into(imageView);

        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        fragmentManager = getSupportFragmentManager();

        SubBreedsActivityPagerAdapter pagerAdapter = new SubBreedsActivityPagerAdapter(fragmentManager);
        pagerAdapter.addFragment(SubBreedsFragment.newInstance(breed), "Sub breeds");
        pagerAdapter.addFragment(ImagesGridFragment.newInstance(breed, ""), "Images");
        viewPager.setAdapter(pagerAdapter);

        tabLayout.setTabMode(TabLayout.MODE_FIXED);
        tabLayout.setupWithViewPager(viewPager);

    }

    private void getExtrasFromIntent() {
        Intent intent = getIntent();
        breed = intent.getStringExtra(BREED);
        title = intent.getStringExtra(TITLE);
        imageUri = intent.getStringExtra(IMAGE_URI);
    }
}
