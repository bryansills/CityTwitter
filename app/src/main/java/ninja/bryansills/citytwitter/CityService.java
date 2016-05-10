package ninja.bryansills.citytwitter;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface CityService {
    @GET("/")
    Call<List<Tweet>> tweets();
}
