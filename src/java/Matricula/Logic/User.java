package Matricula.Logic;

import java.sql.Date;

public class User {

    private int id;
    private String personId;
    private String name;
    private int telephone;
    private Date birthday;
    private int careerId;
    private int roleId;
    private String email;
    private String password;

    public User(int id, String personId, String name, int telephone, Date birthday, int careerId, int roleId, String email, String password) {
        this.id = id;
        this.personId = personId;
        this.name = name;
        this.telephone = telephone;
        this.birthday = birthday;
        this.careerId = careerId;
        this.roleId = roleId;
        this.email = email;
        this.password = password;
    }

    public User() {
        this(0, "", "", 0, null, 0, 0, "", "");
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPersonId() {
        return personId;
    }

    public void setPersonId(String personId) {
        this.personId = personId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getTelephone() {
        return telephone;
    }

    public void setTelephone(int telephone) {
        this.telephone = telephone;
    }

    public Date getBithday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = Date.valueOf(birthday);
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public int getCareerId() {
        return careerId;
    }

    public void setCareerId(int careerId) {
        this.careerId = careerId;
    }

    public int getRoleId() {
        return roleId;
    }

    public void setRoleId(int roleId) {
        this.roleId = roleId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("User{id=").append(id);
        sb.append(", personId=").append(personId);
        sb.append(", name=").append(name);
        sb.append(", telephone=").append(telephone);
        sb.append(", birthday=").append(birthday);
        sb.append(", careerId=").append(careerId);
        sb.append(", roleId=").append(roleId);
        sb.append(", email=").append(email);
        sb.append(", password=").append(password);
        sb.append('}');
        return sb.toString();
    }

}
