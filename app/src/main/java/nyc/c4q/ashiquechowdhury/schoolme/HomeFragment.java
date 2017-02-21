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

import io.realm.Realm;
import nyc.c4q.ashiquechowdhury.schoolme.models.School;
import nyc.c4q.ashiquechowdhury.schoolme.models.SchoolDbModel;
import nyc.c4q.ashiquechowdhury.schoolme.models.SchoolService;
import nyc.c4q.ashiquechowdhury.schoolme.models.SchoolsResponse;
import nyc.c4q.ashiquechowdhury.schoolme.swipe.SwipeStack;
import nyc.c4q.ashiquechowdhury.schoolme.util.SchoolImageList;
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
        swipeStack.setListener(this);
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

        Realm.init(getActivity().getApplicationContext());
        Realm realm = Realm.getDefaultInstance();
        realm.beginTransaction();

        SchoolDbModel schoolDbModel = realm.createObject(SchoolDbModel.class);

        schoolDbModel.setPictureURL(SchoolImageList.getSchoolImageUrl(swipedElement.getSchool_name()));
        schoolDbModel.setSchoolName(swipedElement.getSchool_name());

        realm.commitTransaction();
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

            school = schoolList.get(position);
            schoolPic.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View view) {
                    Bundle args = new Bundle();
                    args.putParcelable("SchoolObject", schoolList.get(position));
//            args.putString("SchoolImage", schoolImageUrl);

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

            schoolImageUrl = SchoolImageList.getSchoolImageUrl(school.getSchool_name());

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
                    schoolImageUrl = "https://i.ytimg.com/vi/RjNXz3GnBdg/maxresdefault.jpg";
                    break;
                case "Unity Center for Urban Technologies":
                    schoolImageUrl = "https://i.ytimg.com/vi/GdtvQT70Kqo/maxresdefault.jpg";
                    break;
                case "Stephen T. Mather Building Arts & Craftsmanship High School":
                    schoolImageUrl = "https://lh3.googleusercontent.com/-Mig8A_nEnto/VVOW1cRDPgI/AAAAAAABLSo/NbNIrMEhkdk36bJGyrkKavKhw4g9p35BQCHM/s540/DSCF8041.jpg";
                    break;
                case "M.S. 260 Clinton School Writers & Artists":
                    schoolImageUrl = "https://lh3.googleusercontent.com/-H18w66cyYL8/VhK9J-TpekI/AAAAAAABXq4/3qBbMa62u4A-T__C_G_TXxePln11-UmGACHM/s540/DSCF0216.jpg";
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
    }
}
