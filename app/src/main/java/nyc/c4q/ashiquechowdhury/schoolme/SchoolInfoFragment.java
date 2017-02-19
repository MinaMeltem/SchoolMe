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
public class SchoolInfoFragment extends Fragment implements View.OnClickListener {

    private static String SCHOOLIMAGE;
    private School school;
    private ImageView appBarBackground;
    private LinearLayout schoolNameInfo;
    private LinearLayout schoolEmailInfo;
    private TextView schoolEmailAddress;
    private LinearLayout schoolPhoneInfo;
    private TextView schoolPhone;
    private TextView schoolName;
    private TextView schoolTotalStudents;
    private TextView schoolAddress;
    private TextView schoolBoro;
    private TextView schoolNameHeader;

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
        schoolPhoneInfo = (LinearLayout) view.findViewById(R.id.school_phone_number_info);

        schoolNameHeader = (TextView) view.findViewById(R.id.school_name_header);
        schoolName = (TextView) view.findViewById(R.id.school_name);
        schoolEmailAddress = (TextView) view.findViewById(R.id.school_email);
        schoolPhone = (TextView) view.findViewById(R.id.school_phone_number);
        schoolTotalStudents = (TextView) view.findViewById(R.id.student_number);
        schoolAddress = (TextView) view.findViewById(R.id.school_address);
        schoolBoro = (TextView) view.findViewById(R.id.school_boro);

        Glide.with(view.getContext()).load(SCHOOLIMAGE).into(appBarBackground);
        schoolName.setText(school.getSchool_name());
        schoolNameHeader.setText(school.getSchool_name());
        schoolEmailAddress.setText(school.getEmail());
        schoolPhone.setText(school.getPhone());
//        if (school.getTotal_students() != null) {
//            schoolTotalStudents.setText(school.getTotal_students());
//        } else {
//            schoolTotalStudents.setText("Information Unavailable");
//        }
        schoolAddress.setText(school.getPrimary_address_line_1());
        schoolBoro.setText(school.getBoro());

        schoolNameInfo.setOnClickListener(this);
        schoolEmailInfo.setOnClickListener(this);
        schoolPhoneInfo.setOnClickListener(this);

        return view;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.school_name_info:
                Toast.makeText(getContext(), "Clicked on School Name", Toast.LENGTH_SHORT).show();
                break;
            case R.id.school_email_info:
                sendEmailIntent();
                break;
            case R.id.school_phone_number_info:
                sendPhoneNumberIntent();
                break;
            case R.id.school_address_info:
                sendAddressIntent();
                break;
        }
    }

    private void sendAddressIntent() {

        Toast.makeText(getContext(), "Send Address Intent", Toast.LENGTH_SHORT).show();

//        Intent intent = new Intent();
//        intent.setAction(Intent.ACTION_VIEW);
//        String data = String.format("geo:%s,%s", latitude, longitude);
//        if (zoomLevel != null) {
//            data = String.format("%s?z=%s", data, zoomLevel);
//        }
//        intent.setData(Uri.parse(data));
//        if (intent.resolveActivity(getContext().getPackageManager()) != null) {
//            startActivity(intent);
//        }
    }

    private void sendPhoneNumberIntent() {
        Toast.makeText(getContext(), "YOOO", Toast.LENGTH_SHORT).show();

        String phoneNumber = (String) schoolPhone.getText();
        Intent callIntent = new Intent(Intent.ACTION_DIAL);
        callIntent.setData(Uri.parse("tel:" + phoneNumber));
        if (callIntent.resolveActivity(getActivity().getPackageManager()) != null) {
            startActivity(callIntent);
        }
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
