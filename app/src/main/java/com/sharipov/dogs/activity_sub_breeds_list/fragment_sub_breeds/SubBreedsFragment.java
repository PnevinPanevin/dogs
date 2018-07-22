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
import android.widget.Toast;

import com.sharipov.dogs.R;
import com.sharipov.dogs.activity_images.ImageActivity;
import com.sharipov.dogs.data.SubBreedObject;
import com.sharipov.dogs.data.SubBreedsDataProvider;

import java.util.ArrayList;
import java.util.List;

public class SubBreedsFragment extends Fragment {

    private static final String TAG = "qqq";
    private static final String BREED = "BREED";
    private static final String TITLE = "TITLE";
    private static final String IMAGE_URI = "IMAGE_URI";

    private RecyclerView recyclerView;
    private SubBreedsAdapter subBreedsAdapter;
    private ProgressBar progressBar;
    private SearchView searchView;
    private MenuItem searchMenuItem;

    private List<SubBreedObject> subBreedObjectList = new ArrayList<>();

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
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                subBreedsAdapter.setFilter(SubBreedsDataProvider.getFilteredList(newText.toLowerCase(), subBreedObjectList));
                return false;
            }
        });
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

//        AppCompatActivity activity = (AppCompatActivity) getActivity();
//        Toolbar toolbar = activity.findViewById(R.id.toolbar_actionbar);

        SubBreedsDataProvider dataProvider = new SubBreedsDataProvider(breed);
        dataProvider.getSubBreedsList(new SubBreedsDataProvider.OnGetListListener() {
            @Override
            public void onSuccess(List<SubBreedObject> subBreedObjectList) {
                SubBreedsFragment.this.subBreedObjectList = subBreedObjectList;
                progressBar.setVisibility(ProgressBar.GONE);

                initRecyclerView(subBreedObjectList);
            }

            @Override
            public void onFail(Throwable t) {
                Toast.makeText(getContext(), t.toString(), Toast.LENGTH_SHORT).show();
            }
        });

        return view;
    }

    public void initRecyclerView(List<SubBreedObject> list) {
        subBreedsAdapter = new SubBreedsAdapter(getContext(), list);
        subBreedsAdapter.setOnItemClickListener(
//                new SubBreedsAdapter.OnItemClickListener() {
//            @Override
//            public void onItemClick(SubBreedObject subBreedObject) {
//                Log.d(TAG, "onItemClick: " + breed + " " + subBreedObject.getSubBreed());
//                ImageActivity.start(getContext(), breed, subBreedObject.getSubBreed());
//            }
//        }
                (subBreedObject) -> {
                    Log.d(TAG, "onItemClick: " + breed + " " + subBreedObject.getSubBreed());
                    ImageActivity.start(getContext(), breed, subBreedObject.getSubBreed());
                }
        );
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
