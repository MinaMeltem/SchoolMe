package nyc.c4q.ashiquechowdhury.schoolme;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by ashiquechowdhury on 2/18/17.
 */

public class FavoritesFragment extends Fragment {


    @Override
    public View onCreateView(LayoutInflater inflater,ViewGroup parent, Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.fragment_favorites, parent, false);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState){

    }


}
