package nyc.c4q.ashiquechowdhury.schoolme;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import nyc.c4q.ashiquechowdhury.schoolme.model.School;
import nyc.c4q.ashiquechowdhury.schoolme.model.SchoolsResponse;
import nyc.c4q.ashiquechowdhury.schoolme.network.SchoolService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ProfileFragment extends Fragment {
    private String TAG = SchoolsResponse.class.getSimpleName();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.fragment_profile, parent, false);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState){
//        retrofit();
    }

    public void retrofit(){
        String base_URL = "https://data.cityofnewyork.us";
        Retrofit retrofit =  new Retrofit.Builder()
                .baseUrl(base_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        SchoolService service = retrofit.create(SchoolService.class);
        Call<List<School>> call = service.getResponse();
        call.enqueue(new Callback<List<School>>() {
            @Override
            public void onResponse(Call<List<School>> call, Response<List<School>> response) {
                Log.d(TAG, "onResponse: " + response.body().get(1).getBoro());
            }

            @Override
            public void onFailure(Call<List<School>> call, Throwable t) {

            }
        });
    }


}
