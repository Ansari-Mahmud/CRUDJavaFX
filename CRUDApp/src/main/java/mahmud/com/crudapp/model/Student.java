package mahmud.com.crudapp.model;

public class Student {
    private int id;
    private String fname;
    private String mname;
    private String lname;

    public Student() {

    }









    public Student(String fname, int id, String mname, String lname) {
        this.fname = fname;
        this.id = id;
        this.mname = mname;
        this.lname = lname;
    }

    public int getId() {
        return id;
    }

    public String getFname() {
        return fname;
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

    public void setLname(String lname) {
        this.lname = lname;
    }
}
