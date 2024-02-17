package ru.sample.duckapp;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import com.bumptech.glide.Glide;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import your.ru.sample.duckapp.ApiClient;
import your.ru.sample.duckapp.DucksApi;
import your.ru.sample.duckapp.RandomDuckResponse;

public class MainActivity extends AppCompatActivity {

    private ImageView duckImageView;
    private Button loadDuckButton;
    private EditText codeEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        duckImageView = findViewById(R.id.duckImageView);
        loadDuckButton = findViewById(R.id.loadDuckButton);
        codeEditText = findViewById(R.id.codeEditText);

        loadDuckButton.setOnClickListener(view -> loadDuck());
    }

    private void loadDuck() {
        String code = codeEditText.getText().toString();
        DucksApi service = ApiClient.getClient().create(DucksApi.class);
        Call<RandomDuckResponse> call;

        if (code.isEmpty()) {
            call = service.getRandomDuck();
        } else {
            int httpCode;
            try {
                httpCode = Integer.parseInt(code);
                call = service.getDuckByCode(httpCode);
            } catch (NumberFormatException e) {
                Toast.makeText(MainActivity.this, "Invalid code. Loading random duck instead.", Toast.LENGTH_SHORT).show();
                call = service.getRandomDuck();
            }
        }

        call.enqueue(new Callback<RandomDuckResponse>() {
            @Override
            public void onResponse(Call<RandomDuckResponse> call, Response<RandomDuckResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    Glide.with(MainActivity.this)
                            .load(response.body().getUrl())
                            .into(duckImageView);
                } else {
                    Toast.makeText(MainActivity.this, "Failed to load image", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<RandomDuckResponse> call, Throwable t) {
                Toast.makeText(MainActivity.this, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}


