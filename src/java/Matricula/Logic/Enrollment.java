package Matricula.Logic;

public class Enrollment {

    private int id;
    private Group group;
    private User user;
    private int grade;

    public Enrollment(int id, Group groupId, User userId, int grade) {
        this.id = id;
        this.group = groupId;
        this.user = userId;
        this.grade = grade;
    }

    public Enrollment() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Group getGroup() {
        return group;
    }

    public void setGroup(Group group) {
        this.group = group;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public int getGrade() {
        return grade;
    }

    public void setGrade(int grade) {
        this.grade = grade;
    }

    @Override
    public String toString() {
        return "Enrollment{" + "id=" + id + ", groupId=" + group + ", userId=" + user + ", grade=" + grade + '}';
    }

    

}
