package com.sharipov.dogs.activity_sub_breeds_list.fragment_sub_breeds;

import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.jakewharton.rxbinding2.support.v7.widget.RxSearchView;
import com.jakewharton.rxbinding2.widget.RxTextView;
import com.sharipov.dogs.R;
import com.sharipov.dogs.activity_images.ImageActivity;
import com.sharipov.dogs.model.data.SubBreedObject;
import com.sharipov.dogs.model.data_provider.SubBreedsDataProvider;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class SubBreedsFragment extends Fragment {

    private static final String TAG = "qqq";
    private static final String BREED = "BREED";
    public static final int DEBOUNCE_TIME_MS = 300;

    private RecyclerView recyclerView;
    private SubBreedsAdapter subBreedsAdapter;
    private ProgressBar progressBar;
    private SearchView searchView;
    private MenuItem searchMenuItem;

    private List<SubBreedObject> subBreedObjectList = new ArrayList<>();
    private Disposable searchTextChanges;

    private String breed;

    public static SubBreedsFragment newInstance(String breed) {
        Bundle args = new Bundle();
        args.putString(BREED, breed);

        SubBreedsFragment fragment = new SubBreedsFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater menuInflater) {
        menuInflater.inflate(R.menu.search_menu, menu);
        searchMenuItem = menu.findItem(R.id.action_search);
        searchView = (SearchView) searchMenuItem.getActionView();
        searchView.setQueryHint(getString(R.string.search_hint));
        searchView.setIconifiedByDefault(true);
        searchTextChanges = RxSearchView.queryTextChanges(searchView)
                .skipInitialValue()
                .debounce(DEBOUNCE_TIME_MS, TimeUnit.MILLISECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .map(query -> query.toString())
                .subscribe(
                        query -> subBreedsAdapter.filter(query),
                        throwable -> Log.d(TAG, "onCreateOptionsMenu: " + throwable.toString())
                );
        super.onCreateOptionsMenu(menu, menuInflater);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getStringsFromBundle();
        setHasOptionsMenu(true);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_sub_breeds, container, false);
        progressBar = view.findViewById(R.id.progress_bar);
        recyclerView = view.findViewById(R.id.sub_breed_recycler_view);

        SubBreedsDataProvider dataProvider = new SubBreedsDataProvider(breed, getActivity().getCacheDir());
        dataProvider.getSubBreedsList(
                subBreedObjects -> {
                    subBreedObjectList = subBreedObjects;
                    progressBar.setVisibility(ProgressBar.GONE);
                    initRecyclerView(subBreedObjectList);
                },
                throwable -> Log.d(TAG, "onCreateView: " + throwable.toString())
        );
        return view;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        searchTextChanges.dispose();
    }

    public void initRecyclerView(List<SubBreedObject> list) {
        subBreedsAdapter = new SubBreedsAdapter(getContext(), list);
        subBreedsAdapter.setOnItemClickListener(
                subBreedObject -> {
                    Log.d(TAG, "onItemClick: " + breed + " " + subBreedObject.getSubBreed());
                    ImageActivity.start(getContext(), breed, subBreedObject.getSubBreed());
                });
        recyclerView.setAdapter(subBreedsAdapter);
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), getSpanCount()));
    }

    private void getStringsFromBundle() {
        Bundle args = getArguments();
        if (args != null) {
            breed = args.getString(BREED);
        }
    }

    private int getSpanCount() {
        int spanCount = 2;
        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            spanCount = 3;
        }
        return spanCount;
    }
}
