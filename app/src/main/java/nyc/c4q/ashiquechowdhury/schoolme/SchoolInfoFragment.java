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

import com.bumptech.glide.Glide;

import nyc.c4q.ashiquechowdhury.schoolme.models.School;


/**
 * Created by SACC on 2/18/17.
 */
public class SchoolInfoFragment extends Fragment implements View.OnClickListener {

    private School school;
    private LinearLayout schoolNameInfo;
    private LinearLayout schoolEmailInfo;
    private LinearLayout schoolPhoneInfo;
    private LinearLayout schoolAddressInfo;
    private LinearLayout schoolOverviewInfo;
    private LinearLayout schoolExtraCurricularInfo;
    private ImageView appBarBackground;
    private ImageView overviewArrow;
    private ImageView overviewArrow2;
    private TextView schoolEmailAddress;
    private TextView schoolPhone;
    private TextView schoolName;
    private TextView schoolTotalStudents;
    private TextView schoolAddress;
    private TextView schoolBoro;
    private TextView schoolNameHeader;
    private TextView schoolOverviewShort;
    private TextView schoolOverviewFull;
    private TextView schoolExtraCurricularActivitiesShort;
    private TextView schoolExtraCurricularActivitiesFull;
    private TextView schoolRatingTv;
    private String schoolRating;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = this.getArguments();
        school = (School) bundle.get("SchoolObject");
        schoolRating = (String) bundle.get("SchoolRating");
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
        schoolOverviewInfo = (LinearLayout) view.findViewById(R.id.school_overview_info);
        schoolExtraCurricularInfo = (LinearLayout) view.findViewById(R.id.school_extracurricular_info);

        schoolNameHeader = (TextView) view.findViewById(R.id.school_name_header);
        schoolName = (TextView) view.findViewById(R.id.school_name);
        schoolOverviewShort = (TextView) view.findViewById(R.id.school_overview_short);
        schoolOverviewFull = (TextView) view.findViewById(R.id.school_overview_full);
        schoolEmailAddress = (TextView) view.findViewById(R.id.school_email);
        schoolPhone = (TextView) view.findViewById(R.id.school_phone_number);
        schoolTotalStudents = (TextView) view.findViewById(R.id.total_students);
        schoolAddress = (TextView) view.findViewById(R.id.school_address);
        schoolBoro = (TextView) view.findViewById(R.id.school_boro);
        overviewArrow = (ImageView) view.findViewById(R.id.school_overview_arrow);
        overviewArrow2 = (ImageView) view.findViewById(R.id.school_overview_arrow2);
        schoolExtraCurricularActivitiesShort = (TextView) view.findViewById(R.id.school_extracurricular_activities_short);
        schoolExtraCurricularActivitiesFull = (TextView) view.findViewById(R.id.school_extracurricular_activities_full);
        schoolRatingTv = (TextView) view.findViewById(R.id.school_rating_tv);


        String schoolImageUrl = getSchoolImageUrl(school.getSchool_name());

        Glide.with(view.getContext()).load(schoolImageUrl).into(appBarBackground);
        schoolName.setText(school.getSchool_name());
        schoolNameHeader.setText(school.getSchool_name());
        schoolOverviewShort.setText(school.getOverview_paragraph());
        schoolOverviewFull.setText(school.getOverview_paragraph());
        schoolExtraCurricularActivitiesShort.setText(school.getExtracurricular_activities());
        schoolExtraCurricularActivitiesFull.setText(school.getExtracurricular_activities());
        schoolEmailAddress.setText(school.getSchool_email());
        schoolPhone.setText(school.getPhone_number());
        schoolAddress.setText(school.getPrimary_address_line_1());
        schoolTotalStudents.setText(school.getTotal_students());
        schoolBoro.setText(school.getBoro() + ", " + school.getZip());
        schoolRatingTv.setText(schoolRating);

