package com.sharipov.dogs.activity_breeds_list;

import android.content.res.Configuration;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.jakewharton.rxbinding2.support.v7.widget.RxSearchView;
import com.sharipov.dogs.activity_images.ImageActivity;
import com.sharipov.dogs.activity_sub_breeds_list.SubBreedsFragmentsActivity;
import com.sharipov.dogs.model.data.BreedObject;
import com.sharipov.dogs.model.data_provider.BreedsDataProvider;
import com.sharipov.dogs.R;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

public class MainActivity extends AppCompatActivity {

    public static final String TAG = "qqq";
    public static final int DEBOUNCE_TIME_MS = 300;

    private RecyclerView recyclerView;
    private MenuItem searchMenuItem;
    private SearchView searchView;
    private ProgressBar progressBar;

    private BreedsListAdapter breedsListAdapter;

    private List<BreedObject> breedObjects = new ArrayList<>();
    private Disposable searchQueryChanges;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        progressBar = findViewById(R.id.progress_bar);
        initToolbar();
        getDataFromProvider();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.search_menu, menu);
        searchMenuItem = menu.findItem(R.id.action_search);
        searchView = (SearchView) searchMenuItem.getActionView();
        searchView.setQueryHint(getString(R.string.search_hint));
        searchView.setIconifiedByDefault(true);
        searchQueryChanges = RxSearchView.queryTextChanges(searchView)
                .skipInitialValue()
                .debounce(DEBOUNCE_TIME_MS, TimeUnit.MILLISECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .map(query -> query.toString())
                .subscribe(
                        query -> breedsListAdapter.filter(query),
                        throwable -> Log.d(TAG, "onCreateOptionsMenu: " + throwable.toString())
                );
        return super.onCreateOptionsMenu(menu);
    }

    private void getDataFromProvider() {
        BreedsDataProvider provider = new BreedsDataProvider(getCacheDir());
        provider.getBreedObjectList(
                breedList -> {
                    breedObjects = breedList;
                    progressBar.setVisibility(ProgressBar.GONE);
                    initRecyclerView(getSpanCount(), breedObjects);
                },
                throwable -> Toast.makeText(MainActivity.this, throwable.toString(), Toast.LENGTH_SHORT).show()
        );
    }

    private void initToolbar() {
        Toolbar toolbar = findViewById(R.id.toolbar_actionbar);
        setSupportActionBar(toolbar);
    }

    private void initRecyclerView(int spanCount, List<BreedObject> list) {
        recyclerView = findViewById(R.id.breeds_recycler_view);
        recyclerView.setLayoutManager(new GridLayoutManager(getApplicationContext(), spanCount));
        breedsListAdapter = new BreedsListAdapter(getApplicationContext(), list);
        breedsListAdapter.setOnItemClickListener(
                (transitionImageView, breedObject) -> {
                    if (breedObject.getSubBreeds().size() > 0) {
                        SubBreedsFragmentsActivity.start(MainActivity.this, breedObject.getBreed(), breedObject.getTitle(), breedObject.getImageUri(), transitionImageView);
                    } else {
                        String subBreed = "";
                        if (breedObject.getSubBreeds().size() == 1) {
                            subBreed = breedObject.getSubBreeds().get(0);
                        }
                        ImageActivity.start(MainActivity.this, breedObject.getBreed(), subBreed);
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

    @Override
    protected void onDestroy() {
        super.onDestroy();
        searchQueryChanges.dispose();
    }
}
