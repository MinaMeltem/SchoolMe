package nyc.c4q.ashiquechowdhury.schoolme;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

/**
 * Created by SACC on 2/21/17.
 */

public class FilterActivity extends AppCompatActivity {

    private TextView filterInstructionsTv;
    private Button filterButton;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filter);

        filterInstructionsTv = (TextView) findViewById(R.id.filter_instructions);
        filterInstructionsTv.setText("Select the borough\n where you'd like to search.");

        filterButton = (Button) findViewById(R.id.filter_button);
        filterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
            }
        });
    }
}