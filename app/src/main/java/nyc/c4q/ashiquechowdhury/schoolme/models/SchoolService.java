package nyc.c4q.ashiquechowdhury.schoolme.models;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface SchoolService {

    @GET("/resource/4isn-xf7m.json")
    Call<List<School>> getResponse();
}
