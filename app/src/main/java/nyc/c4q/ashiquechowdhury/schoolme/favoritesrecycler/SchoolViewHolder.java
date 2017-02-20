package nyc.c4q.ashiquechowdhury.schoolme.favoritesrecycler;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import nyc.c4q.ashiquechowdhury.schoolme.R;
import nyc.c4q.ashiquechowdhury.schoolme.models.SchoolDbModel;


/**
 * Created by ashiquechowdhury on 2/18/17.
 */
public class SchoolViewHolder extends RecyclerView.ViewHolder {
    private ImageView schoolPictureImage;
    private ImageView closeButton;

    public SchoolViewHolder(View itemView) {
        super(itemView);
        schoolPictureImage = (ImageView) itemView.findViewById(R.id.school_image);
    }

    public void bind(SchoolDbModel schoolDbModel) {
        schoolPictureImage.setImageResource(R.drawable.ic_home_black_24dp);
    }
}