package Matricula.Logic;

public class Course {

    private int id;
    private String code;
    private String name;
    private int credits;
    private int hours;
    private int careerId;

    public Course(int id, String code, String name, int credits, int hours, int careerId) {
        this.id = id;
        this.code = code;
        this.name = name;
        this.credits = credits;
        this.hours = hours;
        this.careerId = careerId;
    }

    public Course() {
        this(0, "", "", 0, 0, 0);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCredits() {
        return credits;
    }

    public void setCredits(int credits) {
        this.credits = credits;
    }

    public int getHours() {
        return hours;
    }

    public void setHours(int hours) {
        this.hours = hours;
    }

    public int getCareerId() {
        return careerId;
    }

    public void setCareerId(int careerId) {
        this.careerId = careerId;
    }

    @Override
    public String toString() {
        return "Course{" + "id=" + id + ", code=" + code + ", name=" + name + ", credits=" + credits + ", hours=" + hours + ", careerId=" + careerId + '}';
    }

}
