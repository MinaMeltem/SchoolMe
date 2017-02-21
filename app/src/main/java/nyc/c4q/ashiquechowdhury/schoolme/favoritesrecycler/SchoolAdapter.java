package nyc.c4q.ashiquechowdhury.schoolme.favoritesrecycler;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import io.realm.Realm;
import io.realm.RealmResults;
import nyc.c4q.ashiquechowdhury.schoolme.R;
import nyc.c4q.ashiquechowdhury.schoolme.models.SchoolDbModel;

/**
 * Created by ashiquechowdhury on 2/18/17.
 */
public class SchoolAdapter extends RecyclerView.Adapter<SchoolViewHolder> {
    private List<SchoolDbModel> favoriteSchoolDbModels;

    public SchoolAdapter(RealmResults<SchoolDbModel> results) {
        favoriteSchoolDbModels = results;
    }

    @Override
    public SchoolViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.favorite_school_card, parent, false);
        return new SchoolViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final SchoolViewHolder holder, final int position) {
        final SchoolDbModel schoolDbModel = favoriteSchoolDbModels.get(position);
        holder.bind(schoolDbModel);

        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                Realm.init(holder.itemView.getContext());
                Realm realm = Realm.getDefaultInstance();
                realm.executeTransaction(new Realm.Transaction() {
                    @Override
                    public void execute(Realm realm) {
                        schoolDbModel.deleteFromRealm();
                    }
                });
                removeAt(position);
                return false;
            }
        });
    }

    @Override
    public int getItemCount() {
        return favoriteSchoolDbModels.size();
    }

    public void removeAt(int position) {
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, favoriteSchoolDbModels.size());
    }
}
