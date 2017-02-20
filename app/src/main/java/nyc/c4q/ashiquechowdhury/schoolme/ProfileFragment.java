package nyc.c4q.ashiquechowdhury.schoolme;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import nyc.c4q.ashiquechowdhury.schoolme.models.SchoolsResponse;

public class ProfileFragment extends Fragment {
    private String TAG = SchoolsResponse.class.getSimpleName();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.fragment_profile, parent, false);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState){
    }

}
