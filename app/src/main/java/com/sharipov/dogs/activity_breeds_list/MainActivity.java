package com.sharipov.dogs.activity_breeds_list;

import android.content.res.Configuration;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.sharipov.dogs.Data.BreedObject;
import com.sharipov.dogs.Data.BreedsDataProvider;
import com.sharipov.dogs.R;
import com.sharipov.dogs.activity_sub_breeds.SubBreedsActivity;

import java.util.List;
public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private BreedsListAdapter breedsListAdapter;
    private List<BreedObject> breedObjects;
    private static final String TAG = "qqq";
    private int spanCount = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        spanCount = getSpanCount();
        BreedsDataProvider breedsDataProvider = new BreedsDataProvider();
        breedsDataProvider.getBreedObjectList(new BreedsDataProvider.OnGetListListener() {
            @Override
            public void onSuccess(List<BreedObject> breedList) {
                MainActivity.this.breedObjects = breedList;
                initRecyclerView(spanCount);
            }
            @Override
            public void onFail(Throwable t) {
                Toast.makeText(MainActivity.this, t.toString(),Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void initRecyclerView(int spanCount) {
        recyclerView = findViewById(R.id.breeds_recycler_view);
        breedsListAdapter = new BreedsListAdapter(getApplicationContext(), breedObjects);
        breedsListAdapter.setOnItemClickListener(new BreedsListAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BreedObject breedObject) {
                if (breedObject.getSubBreeds().size() !=0 ){
                    SubBreedsActivity.start(MainActivity.this, breedObject.getBreed());
                } else {

                }
            }
        });
        recyclerView.setAdapter(breedsListAdapter);
        recyclerView.setLayoutManager(new GridLayoutManager(getApplicationContext(),spanCount));
    }

    private int getSpanCount(){
        int spanCount = 2;
        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE)
        {
            spanCount = 3;
        }
        return spanCount;
    }

}
