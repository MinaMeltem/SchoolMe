package nyc.c4q.ashiquechowdhury.schoolme;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        inflateFragment();
    }

    void inflateFragment(){
        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.frame_layout, new SwipeViewFragment())
                .commit();
    }

}
