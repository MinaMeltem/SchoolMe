package nyc.c4q.ashiquechowdhury.schoolme;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import de.hdodenhof.circleimageview.CircleImageView;
import nyc.c4q.ashiquechowdhury.schoolme.models.SchoolsResponse;

public class ProfileFragment extends Fragment {
    private String TAG = SchoolsResponse.class.getSimpleName();
    ImageView gifImage;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, parent, false);
        gifImage = (CircleImageView) view.findViewById(R.id.profile_image);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        Glide.with(getContext()).load(R.raw.calvin).asGif().into(gifImage);
    }
}
