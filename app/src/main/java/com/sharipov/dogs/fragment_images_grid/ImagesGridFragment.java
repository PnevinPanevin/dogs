package com.sharipov.dogs.fragment_images_grid;

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
import android.view.ViewParent;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.sharipov.dogs.R;
import com.sharipov.dogs.activity_fullscreen_image.FullscreenImageActivity;
import com.sharipov.dogs.data.ImageDataProvider;

import java.util.List;

public class ImagesGridFragment extends Fragment {

    private static final String TAG = "qqq";
    private static final String BREED = "BREED";
    private static final String SUB_BREED = "SUB_BREED";

    private String breed;
    private String subBreed;
    private List<String> imageList;

    private ProgressBar progressBar;
    private RecyclerView recyclerView;
    private ImagesGridAdapter imageAdapter;

    public static Fragment newInstance(String breed, String subBreed) {
        Bundle args = new Bundle();
        args.putString(BREED, breed);
        args.putString(SUB_BREED, subBreed);

        ImagesGridFragment fragment = new ImagesGridFragment();
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
        View view = inflater.inflate(R.layout.fragment_images_grid, container, false);

        progressBar = view.findViewById(R.id.progress_bar);
        recyclerView = view.findViewById(R.id.image_recycler_view);

        ImageDataProvider imageDataProvider = new ImageDataProvider(breed, subBreed);
        imageDataProvider.getImageList(new ImageDataProvider.OnGetList() {
            @Override
            public void onSuccess(List<String> list) {
                imageList = list;
                progressBar.setVisibility(ProgressBar.GONE);

                imageAdapter = new ImagesGridAdapter(getContext(), imageList);
                imageAdapter.setOnItemClickListener(
                        (parent, imageUri) -> FullscreenImageActivity.
                                start(getActivity(), parent.findViewById(R.id.image_view), imageUri)
                );
                recyclerView.setAdapter(imageAdapter);
                recyclerView.setLayoutManager(new GridLayoutManager(getContext(), getSpanCount()));
            }

            @Override
            public void onFail(Throwable t) {
                Toast.makeText(getContext(), t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                Log.d(TAG, "onFail: " + t.getLocalizedMessage());
            }
        });
        return view;
    }

    private void getStringsFromBundle() {
        Bundle args = getArguments();
        if (args != null) {
            breed = args.getString(BREED);
            subBreed = args.getString(SUB_BREED);
        }
    }

    private int getSpanCount() {
        int spanCount = 3;
        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            spanCount = 4;
        }
        return spanCount;
    }
}
