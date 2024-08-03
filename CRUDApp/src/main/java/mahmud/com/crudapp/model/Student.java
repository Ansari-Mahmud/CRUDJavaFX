package mahmud.com.crudapp.model;

public class Student {
    private int id;
    private String fname;
    private String mname;
    private String lname;
    private String course;
    private String gender;

    public Student() {

    }
    public Student(String fname, int id, String mname, String lname,String course, String gender) {
        this.fname = fname;
        this.id = id;
        this.mname = mname;
        this.lname = lname;
        this.course = course;
        this.gender = gender;
    }

    public int getId() {
        return id;
    }

    public String getFname() {
        return fname;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getMname() {
        return mname;
    }

    public String getLname() {
        return lname;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setFname(String fname) {
        this.fname = fname;
    }

    public void setMname(String mname) {
        this.mname = mname;
    }

    public String getCourse() {
        return course;
    }

    public void setCourse(String course) {
        this.course = course;
    }

    public void setLname(String lname) {
        this.lname = lname;
    }
}
