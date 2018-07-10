package com.sharipov.dogs;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.sharipov.dogs.Data.SubBreedObject;
import com.sharipov.dogs.Data.SubBreedsDataProvider;
import com.sharipov.dogs.R;
import com.sharipov.dogs.ResponseStructures.Api;
import com.sharipov.dogs.ResponseStructures.RandomImage;
import com.sharipov.dogs.ResponseStructures.SubBreeds;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class TestActivity extends AppCompatActivity {

    public interface OnGetList{
        void onSuccess(List<SubBreedObject> list);
        void onFail(Throwable t);
    }

    public static final String TAG = "TAG";
    public static final String BREED = "BREED";
    private TextView textView;
    private String breed;

    private List<SubBreedObject> subBreedsList;

    public static void start(Context context, String breed) {
        Intent starter = new Intent(context, TestActivity.class);
        starter.putExtra(BREED, breed);
        context.startActivity(starter);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);

        breed = getIntent().getStringExtra(BREED);

        textView = findViewById(R.id.text_view);
        textView.setText(breed);

    }
}
