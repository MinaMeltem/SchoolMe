package nyc.c4q.ashiquechowdhury.schoolme;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;

import java.util.List;

import nyc.c4q.ashiquechowdhury.schoolme.Model.School;
import nyc.c4q.ashiquechowdhury.schoolme.Model.SchoolsResponse;
import nyc.c4q.ashiquechowdhury.schoolme.Network.SchoolService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {
    BottomNavigationView bottomNavigationView;
    private String TAG = SchoolsResponse.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        retrofit();

        setupBottomNavigation();
        inflateHomeFragment();
    }

    private void setupBottomNavigation() {
        bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(
                new BottomNavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.profile_menu_item:
                                inflateProfileFragment();
                                break;
                            case R.id.home_menu_item:
                                inflateHomeFragment();
                                break;
                            case R.id.favorites_menu_item:
                                inflateFavoritesFragment();
                                break;
                        }
                        return false;
                    }
                }
        );
    }

    private void inflateProfileFragment() {
        getSupportFragmentManager().beginTransaction().replace(R.id.frame_layout, new ProfileFragment()).commit();
    }

    private void inflateHomeFragment() {
        getSupportFragmentManager().beginTransaction().replace(R.id.frame_layout, new HomeFragment()).commit();
    }

    private void inflateFavoritesFragment() {
        getSupportFragmentManager().beginTransaction().replace(R.id.frame_layout, new FavoritesFragment()).commit();
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
