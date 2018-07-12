package com.sharipov.dogs.activity_sub_breeds_list;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.sharipov.dogs.R;
import com.sharipov.dogs.activity_images.ImageActivity;
import com.sharipov.dogs.data.SubBreedObject;
import com.sharipov.dogs.data.SubBreedsDataProvider;

import java.util.ArrayList;
import java.util.List;

public class SubBreedsActivity extends AppCompatActivity {

    private static final String TAG = "qqq";
    private static final String BREED = "Breed";
    private static final String TITLE = "Title";

    private String breed;
    private String title;
    private List<SubBreedObject> subBreedObjectList = new ArrayList<>();

    private Toolbar toolbar;
    private RecyclerView recyclerView;
    private SubBreedsAdapter subBreedsAdapter;
    private ProgressBar progressBar;

    public static void start(Context context, String breed, String title) {
        Intent starter = new Intent(context, SubBreedsActivity.class);
        starter.putExtra(BREED, breed);
        starter.putExtra(TITLE,title);
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
        setContentView(R.layout.activity_sub_breed);

        Toolbar toolbar = findViewById(R.id.toolbar_actionbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        breed = getIntent().getStringExtra(BREED);
        title = getIntent().getStringExtra(TITLE);
        setTitle(title);
        Log.d(TAG, "onCreate: " + breed);

        SubBreedsDataProvider dataProvider = new SubBreedsDataProvider(breed);
        dataProvider.getSubBreedsList(new SubBreedsDataProvider.OnGetListListener() {
            @Override
            public void onSuccess(List<SubBreedObject> subBreedObjectList) {
                SubBreedsActivity.this.subBreedObjectList = subBreedObjectList;
                initRecyclerView();
            }

            @Override
            public void onFail(Throwable t) {
                Toast.makeText(SubBreedsActivity.this,t.toString(),Toast.LENGTH_SHORT).show();
            }
        });
        showLoadingProgress();
    }

    private void initRecyclerView() {
        recyclerView = findViewById(R.id.sub_breed_recycler_view);
        subBreedsAdapter = new SubBreedsAdapter(this, subBreedObjectList);
        subBreedsAdapter.setOnItemClickListener(new SubBreedsAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(SubBreedObject subBreedObject) {
                Log.d(TAG, "onItemClick: " + breed + " " + subBreedObject.getSubBreed());
                ImageActivity.start(SubBreedsActivity.this, breed, subBreedObject.getSubBreed());
            }
        });
        recyclerView.setAdapter(subBreedsAdapter);
        recyclerView.setLayoutManager(new GridLayoutManager(getApplicationContext(), getSpanCount()));
    }

    private int getSpanCount(){
        int spanCount = 2;
        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE)
        {
            spanCount = 3;
        }
        return spanCount;
    }

    private void showLoadingProgress() {
        progressBar = findViewById(R.id.progress_bar);
        Handler handler = new Handler();
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (subBreedObjectList.size() == 0) {}
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
