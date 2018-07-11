package com.sharipov.dogs.activity_breeds_list;

import android.content.res.Configuration;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.sharipov.dogs.activity_sub_breeds_grid_images.ImageActivity;
import com.sharipov.dogs.data.BreedObject;
import com.sharipov.dogs.data.BreedsDataProvider;
import com.sharipov.dogs.R;
import com.sharipov.dogs.activity_sub_breeds_list.SubBreedsActivity;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private TextView textView;
    private Toolbar toolbar;
    private RecyclerView recyclerView;
    private BreedsListAdapter breedsListAdapter;
    private List<BreedObject> breedObjects;
    private static final String TAG = "qqq";
    private int spanCount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textView = findViewById(R.id.text_view);
        spanCount = getSpanCount();
        BreedsDataProvider breedsDataProvider = new BreedsDataProvider();
        breedsDataProvider.getBreedObjectList(new BreedsDataProvider.OnGetListListener() {
            @Override
            public void onSuccess(List<BreedObject> breedList) {
                breedObjects = breedList;
                initBreedsListAdapter(breedObjects);
                initRecyclerView(spanCount);
            }

            @Override
            public void onFail(Throwable t) {
                Toast.makeText(MainActivity.this, t.toString(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void initRecyclerView(int spanCount) {
        recyclerView = findViewById(R.id.breeds_recycler_view);
        recyclerView.setAdapter(breedsListAdapter);
        recyclerView.setLayoutManager(new GridLayoutManager(getApplicationContext(), spanCount));
    }

    private void initBreedsListAdapter(List<BreedObject> breedObjects) {
        breedsListAdapter = new BreedsListAdapter(getApplicationContext(), breedObjects);
        breedsListAdapter.setOnItemClickListener(new BreedsListAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BreedObject breedObject) {
                if (breedObject.getSubBreeds().size() > 1) {
                    SubBreedsActivity.start(MainActivity.this, breedObject.getBreed(), breedObject.getTitle());
                } else {
                    String subBreed = "";
                    if (breedObject.getSubBreeds().size() == 1) {
                        subBreed = breedObject.getSubBreeds().get(0);
                        Log.d(TAG, "onItemClick: " + breedObject.getBreed() + " " + subBreed);
                    }
                    Log.d(TAG, "onItemClick: " + breedObject.getBreed());
                    ImageActivity.start(MainActivity.this, breedObject.getBreed(), subBreed);
                }
            }
        });
    }

    private int getSpanCount() {
        int spanCount = 3;
        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            spanCount = 4;
        }
        return spanCount;
    }

    private List<BreedObject> getSearchResultList(String s) {
        List<BreedObject> searchResultList = new ArrayList<>();
        for (BreedObject b : breedObjects) {
            if (b.getBreed().contains(s)) {
                searchResultList.add(b);
            }
        }
        return searchResultList;
    }
}
