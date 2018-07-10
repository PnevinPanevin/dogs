package com.sharipov.dogs.activity_sub_breeds;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Toast;

import com.sharipov.dogs.R;
import com.sharipov.dogs.Data.SubBreedObject;
import com.sharipov.dogs.Data.SubBreedsDataProvider;

import java.util.List;

public class SubBreedsActivity extends AppCompatActivity {

    private static final String TAG = "qqq";
    private RecyclerView recyclerView;
    private SubBreedsAdapter subBreedsAdapter;
    private static final String BREED = "Breed";
    private String breed;
    private List<SubBreedObject> subBreedObjectList;
    private int spanCount;

    public static void start(Context context, String breed) {
        Intent starter = new Intent(context, SubBreedsActivity.class);
        starter.putExtra(BREED, breed);
        context.startActivity(starter);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sub_breed);

        spanCount = getSpanCount();
        breed = getIntent().getStringExtra(BREED);
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
    }

    private void initRecyclerView() {
        recyclerView = findViewById(R.id.sub_breed_recycler_view);
        subBreedsAdapter = new SubBreedsAdapter(this, subBreedObjectList);
        subBreedsAdapter.setOnItemClickListener(new SubBreedsAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {

            }
        });
        recyclerView.setAdapter(subBreedsAdapter);
        recyclerView.setLayoutManager(new GridLayoutManager(getApplicationContext(), spanCount));
    }

    private int getSpanCount(){
        int spanCount = 1;
        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE)
        {
            spanCount = 2;
        }
        return spanCount;
    }
}
