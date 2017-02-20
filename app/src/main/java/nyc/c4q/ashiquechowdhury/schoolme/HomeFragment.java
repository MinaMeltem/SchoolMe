package nyc.c4q.ashiquechowdhury.schoolme;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.transition.Fade;
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

import nyc.c4q.ashiquechowdhury.schoolme.models.School;
import nyc.c4q.ashiquechowdhury.schoolme.models.SchoolService;
import nyc.c4q.ashiquechowdhury.schoolme.models.SchoolsResponse;
import nyc.c4q.ashiquechowdhury.schoolme.swipe.HomeSwipeStack;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class HomeFragment extends Fragment implements HomeSwipeStack.SwipeStackListener {

    private static final String TAG = SchoolsResponse.class.getSimpleName();
    private List<School> schoolList = new ArrayList<>();
    private HomeSwipeStack homeSwipeStack;
    private SwipeStackAdapter swipeAdapter;
    private School school = new School();
    private String schoolImageUrl;

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
        homeSwipeStack = (HomeSwipeStack) getView().findViewById(R.id.swipeStack);
        fillSchoolList();
        homeSwipeStack.setListener(this);
    }

    private void fillSchoolList() {

        swipeAdapter = new SwipeStackAdapter(schoolList);
        homeSwipeStack.setAdapter(swipeAdapter);

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

                Log.d(TAG, "onResponse: " + response.body().get(1).getBoro());
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

    public class SwipeStackAdapter extends BaseAdapter implements View.OnClickListener {

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

            schoolImageUrl = getSchoolImageUrl(school.getSchool_name());
            Glide.with(getContext()).load(schoolImageUrl).into(schoolPic);


            schoolName.setText(school.getSchool_name());
            schoolLocation.setText(school.getBoro());
            studentNumber.setText(school.getTotal_students());
            advancedPlacement.setText(school.getAdvancedplacement_courses());
            extraCurricular.setText(school.getExtracurricular_activities());

            schoolName.setOnClickListener(this);
            advancedPlacement.setText("Advanced Placement Courses: \n" + school.getAdvancedplacement_courses());
            extraCurricular.setText("Extracurricular Activities: \n" + school.getExtracurricular_activities());

            schoolPic.setOnClickListener(this);

            return convertView;
        }

        @Override
        public void onClick(View view) {

            SchoolInfoFragment schoolInfoFragment = new SchoolInfoFragment();
            ImageView imageView = (ImageView) view.findViewById(R.id.school_pic);
            String imageTransitionName = "imageTrans";

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {

                schoolInfoFragment.setSharedElementEnterTransition(new DetailsTransition());
                schoolInfoFragment.setEnterTransition(new Fade());
                setExitTransition(new Fade());
                schoolInfoFragment.setSharedElementReturnTransition(new DetailsTransition());

            }

            Bundle args = new Bundle();
            args.putParcelable("SchoolObject", school);
//            args.putString("SchoolImage", schoolImageUrl);

            schoolInfoFragment.setArguments(args);

            getActivity()
                    .getSupportFragmentManager()
                    .beginTransaction()
                    .addSharedElement(imageView, imageTransitionName)
                    .replace(R.id.frame_layout, schoolInfoFragment)
                    .addToBackStack(null)
                    .commit();
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
}
