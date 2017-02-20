package nyc.c4q.ashiquechowdhury.schoolme.models;

import io.realm.RealmObject;

public class SchoolDbModel extends RealmObject {
    private String pictureURL;
    private String schoolName;


    public String getSchoolName() {
        return schoolName;
    }

    public String getPictureURL() {
        return pictureURL;
    }

    public void setPictureURL(String pictureURL) {
        this.pictureURL = pictureURL;
    }

    public void setSchoolName(String schoolName) {
        this.schoolName = schoolName;
    }
}