        schoolNameInfo.setOnClickListener(this);
        schoolEmailInfo.setOnClickListener(this);
        schoolPhoneInfo.setOnClickListener(this);
        schoolAddressInfo.setOnClickListener(this);
        schoolOverviewInfo.setOnClickListener(this);
        schoolExtraCurricularInfo.setOnClickListener(this);

        return view;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.school_overview_info:
                if (schoolOverviewShort.getVisibility() == View.VISIBLE) {
                    schoolOverviewShort.setVisibility(View.GONE);
                    overviewArrow.setImageResource(R.drawable.ic_arrow_drop_up_black_24dp);
                    schoolOverviewFull.setVisibility(View.VISIBLE);
                } else {
                    schoolOverviewFull.setVisibility(View.GONE);
                    overviewArrow.setImageResource(R.drawable.ic_arrow_drop_down_black_24dp);
                    schoolOverviewShort.setVisibility(View.VISIBLE);
                }
                break;
            case R.id.school_extracurricular_info:
                if (schoolExtraCurricularActivitiesShort.getVisibility() == View.VISIBLE) {
                    schoolExtraCurricularActivitiesShort.setVisibility(View.GONE);
                    overviewArrow2.setImageResource(R.drawable.ic_arrow_drop_up_black_24dp);
                    schoolExtraCurricularActivitiesFull.setVisibility(View.VISIBLE);
                } else {
                    schoolExtraCurricularActivitiesFull.setVisibility(View.GONE);
                    overviewArrow2.setImageResource(R.drawable.ic_arrow_drop_down_black_24dp);
                    schoolExtraCurricularActivitiesShort.setVisibility(View.VISIBLE);
                }
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
                schoolImageUrl = getString(R.string.henry_street_school);
                break;
            case "University Neighborhood High School":
                schoolImageUrl = getString(R.string.university_neighborhood_hs);
                break;
            case "East Side Community School":
                schoolImageUrl = getString(R.string.east_side_community_school);
                break;
            case "Marta Valle High School":
                schoolImageUrl = getString(R.string.marta_valle_hs);
                break;
            case "New Explorations into Science, Technology and Math High School":
                schoolImageUrl = getString(R.string.new_explorations_into_science);
                break;
            case "Bard High School Early College":
                schoolImageUrl = getString(R.string.bard_hs_early_college);
                break;
            case "47 The American Sign Language and English Secondary School":
                schoolImageUrl = getString(R.string.the_american_sign_language);
                break;
            case "The Urban Assembly School for Emergency Management":
                schoolImageUrl = getString(R.string.urban_assembly_school_emergency);
                break;
            case "Unity Center for Urban Technologies":
                schoolImageUrl = getString(R.string.unity_center_for_urban_tech);
                break;
            case "Stephen T. Mather Building Arts & Craftsmanship High School":
                schoolImageUrl = getString(R.string.stephen_t_mather);
                break;
            case "M.S. 260 Clinton School Writers & Artists":
                schoolImageUrl = getString(R.string.clinton_school_writers);
                break;
            case "Manhattan Early College School for Advertising":
                schoolImageUrl = getString(R.string.manhattan_early_college);
                break;
            case "Urban Assembly Maker Academy":
                schoolImageUrl = getString(R.string.urban_assembly_maker_academy);
                break;
            case "Food and Finance High School":
                schoolImageUrl = getString(R.string.food_and_finance);
                break;
            case "Essex Street Academy":
                schoolImageUrl = getString(R.string.essex_street_academy);
                break;
            case "High School of Hospitality Management":
                schoolImageUrl = getString(R.string.hs_of_hospitality_management);
                break;
            case "Pace High School":
                schoolImageUrl = getString(R.string.pace_hs);
                break;
            case "Urban Assembly School of Design and Construction, The":
                schoolImageUrl = getString(R.string.urban_assembly_school_design);
                break;
            case "Facing History School, The":
                schoolImageUrl = getString(R.string.facing_history_school);
                break;
            case "Urban Assembly Academy of Government and Law, The":
                schoolImageUrl = getString(R.string.urban_assembly_academy_government);
                break;
        }
        return schoolImageUrl;
    }

    private void sendAddressIntent() {
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
