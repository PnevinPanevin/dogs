package com.sharipov.dogs.activity_sub_breeds_list.fragment_sub_breeds_images;

import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.sharipov.dogs.R;
import com.sharipov.dogs.data.ImageDataProvider;

import java.util.List;

public class SubBreedsImagesFragment extends Fragment{

    private static final String TAG = "qqq";
    private static final String BREED = "BREED";
    private static final String TITLE = "TITLE";
    private static final String IMAGE_URI = "IMAGE_URI";

    private String breed;
    private List<String> imageList;

    private ProgressBar progressBar;
    private RecyclerView recyclerView;
    private SubBreedsImagesAdapter imageAdapter;

    public static Fragment newInstance(String breed){
        Bundle args = new Bundle();
        args.putString(BREED, breed);

        SubBreedsImagesFragment fragment = new SubBreedsImagesFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getStringsFromBundle();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_sub_breeds_images, container, false);

        Log.d(TAG, "onCreateView: start");
        
        progressBar = view.findViewById(R.id.progress_bar);
        recyclerView = view.findViewById(R.id.sub_breed_image_recycler_view);
        ImageDataProvider imageDataProvider = new ImageDataProvider(breed, "");
        imageDataProvider.getImageList(new ImageDataProvider.OnGetList() {
            @Override
            public void onSuccess(List<String> list) {
                Log.d(TAG, "onSuccess: ");
                imageList = list;
                progressBar.setVisibility(ProgressBar.GONE);

                imageAdapter = new SubBreedsImagesAdapter(getContext(), imageList);
                imageAdapter.setOnItemClickListener((String imageUri) -> {

                });
                recyclerView.setAdapter(imageAdapter);
                recyclerView.setLayoutManager(new GridLayoutManager(getContext(), getSpanCount()));
            }

            @Override
            public void onFail(Throwable t) {
                Toast.makeText(getContext(),t.getLocalizedMessage(),Toast.LENGTH_SHORT).show();
                Log.d(TAG, "onFail: " + t.getLocalizedMessage());
            }
        });
        return view;
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
