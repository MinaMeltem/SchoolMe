package nyc.c4q.ashiquechowdhury.schoolme;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import io.realm.Realm;
import io.realm.RealmResults;
import nyc.c4q.ashiquechowdhury.schoolme.favoritesrecycler.SchoolAdapter;
import nyc.c4q.ashiquechowdhury.schoolme.model.SchoolDbModel;

/**
 * Created by ashiquechowdhury on 2/18/17.
 */

public class FavoritesFragment extends Fragment {
    private RecyclerView schoolList;

    @Override
    public View onCreateView(LayoutInflater inflater,ViewGroup parent, Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.fragment_favorites, parent, false);
        schoolList = (RecyclerView) view.findViewById(R.id.school_recycler);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState){
        Realm.init(getActivity().getApplicationContext());
        Realm realm = Realm.getDefaultInstance();
//        realm.beginTransaction();
//
//        SchoolDbModel schoolDbModel = realm.createObject(SchoolDbModel.class);
//        schoolDbModel.setPictureURL("http://weburbanist.com/wp-content/uploads/2009/04/orestad-high-schoolDbModel-1.jpg");
//        schoolDbModel.setSchoolName("iscoolme");
//        schoolDbModel.setPictureURL("http://weburbanist.com/wp-content/uploads/2009/04/orestad-high-schoolDbModel-1.jpg");
//        schoolDbModel.setSchoolName("iscoolme");
//        schoolDbModel.setPictureURL("http://weburbanist.com/wp-content/uploads/2009/04/orestad-high-schoolDbModel-1.jpg");
//        schoolDbModel.setSchoolName("iscoolme");
//        schoolDbModel.setPictureURL("http://weburbanist.com/wp-content/uploads/2009/04/orestad-high-schoolDbModel-1.jpg");
//        schoolDbModel.setSchoolName("iscoolme");
//
//        realm.commitTransaction();

        RealmResults<SchoolDbModel> results = realm.where(SchoolDbModel.class).findAll();
        schoolList.setLayoutManager(new LinearLayoutManager(view.getContext()));
        schoolList.setAdapter(new SchoolAdapter(results));
    }
}
