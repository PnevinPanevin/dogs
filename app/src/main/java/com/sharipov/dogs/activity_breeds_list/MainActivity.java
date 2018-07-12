package com.sharipov.dogs.activity_breeds_list;

import android.content.res.Configuration;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.sharipov.dogs.activity_images.ImageActivity;
import com.sharipov.dogs.data.BreedObject;
import com.sharipov.dogs.data.BreedsDataProvider;
import com.sharipov.dogs.R;
import com.sharipov.dogs.activity_sub_breeds_list.SubBreedsActivity;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private MenuItem searchMenuItem;
    private SearchView searchView;
    private ProgressBar progressBar;

    private BreedsListAdapter breedsListAdapter;

    private List<BreedObject> breedObjects = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initToolbar();
        getDataFromProvider();
        showLoadingProgress();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_main_menu, menu);
        searchMenuItem = menu.findItem(R.id.action_search);
        searchView = (SearchView) searchMenuItem.getActionView();
        searchView.setQueryHint(getString(R.string.search_hint));
        searchView.setIconifiedByDefault(true);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                initRecyclerView(getSpanCount(), BreedsDataProvider.getFilteredList(newText.toLowerCase(), breedObjects));
                return false;
            }
        });
        return super.onCreateOptionsMenu(menu);
    }

    private void getDataFromProvider() {
        BreedsDataProvider breedsDataProvider = new BreedsDataProvider();
        breedsDataProvider.getBreedObjectList(new BreedsDataProvider.OnGetListListener() {
            @Override
            public void onSuccess(List<BreedObject> breedList) {
                breedObjects = breedList;
                initRecyclerView(getSpanCount(), breedObjects);
            }

            @Override
            public void onFail(Throwable t) {
                Toast.makeText(MainActivity.this, t.toString(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void initToolbar() {
        Toolbar toolbar = findViewById(R.id.toolbar_actionbar);
        setSupportActionBar(toolbar);
    }

    private void initRecyclerView(int spanCount, List<BreedObject> list) {
        recyclerView = findViewById(R.id.breeds_recycler_view);
        recyclerView.setLayoutManager(new GridLayoutManager(getApplicationContext(), spanCount));
        breedsListAdapter = new BreedsListAdapter(getApplicationContext(), list);
        breedsListAdapter.setOnItemClickListener(new BreedsListAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BreedObject breedObject) {
                if (breedObject.getSubBreeds().size() > 0) {
                    SubBreedsActivity.start(MainActivity.this, breedObject.getBreed(), breedObject.getTitle());
                } else {
                    String subBreed = "";
                    if (breedObject.getSubBreeds().size() == 1) {
                        subBreed = breedObject.getSubBreeds().get(0);
                    }
                    ImageActivity.start(MainActivity.this, breedObject.getBreed(), subBreed);
                }
            }
        });
        recyclerView.setAdapter(breedsListAdapter);
    }

    private int getSpanCount() {
        int spanCount = 3;
        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            spanCount = 4;
        }
        return spanCount;
    }

    private void showLoadingProgress() {
        progressBar = findViewById(R.id.progress_bar);
        Handler handler = new Handler();
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (breedObjects.size() == 0) {
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
}
