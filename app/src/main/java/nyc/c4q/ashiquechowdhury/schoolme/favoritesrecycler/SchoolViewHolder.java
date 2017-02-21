package nyc.c4q.ashiquechowdhury.schoolme.favoritesrecycler;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import it.sephiroth.android.library.picasso.Picasso;
import nyc.c4q.ashiquechowdhury.schoolme.R;
import nyc.c4q.ashiquechowdhury.schoolme.models.SchoolDbModel;


/**
 * Created by ashiquechowdhury on 2/18/17.
 */
public class SchoolViewHolder extends RecyclerView.ViewHolder {
    private ImageView schoolPictureImage;
    private ImageView closeButton;
    private TextView schoolNameTextView;

    public SchoolViewHolder(View itemView) {
        super(itemView);
        schoolPictureImage = (ImageView) itemView.findViewById(R.id.school_image);
        schoolNameTextView = (TextView) itemView.findViewById(R.id.school_TV);
    }

    public void bind(final SchoolDbModel schoolDbModel) {
        Picasso.with(itemView.getContext()).load(schoolDbModel.getPictureURL()).fit().into(schoolPictureImage);
        schoolNameTextView.setText(schoolDbModel.getSchoolName());
    }
}