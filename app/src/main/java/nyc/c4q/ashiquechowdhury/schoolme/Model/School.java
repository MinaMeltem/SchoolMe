package nyc.c4q.ashiquechowdhury.schoolme.Model;

/**
 * Created by meltemyildirim on 2/18/17.
 */

public class School {
    private String school_name;
    private String school_type;
    private String addtl_info1;
    private String advance_placement_courses;
    private String start_time;
    private String end_time;
    private String program_highlights;
    private String sport_boys;
    private String sport_girls;
    private String language_classes;
    private String extra_activities;
    private String grade_span_min;
    private String grade_span_max;
    private String total_students;
    private String partner_hospital;
    private String primary_address_line_1;
    private String zip;
    private String boro;
    private String phone;
    private String email;
    private String website;
    private String ell_program;
    private String bus;
    private String subway;

    //Constructors

    public School() {
    }

    public School(String school_name, String school_type, String addtl_info1, String advance_placement_courses, String start_time, String end_time, String program_highlights, String sport_boys, String sport_girls, String language_classes, String extra_activities, String grade_span_min, String grade_span_max, String total_students, String partner_hospital, String primary_address_line_1, String zip, String boro, String phone, String email, String website, String ell_program, String bus, String subway) {
        this.school_name = school_name;
        this.school_type = school_type;
        this.addtl_info1 = addtl_info1;
        this.advance_placement_courses = advance_placement_courses;
        this.start_time = start_time;
        this.end_time = end_time;
        this.program_highlights = program_highlights;
        this.sport_boys = sport_boys;
        this.sport_girls = sport_girls;
        this.language_classes = language_classes;
        this.extra_activities = extra_activities;
        this.grade_span_min = grade_span_min;
        this.grade_span_max = grade_span_max;
        this.total_students = total_students;
        this.partner_hospital = partner_hospital;
        this.primary_address_line_1 = primary_address_line_1;
        this.zip = zip;
        this.boro = boro;
        this.phone = phone;
        this.email = email;
        this.website = website;
        this.ell_program = ell_program;
        this.bus = bus;
        this.subway = subway;
    }

    //Getters

    public String getSchool_name() {
        return school_name;
    }

    public String getSchool_type() {
        return school_type;
    }

    public String getAddtl_info1() {
        return addtl_info1;
    }

    public String getAdvance_placement_courses() {
        return advance_placement_courses;
    }

    public String getStart_time() {
        return start_time;
    }

    public String getEnd_time() {
        return end_time;
    }

    public String getProgram_highlights() {
        return program_highlights;
    }

    public String getSport_boys() {
        return sport_boys;
    }

    public String getSport_girls() {
        return sport_girls;
    }

    public String getLanguage_classes() {
        return language_classes;
    }

    public String getExtra_activities() {
        return extra_activities;
    }

    public String getGrade_span_min() {
        return grade_span_min;
    }

    public String getGrade_span_max() {
        return grade_span_max;
    }

    public String getTotal_students() {
        return total_students;
    }

    public String getPartner_hospital() {
        return partner_hospital;
    }

    public String getPrimary_address_line_1() {
        return primary_address_line_1;
    }

    public String getZip() {
        return zip;
    }

    public String getBoro() {
        return boro;
    }

    public String getPhone() {
        return phone;
    }

    public String getEmail() {
        return email;
    }

    public String getWebsite() {
        return website;
    }

    public String getEll_program() {
        return ell_program;
    }

    public String getBus() {
        return bus;
    }

    public String getSubway() {
        return subway;
    }

    //Setters
    public void setSchool_name(String school_name) {
        this.school_name = school_name;
    }

    public void setSchool_type(String school_type) {
        this.school_type = school_type;
    }

    public void setAddtl_info1(String addtl_info1) {
        this.addtl_info1 = addtl_info1;
    }

    public void setAdvance_placement_courses(String advance_placement_courses) {
        this.advance_placement_courses = advance_placement_courses;
    }

    public void setStart_time(String start_time) {
        this.start_time = start_time;
    }

    public void setEnd_time(String end_time) {
        this.end_time = end_time;
    }

    public void setProgram_highlights(String program_highlights) {
        this.program_highlights = program_highlights;
    }

    public void setSport_boys(String sport_boys) {
        this.sport_boys = sport_boys;
    }

    public void setSport_girls(String sport_girls) {
        this.sport_girls = sport_girls;
    }

    public void setLanguage_classes(String language_classes) {
        this.language_classes = language_classes;
    }

    public void setExtra_activities(String extra_activities) {
        this.extra_activities = extra_activities;
    }

    public void setGrade_span_min(String grade_span_min) {
        this.grade_span_min = grade_span_min;
    }

    public void setGrade_span_max(String grade_span_max) {
        this.grade_span_max = grade_span_max;
    }

    public void setTotal_students(String total_students) {
        this.total_students = total_students;
    }

    public void setPartner_hospital(String partner_hospital) {
        this.partner_hospital = partner_hospital;
    }

    public void setPrimary_address_line_1(String primary_address_line_1) {
        this.primary_address_line_1 = primary_address_line_1;
    }

    public void setZip(String zip) {
        this.zip = zip;
    }

    public void setBoro(String boro) {
        this.boro = boro;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public void setEll_program(String ell_program) {
        this.ell_program = ell_program;
    }

    public void setBus(String bus) {
        this.bus = bus;
    }

    public void setSubway(String subway) {
        this.subway = subway;
    }
}

