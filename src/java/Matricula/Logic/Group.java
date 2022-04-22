package Matricula.Logic;

public class Group {

    private int id;
    private int courseId;
    private int groupNumber;
    private String schedule;
    private int cicleId;
    private int professorId;

    public Group(int id, int courseId, int groupNumber, String schedule, int cicleId, int professorId) {
        this.id = id;
        this.courseId = courseId;
        this.groupNumber = groupNumber;
        this.schedule = schedule;
        this.cicleId = cicleId;
        this.professorId = professorId;
    }

    public Group() {
        this(0, 0, 0, "", 0, 0);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCourseId() {
        return courseId;
    }

    public void setCourseId(int courseId) {
        this.courseId = courseId;
    }

    public int getGroupNumber() {
        return groupNumber;
    }

    public void setGroupNumber(int groupNumber) {
        this.groupNumber = groupNumber;
    }

    public String getSchedule() {
        return schedule;
    }

    public void setSchedule(String schedule) {
        this.schedule = schedule;
    }

    public int getCicleId() {
        return cicleId;
    }

    public void setCicleId(int cicleId) {
        this.cicleId = cicleId;
    }
    
    

    public int getProfessorId() {
        return professorId;
    }

    public void setProfessorId(int professorId) {
        this.professorId = professorId;
    }

    @Override
    public String toString() {
        return "Group{" + "id=" + id + ", courseId=" + courseId + ", groupNumber=" + groupNumber + ", schedule=" + schedule + ", cicleId=" + cicleId + ", professorId=" + professorId + '}';
    }

   
}
