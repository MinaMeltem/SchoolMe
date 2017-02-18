package nyc.c4q.ashiquechowdhury.schoolme;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
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
import nyc.c4q.ashiquechowdhury.schoolme.swipe.SwipeStack;

public class HomeFragment extends Fragment implements SwipeStack.SwipeStackListener {

    private String SCHOOL_IMAGE = "http://vignette1.wikia.nocookie.net/springfieldbound/images/7/7b/Springfield_Elementary_School.PNG/revision/latest?cb=20120516024143";
    private ArrayList<School> data; // CHANGE TO MODEL
    private SwipeStack swipeStack;
    private SwipeStackAdapter swipeAdapter;
    private ImageView schoolPic;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.swipe_view_fragment, container, false);
    }

    @Override
    public void onStart() {
        super.onStart();
        setViews();
    }

    public void setViews() {
        swipeStack = (SwipeStack) getView().findViewById(R.id.swipeStack);
        data = new ArrayList<>();
        swipeAdapter = new SwipeStackAdapter(data);
        swipeStack.setAdapter(swipeAdapter);
        swipeStack.setListener(this);
        fillWithTestData();
        schoolPic = (ImageView) getView().findViewById(R.id.school_pic);
    }

    private void fillWithTestData() {
        for (int x = 0; x < 5; x++) {
            data.add(new School(getString(R.string.school_name) + " " + (x + 1)));
        }
    }

    @Override
    public void onViewSwipedToRight(int position) {
        School swipedElement = swipeAdapter.getItem(position);
        Toast.makeText(getActivity(), "Added to Favorites",
                Toast.LENGTH_SHORT).show();
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

        //ADD TPP TO LIST, CREATE MODEL

        private List<School> data;

        public SwipeStackAdapter(List<School> data) {
            this.data = data;
        }

        @Override
        public int getCount() {
            return data.size();
        }

        @Override
        public School getItem(int position) {
            return data.get(position);
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

            ImageView ivSchoolname = (ImageView) convertView.findViewById(R.id.school_pic);
            TextView tvBoroughName = (TextView) convertView.findViewById(R.id.school_location);
            TextView tvSchoolname = (TextView) convertView.findViewById(R.id.school_name);

            School school = data.get(position);

            Glide.with(getContext()).load(SCHOOL_IMAGE).into(ivSchoolname);
            tvBoroughName.setText(school.getSchoolBorough());
            tvSchoolname.setText(school.getSchoolName());

            ivSchoolname.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Toast.makeText(getContext(), "YOOOO", Toast.LENGTH_SHORT).show();
                }
            });

            return convertView;
        }
    }
}
