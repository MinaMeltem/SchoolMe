package nyc.c4q.ashiquechowdhury.schoolme.Network;

import java.util.List;

import nyc.c4q.ashiquechowdhury.schoolme.Model.School;
import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by meltemyildirim on 2/18/17.
 * https://data.cityofnewyork.us/resource/4isn-xf7m.json

 */

public interface SchoolService {

    @GET("/resource/4isn-xf7m.json")
    Call<List<School>> getResponse();


}
