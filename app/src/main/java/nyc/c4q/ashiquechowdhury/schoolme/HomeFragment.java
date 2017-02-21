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
            advancedPlacement.setText(school.getAdvancedplacement_courses());
            extraCurricular.setText(school.getExtracurricular_activities());

            advancedPlacement.setText("Advanced Placement Courses: \n" + school.getAdvancedplacement_courses());
            extraCurricular.setText("Extracurricular Activities: \n" + school.getExtracurricular_activities());

            return convertView;
        }

    }
}
