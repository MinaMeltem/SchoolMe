package nyc.c4q.ashiquechowdhury.schoolme.model;

import android.graphics.drawable.Drawable;
import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by SACC on 2/18/17.
 */
public class School implements Parcelable {

    public School(String schoolName) {
        this.schoolName = schoolName;
    }

    private String schoolName;
    private String schoolBorough;

    protected School(Parcel in) {
        schoolName = in.readString();
        schoolBorough = in.readString();
    }

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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(schoolName);
        parcel.writeString(schoolBorough);
    }

    public static final Creator<School> CREATOR = new Creator<School>() {
        @Override
        public School createFromParcel(Parcel in) {
            return new School(in);
        }

        @Override
        public School[] newArray(int size) {
            return new School[size];
        }
    };
}
