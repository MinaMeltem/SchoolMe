package nyc.c4q.ashiquechowdhury.schoolme.models;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by ashiquechowdhury on 2/19/17.
 */

public class SchoolApi {
    private static int called = 0;
    static List<School> mySchoolList;
    private static final String TAG = SchoolApi.class.getSimpleName();
    private static final String BASE_URL = "https://data.cityofnewyork.us";

    public static List<School> getSchoolList(){
        Retrofit retrofit =  new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        SchoolService service = retrofit.create(SchoolService.class);
        Call<List<School>> call = service.getResponse("");
        call.enqueue(new Callback<List<School>>() {
            @Override
            public void onResponse(Call<List<School>> call, Response<List<School>> response) {
                mySchoolList = response.body();
                called = 1;
            }

            @Override
            public void onFailure(Call<List<School>> call, Throwable t) {
            }
        });
        while(called == 0){

        }
        return mySchoolList;
    }
}
