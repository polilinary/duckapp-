package api;

public class RetrofitClient {
    Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("https://random-d.uk/api/")
            .addConverterFactory(GsonConverterFactory.create())
            .build();

    DucksApi ducksApi = retrofit.create(DucksApi.class);
}
