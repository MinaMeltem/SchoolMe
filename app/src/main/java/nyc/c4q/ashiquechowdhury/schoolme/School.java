package nyc.c4q.ashiquechowdhury.schoolme;

import android.graphics.drawable.Drawable;

/**
 * Created by SACC on 2/18/17.
 */
public class School {

    public School(String schoolName) {
        this.schoolName = schoolName;
    }

    private String schoolName;
    private String schoolBorough;

    public String getSchoolName() {
        return schoolName;
    }

    public void setSchoolName(String schoolName) {
        this.schoolName = schoolName;
    }

    public String getSchoolBorough() {
        return schoolBorough;
    }

    public void setSchoolBorough(String schoolBorough) {
        this.schoolBorough = schoolBorough;
    }
}
