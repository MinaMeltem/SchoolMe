package nyc.c4q.ashiquechowdhury.schoolme;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import nyc.c4q.ashiquechowdhury.schoolme.models.School;
import nyc.c4q.ashiquechowdhury.schoolme.models.SchoolService;
import nyc.c4q.ashiquechowdhury.schoolme.models.SchoolsResponse;
import nyc.c4q.ashiquechowdhury.schoolme.swipe.SwipeStack;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class HomeFragment extends Fragment implements SwipeStack.SwipeStackListener {

    private static final String TAG = SchoolsResponse.class.getSimpleName();
    private List<School> schoolList = new ArrayList<>();
    private SwipeStack swipeStack;
    private SwipeStackAdapter swipeAdapter;

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {

    }

    private School school = new School();
    private String schoolImageUrl;
    private ImageView schoolPic;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.home_view_fragment, container, false);
    }

    @Override
    public void onStart() {
        super.onStart();
        setSwipeViews();
    }

    public void setSwipeViews() {
        swipeStack = (SwipeStack) getView().findViewById(R.id.swipeStack);
//        swipeStack.setListener(this);
        fillSchoolList();
    }

    private void fillSchoolList() {
        schoolPic = (ImageView) getActivity().findViewById(R.id.school_pic);
        swipeAdapter = new SwipeStackAdapter(schoolList);
        swipeStack.setAdapter(swipeAdapter);

        String base_URL = "https://data.cityofnewyork.us";

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(base_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        SchoolService service = retrofit.create(SchoolService.class);

        Call<List<School>> call = service.getResponse();
        call.enqueue(new Callback<List<School>>() {

            @Override
            public void onResponse(Call<List<School>> call, Response<List<School>> response) {
                schoolList.addAll(response.body());
                swipeAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<List<School>> call, Throwable t) {
                Log.d(TAG, "onFailure: " + t);
            }
        });
    }

    @Override
    public void onViewSwipedToRight(int position) {
        School swipedElement = swipeAdapter.getItem(position);
        Toast.makeText(getActivity(), "Added to Favorites",
                Toast.LENGTH_SHORT).show();

//        Realm.init(getActivity().getApplicationContext());
//        Realm realm = Realm.getDefaultInstance();
//        realm.beginTransaction();
//
//        SchoolDbModel schoolDbModel = realm.createObject(SchoolDbModel.class);
//        schoolDbModel.setPictureURL("http://weburbanist.com/wp-content/uploads/2009/04/orestad-high-schoolDbModel-1.jpg");
//        schoolDbModel.setSchoolName("iscoolme");
//
//        realm.commitTransaction();
    }

    @Override
    public void onViewSwipedToLeft(int position) {
        School swipedElement = swipeAdapter.getItem(position);

        Toast.makeText(getContext(), "Disliked",
                Toast.LENGTH_SHORT).show();

    }

    @Override
    public void onStackEmpty() {
        Toast.makeText(getContext(), R.string.stack_empty, Toast.LENGTH_SHORT).show();
    }

    public class SwipeStackAdapter extends BaseAdapter {

        private List<School> schoolList;

        private ImageView schoolPic;
        private TextView schoolName;
        private TextView schoolLocation;
        private TextView studentNumber;
        private TextView advancedPlacement;
        private TextView extraCurricular;
        private TextView schoolRatingTv;


        public SwipeStackAdapter(List<School> schoolList) {
            this.schoolList = schoolList;
        }

        @Override
        public int getCount() {
            return schoolList.size();
        }

        @Override
        public School getItem(int position) {
            return schoolList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {

            if (convertView == null) {
                convertView = getActivity().getLayoutInflater().inflate(R.layout.school_card, parent, false);
            }

            schoolPic = (ImageView) convertView.findViewById(R.id.school_pic);
            schoolName = (TextView) convertView.findViewById(R.id.school_name);
            schoolLocation = (TextView) convertView.findViewById(R.id.school_location);
            studentNumber = (TextView) convertView.findViewById(R.id.student_number);
            advancedPlacement = (TextView) convertView.findViewById(R.id.advanced_placement);
            extraCurricular = (TextView) convertView.findViewById(R.id.extracurricular);
            schoolRatingTv = (TextView) convertView.findViewById(R.id.school_rating);

            String schoolRating = null;
            if (position % 3 == 0) {
                schoolRating = "93%";
            } else if (position % 2 == 0) {
                schoolRating = "96%";
            } else {
                schoolRating = "97%";
            }
            schoolRatingTv.setText(schoolRating);

            school = schoolList.get(position);
            final String finalSchoolRating = schoolRating;
            schoolPic.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View view) {
                    Bundle args = new Bundle();
                    args.putParcelable("SchoolObject", schoolList.get(position));
                    args.putString("SchoolRating", finalSchoolRating);

                    SchoolInfoFragment schoolInfoFragment = new SchoolInfoFragment();
                    schoolInfoFragment.setArguments(args);

                    getActivity().getSupportFragmentManager()
                            .beginTransaction()
                            .replace(R.id.frame_layout, schoolInfoFragment)
                            .addToBackStack(null)
                            .commit();
                    school.getAddtl_info1();
                }
            });

            schoolImageUrl = getSchoolImageUrl(school.getSchool_name());

            Glide.with(getContext()).load(schoolImageUrl).into(schoolPic);


            schoolName.setText(school.getSchool_name());
            schoolLocation.setText(school.getBoro());
            studentNumber.setText(school.getTotal_students());

            if (school.getAdvancedplacement_courses() != null) {
                advancedPlacement.setText("Advanced Placement Courses: \n" + school.getAdvancedplacement_courses());
            } else {
                advancedPlacement.setText("Advanced Placement Courses: \n" + "Information Unavailable");
            }
            if (school.getExtracurricular_activities() != null) {
                extraCurricular.setText("Extracurricular Activities: \n" + school.getExtracurricular_activities());
            } else {
                extraCurricular.setText("Extracurricular Activities: \n" + "Information Unavailable");
            }

            return convertView;
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
    }
}
