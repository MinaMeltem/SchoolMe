package nyc.c4q.ashiquechowdhury.schoolme;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import java.util.ArrayList;

import nyc.c4q.ashiquechowdhury.schoolme.models.School;

public class MainActivity extends AppCompatActivity {
    private BottomNavigationView bottomNavigationView;
    private ArrayList<School> schools;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        schools = new ArrayList<>();

        schools = getIntent().getParcelableArrayListExtra("list of schools");
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
                        return true;
                    }
                }
        );
    }

    private void inflateProfileFragment() {
        getSupportFragmentManager().beginTransaction().replace(R.id.frame_layout, new ProfileFragment()).commit();
    }

    private void inflateHomeFragment() {
        HomeFragment fragment = new HomeFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelableArrayList("list of schools",schools);
        fragment.setArguments(bundle);
        getSupportFragmentManager().beginTransaction().replace(R.id.frame_layout, fragment).commit();
    }

    private void inflateFavoritesFragment() {
        getSupportFragmentManager().beginTransaction().replace(R.id.frame_layout, new FavoritesFragment()).commit();
    }
}
