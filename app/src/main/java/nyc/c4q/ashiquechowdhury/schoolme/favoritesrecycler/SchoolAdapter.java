package nyc.c4q.ashiquechowdhury.schoolme.favoritesrecycler;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import io.realm.RealmResults;
import nyc.c4q.ashiquechowdhury.schoolme.R;
import nyc.c4q.ashiquechowdhury.schoolme.model.School;

/**
 * Created by ashiquechowdhury on 2/18/17.
 */
public class SchoolAdapter extends RecyclerView.Adapter<SchoolViewHolder> {
    List<School> favoriteSchools;

    public SchoolAdapter(RealmResults<School> results) {
        favoriteSchools = results;
    }

    @Override
    public SchoolViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.favorite_school_card, parent, false);
        return new SchoolViewHolder(view);
    }

    @Override
    public void onBindViewHolder(SchoolViewHolder holder, int position) {
        holder.bind(favoriteSchools.get(position));
    }

    @Override
    public int getItemCount() {
        return favoriteSchools.size();
    }
}
