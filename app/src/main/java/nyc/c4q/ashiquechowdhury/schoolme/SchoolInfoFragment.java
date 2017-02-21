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
    private LinearLayout schoolAddressInfo;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = this.getArguments();
        school = (School) bundle.get("SchoolObject");
//        SCHOOLIMAGE = bundle.getString("SchoolImage");
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragmentschoolinfo, container, false);

        appBarBackground = (ImageView) view.findViewById(R.id.appbar_background);
        schoolNameInfo = (LinearLayout) view.findViewById(R.id.school_name_info);
        schoolEmailInfo = (LinearLayout) view.findViewById(R.id.school_email_info);
        schoolPhoneInfo = (LinearLayout) view.findViewById(R.id.school_phone_number_info);
        schoolAddressInfo = (LinearLayout) view.findViewById(R.id.school_address_info);

        schoolNameHeader = (TextView) view.findViewById(R.id.school_name_header);
        schoolName = (TextView) view.findViewById(R.id.school_name);
        schoolEmailAddress = (TextView) view.findViewById(R.id.school_email);
        schoolPhone = (TextView) view.findViewById(R.id.school_phone_number);
        schoolTotalStudents = (TextView) view.findViewById(R.id.student_number);
        schoolAddress = (TextView) view.findViewById(R.id.school_address);
        schoolBoro = (TextView) view.findViewById(R.id.school_boro);

        String something = getSchoolImageUrl(school.getSchool_name());

        Glide.with(view.getContext()).load(something).into(appBarBackground);
        schoolName.setText(school.getSchool_name());
        schoolNameHeader.setText(school.getSchool_name());
        schoolEmailAddress.setText(school.getSchool_email());
        schoolPhone.setText(school.getPhone_number());

        schoolAddress.setText(school.getPrimary_address_line_1());
        schoolBoro.setText(school.getBoro() + ", " + school.getZip());

        schoolNameInfo.setOnClickListener(this);
        schoolEmailInfo.setOnClickListener(this);
        schoolPhoneInfo.setOnClickListener(this);
        schoolAddressInfo.setOnClickListener(this);

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

    private String getSchoolImageUrl(String school_name) {
        String schoolImageUrl = "";
        switch (school_name) {
            case "Henry Street School for International Studies":
                schoolImageUrl = "https://lh3.googleusercontent.com/-KyPAUxRDc6o/Tw3bV_WlnSI/AAAAAAAAWg0/wt2iWU0yFscF_I_yo1Xg1SFPMaowWUpogCHM/s540/DSC_0008.JPG";
                break;
            case "University Neighborhood High School":
                schoolImageUrl = "https://lh3.googleusercontent.com/-p8HAoGfe0YA/T3Ikv2FzsMI/AAAAAAAAgsg/MoFFtwgnotQ-jp-A3WbnpQP_mXHWXcMOQCHM/s540/DSC_0028.JPG";
                break;
            case "East Side Community School":
                schoolImageUrl = "https://lh3.googleusercontent.com/-kAZqObmM1Iw/T_EkbgylrBI/AAAAAAAAuNw/A6yBJyP0LuA_TWgh-MUe4nGoR8D6SPfEACHM/s540/IMG_4202.JPG";
                break;
            case "Marta Valle High School":
                schoolImageUrl = "https://i.ytimg.com/vi/qcW5cY5FxQk/maxresdefault.jpg";
                break;
            case "New Explorations into Science, Technology and Math High School":
                schoolImageUrl = "http://schools.nyc.gov/NR/rdonlyres/00E90A5C-EA6E-4F69-99C5-9BF363252827/19789/group3.JPG";
                break;
            case "Bard High School Early College":
                schoolImageUrl = "https://lh3.googleusercontent.com/-wHqKbqfo7KU/TctEYnR6THI/AAAAAAAACYU/O798rj8993ACSdKwgOyUwRo3dkvVOAxpwCHM/s540/IMG_0319.JPG";
                break;
            case "47 The American Sign Language and English Secondary School":
                schoolImageUrl = "https://i.ytimg.com/vi/W6NxVZjmayc/maxresdefault.jpg";
                break;
            case "The Urban Assembly School for Emergency Management":
                schoolImageUrl = "https://www.google.com/url?sa=i&rct=j&q=&esrc=s&source=images&cd=&ved=0ahUKEwjeyuH_q53SAhXDKiYKHREWDeYQjBwIBA&url=https%3A%2F%2Flh3.googleusercontent.com%2F-KUWbxeEsCrk%2FVRwJJuBvw-I%2FAAAAAAABGJ0%2FzJoCSm52SrksXFlxXTp8vl4X-8EmrOk3gCHM%2Fs540%2FDSCF7801.jpg&psig=AFQjCNE9-7W-M2Ler6i3FlB7jZ3t6DYB6w&ust=1487634352264089";
                break;
            case "Unity Center for Urban Technologies":
                schoolImageUrl = "https://www.google.com/search?q=Unity+Center+for+Urban+Technologies&source=lnms&tbm=isch&sa=X&ved=0ahUKEwiQ44-GrJ3SAhUG0IMKHR99D28Q_AUICigD&biw=1438&bih=800#imgrc=U-XusW600UYDpM:";
                break;
            case "Stephen T. Mather Building Arts & Craftsmanship High School":
                schoolImageUrl = "https://lh3.googleusercontent.com/-Mig8A_nEnto/VVOW1cRDPgI/AAAAAAABLSo/NbNIrMEhkdk36bJGyrkKavKhw4g9p35BQCHM/s540/DSCF8041.jpg";
                break;
            case "M.S. 260 Clinton School Writers & Artists":
                schoolImageUrl = "https://www.google.com/search?q=M.S.+260+Clinton+School+Writers+%26+Artists&source=lnms&tbm=isch&sa=X&ved=0ahUKEwiG1LqtrJ3SAhVB04MKHaF1DtUQ_AUICigD&biw=1438&bih=800#imgrc=hrG4xQss8U3FFM:";
                break;
            case "Manhattan Early College School for Advertising":
                schoolImageUrl = "https://lh3.googleusercontent.com/-m9ZNZlOaaAc/VRwI_i9HD6I/AAAAAAABGJw/U2_sL7L2FCM1H4czdoEINImpMZethg_kwCHM/s540/DSCF7755.jpg";
                break;
            case "Urban Assembly Maker Academy":
                schoolImageUrl = "https://i.ytimg.com/vi/2xVh_vVKCrM/maxresdefault.jpg";
                break;
            case "Food and Finance High School":
                schoolImageUrl = "http://cms.quallsbenson.com/uploads/dsc_0012.jpg";
                break;
            case "Essex Street Academy":
                schoolImageUrl = "https://lh3.googleusercontent.com/-sLN0IcZ0y-E/TrrmsFqIMII/AAAAAAAAPx0/dad1ns8_LakrB5K02VE0eQ1PMniwyXzvgCHM/s540/IMG_1356.JPG";
                break;
            case "High School of Hospitality Management":
                schoolImageUrl = "https://lh3.googleusercontent.com/-NTNZvmaW_8M/T895zCu12QI/AAAAAAAAswI/ovMaceZJP_It2WhmP_G6flwd_TfcxpSMQCHM/s540/DSC_0100.JPG";
                break;
            case "Pace High School":
                schoolImageUrl = "http://schools.nyc.gov/NR/rdonlyres/D16BB8DC-10F5-4F00-95A2-E32C937ADBC4/28372/school1.JPG";
                break;
            case "Urban Assembly School of Design and Construction, The":
                schoolImageUrl = "https://i.ytimg.com/vi/WWbJKKTnqag/maxresdefault.jpg";
                break;
            case "Facing History School, The":
                schoolImageUrl = "https://lh3.googleusercontent.com/-nVzfDY_OKzo/T8UAvxQ1NqI/AAAAAAAArVI/j3O31npnaow2s5GsKRWC8_J1ptny6WhowCHM/s540/DSC_0086.JPG";
                break;
            case "Urban Assembly Academy of Government and Law, The":
                schoolImageUrl = "https://s3.amazonaws.com/urbanassembly/schools/Government-and-Law.jpg";
                break;
        }
        return schoolImageUrl;
    }

    private void sendAddressIntent() {

        Toast.makeText(getContext(), "Send Address Intent", Toast.LENGTH_SHORT).show();

        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_VIEW);
        intent.setData(Uri.parse("geo:0,0?q=" + Uri.encode(school.getSchool_name())));
        if (intent.resolveActivity(getContext().getPackageManager()) != null) {
            startActivity(intent);
        }
    }

    private void sendPhoneNumberIntent() {
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
