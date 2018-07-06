package com.sharipov.dogs;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
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

        BreedsLab breedsLab = new BreedsLab(getApplicationContext());
        List<BreedObject> breedObjects = breedsLab.getBreedObjectList();

        recyclerView = findViewById(R.id.recyclerView);
        myAdapter = new MyAdapter(getApplicationContext(),breedObjects);
        recyclerView.setAdapter(myAdapter);
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
//        String subBreed = "african";//todo запилить метод выдающий картинку в зависимости от subBreed
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
