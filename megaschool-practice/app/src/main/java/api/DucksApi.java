package api;
import retrofit2.Call;
import retrofit2.http.GET;

public interface DucksApi {
    @GET("random")
    Call<RandomDuckResponse> getRandomDuck();

    @GET("http/{code}")
    Call<RandomDuckResponse> getDuckByCode(@Path("code") int code);
}
