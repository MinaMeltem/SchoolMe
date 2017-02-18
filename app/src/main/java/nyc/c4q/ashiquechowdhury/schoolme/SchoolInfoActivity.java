package nyc.c4q.ashiquechowdhury.schoolme;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

/**
 * Created by SACC on 2/18/17.
 */

public class SchoolInfoActivity extends AppCompatActivity {

    private ImageView appBarBackground;
    private String IMAGE_URL = "https://maps.googleapis.com/maps/api/streetview?size=600x300&location=long%island%20city%20high%20school&key=AIzaSyB7voACTntrjU3uoZ3DSOOdZ8qpBUK-8JI";
    private LinearLayout schoolNameInfo;
    private LinearLayout schoolEmailInfo;
    private TextView schoolEmailAddress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activityschoolinfo);

        appBarBackground = (ImageView) findViewById(R.id.appbar_background);
        schoolNameInfo = (LinearLayout) findViewById(R.id.school_name_info);
        schoolEmailInfo = (LinearLayout) findViewById(R.id.school_email_info);
        schoolEmailAddress = (TextView) findViewById(R.id.school_email);

        Glide.with(getApplicationContext()).load(IMAGE_URL).into(appBarBackground);

        schoolNameInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(SchoolInfoActivity.this, "This Worked", Toast.LENGTH_SHORT).show();
            }
        });

        schoolEmailInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendEmailIntent();
            }
        });
    }

    private void sendEmailIntent() {
        String emailAddress = (String) schoolEmailAddress.getText();
        String uriText =
                "mailto:" + emailAddress +
                        "?subject=" + Uri.encode("School Inquiry") +
                        "&body=" + Uri.encode("Add your text here.");

        Uri uri = Uri.parse(uriText);

        Intent sendIntent = new Intent(Intent.ACTION_SENDTO);
        sendIntent.setData(uri);
        if (sendIntent.resolveActivity(getPackageManager()) != null) {
            startActivity(Intent.createChooser(sendIntent, "Send email"));
        }
    }
}
