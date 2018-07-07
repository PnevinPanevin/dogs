package com.sharipov.dogs;

import android.content.Context;
import android.content.res.Configuration;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import com.sharipov.dogs.Data.BreedObject;
import com.sharipov.dogs.Data.BreedsLab;
import com.sharipov.dogs.Data.MyAdapter;
import java.util.List;
public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private MyAdapter myAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        int spanCount = 2;
        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE)
        {
            spanCount = 3;
        }

        BreedsLab breedsLab = new BreedsLab(getApplicationContext());
        List<BreedObject> breedObjects = breedsLab.getBreedObjectList();

        recyclerView = findViewById(R.id.recyclerView);
        myAdapter = new MyAdapter(getApplicationContext(),breedObjects);
        myAdapter.setOnItemClickListener((position) -> {

                //здесь будет переход в другую активити
            });
        recyclerView.setAdapter(myAdapter);
        recyclerView.setLayoutManager(new GridLayoutManager(getApplicationContext(),spanCount));
    }

//        imageView.setOnClickListener((View v) -> {
//
//        });
//    void randomImageCall() {
//        Call<RandomImage> call = api.getRandomImage();
//
//        call.enqueue(new Callback<RandomImage>() {
//            @Override
//            public void onResponse(Call<RandomImage> call, Response<RandomImage> response) {
//                RandomImage randomImage = response.body();
//                textView.setText(randomImage.getMessage());
//            }
//
//            @Override
//            public void onFailure(Call<RandomImage> call, Throwable t) {
//                Toast.makeText(getApplicationContext(), t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
//            }
//        });
//    }
//
//    void randomAfricanCall(HashMap<String, List<String>> map) {
//        String subBreed = "african";
//        String breed = "";
//        List<String> breedList = map.get(subBreed);
//        if (breedList.size() != 0) {
//            Random random = new Random();
//            breed = breedList.get(random.nextInt(breedList.size())) + "/";
//        }
//        Call<RandomImage> call = api.getRandomImage(subBreed, breed);
//        call.enqueue(new Callback<RandomImage>() {
//            @Override
//            public void onResponse(Call<RandomImage> call, Response<RandomImage> response) {
//                RandomImage randomImage = response.body();
//                String imageUrl = randomImage.getMessage();
//                textView.setText(imageUrl);
//                Picasso.get().load(imageUrl).into(imageView);
//            }
//
//            @Override
//            public void onFailure(Call<RandomImage> call, Throwable t) {
//                Toast.makeText(getApplicationContext(), t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
//            }
//        });
//    }
//
//    void breedsCall() {
//        Call<Breeds> callBreeds = api.getBreeds();
//        callBreeds.enqueue(new Callback<Breeds>() {
//            @Override
//            public void onResponse(Call<Breeds> call, Response<Breeds> response) {
//                Breeds breeds = response.body();
//                HashMap<String, List<String>> map = breeds.getMessage();
//
//                randomAfricanCall(map);
//
////                StringBuilder sb = new StringBuilder();
////                for (Map.Entry<String,List<Object>> entry: map.entrySet()){
////                    sb.append(entry.getKey() + ": " + entry.getValue().size() + "\n");
////                }
////                textView.setText(sb.toString());
//            }
//
//            @Override
//            public void onFailure(Call<Breeds> call, Throwable t) {
//                Toast.makeText(getApplicationContext(), t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
//            }
//        });
//    }
}
