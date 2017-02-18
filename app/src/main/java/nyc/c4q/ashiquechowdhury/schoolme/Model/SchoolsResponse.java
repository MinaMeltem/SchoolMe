package nyc.c4q.ashiquechowdhury.schoolme.Model;

import java.util.List;

/**
 * Created by meltemyildirim on 2/18/17.
 */

public class SchoolsResponse {

    private List<School> schoolList;

    public SchoolsResponse() {
    }

    public SchoolsResponse(List<School> schoolList) {
        this.schoolList = schoolList;
    }

    public List<School> getSchoolList() {
        return schoolList;
    }

    public void setSchoolList(List<School> schoolList) {
        this.schoolList = schoolList;
    }
}
