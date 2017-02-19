package nyc.c4q.ashiquechowdhury.schoolme;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import nyc.c4q.ashiquechowdhury.schoolme.models.School;


/**
 * Created by SACC on 2/18/17.
 */
public class SchoolInfoFragment extends Fragment {

    private static String SCHOOLIMAGE;
    private School school;
    private ImageView appBarBackground;
    private LinearLayout schoolNameInfo;
    private LinearLayout schoolEmailInfo;
    private TextView schoolEmailAddress;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = this.getArguments();
        school = (School) bundle.get("SchoolObject");
        SCHOOLIMAGE = bundle.getString("SchoolImage");
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragmentschoolinfo, container, false);

        appBarBackground = (ImageView) view.findViewById(R.id.appbar_background);
        schoolNameInfo = (LinearLayout) view.findViewById(R.id.school_name_info);
        schoolEmailInfo = (LinearLayout) view.findViewById(R.id.school_email_info);
        schoolEmailAddress = (TextView) view.findViewById(R.id.school_email);

        Glide.with(view.getContext()).load(SCHOOLIMAGE).into(appBarBackground);

        schoolNameInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(view.getContext(), "This Worked", Toast.LENGTH_SHORT).show();
            }
        });

        schoolEmailInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendEmailIntent();
            }
        });

        return view;
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
        if (sendIntent.resolveActivity(getActivity().getPackageManager()) != null) {
            startActivity(Intent.createChooser(sendIntent, "Send email"));
        }
    }
}
