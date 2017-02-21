package nyc.c4q.ashiquechowdhury.schoolme;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.List;

import nyc.c4q.ashiquechowdhury.schoolme.models.School;
import nyc.c4q.ashiquechowdhury.schoolme.models.SchoolService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by meltemyildirim on 2/20/17.
 */

public class FilterActivity extends Activity {

    public String borough;
    private Spinner boroughChooser;
    private Button filter;
    private List<School> schoolList;

    public FilterActivity() {
        schoolList = new ArrayList<>();
    }


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView((R.layout.activity_filter));
        boroughChooser = (Spinner) findViewById(R.id.location);
        filter = (Button) findViewById(R.id.filter_bt);
        filter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fillSchoolList(String.valueOf(boroughChooser.getSelectedItem()));

            }
        });

    }

    private void fillSchoolList(String borough) {

        String base_URL = "https://data.cityofnewyork.us";

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(base_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        SchoolService service = retrofit.create(SchoolService.class);

        Call<List<School>> call = service.getResponse(borough);
        call.enqueue(new Callback<List<School>>() {

            @Override
            public void onResponse(Call<List<School>> call, Response<List<School>> response) {
                schoolList.addAll(response.body());
                //pass this list to
                Intent intent = new Intent(getBaseContext(),MainActivity.class);
                intent.putParcelableArrayListExtra("list of schools", (ArrayList<? extends Parcelable>) schoolList);
                startActivity(intent);

//                Log.d(T, "onResponse: " + response.body().get(1).getBoro());
            }

            @Override
            public void onFailure(Call<List<School>> call, Throwable t) {
//                Log.d(TAG, "onFailure: " + t);
            }
        });
    }



}
