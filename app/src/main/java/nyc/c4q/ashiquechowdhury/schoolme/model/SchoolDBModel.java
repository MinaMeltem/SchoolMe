package nyc.c4q.ashiquechowdhury.schoolme.model;

import io.realm.RealmObject;

/**
 * Created by ashiquechowdhury on 2/18/17.
 */

public class SchoolDBModel extends RealmObject {
    private String pictureURL;
    private String schoolName;

    public SchoolDBModel(String schoolName, String pictureURL) {
        this.schoolName = schoolName;
        this.pictureURL = pictureURL;
    }

    public String getSchoolName() {
        return schoolName;
    }

    public String getPictureURL() {
        return pictureURL;
    }
}