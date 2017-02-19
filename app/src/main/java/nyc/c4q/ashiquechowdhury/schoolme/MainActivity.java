package nyc.c4q.ashiquechowdhury.schoolme;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import io.realm.Realm;
import nyc.c4q.ashiquechowdhury.schoolme.model.School;

public class MainActivity extends AppCompatActivity {
    BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setupBottomNavigation();
        inflateHomeFragment();

        Realm.init(getApplicationContext());
        Realm realm = Realm.getDefaultInstance();
        realm.beginTransaction();

        School school = realm.createObject(School.class);
        school.setPictureURL("http://weburbanist.com/wp-content/uploads/2009/04/orestad-high-school-1.jpg");
        school.setSchoolName("iscoolme");

        realm.commitTransaction();
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

}
