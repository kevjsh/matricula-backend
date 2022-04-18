package Matricula.Logic;

public class Career {

    private int id;
    private String code;
    private String name;
    private String title;

    public Career(int id, String code, String name, String title) {
        this.id = id;
        this.code = code;
        this.name = name;
        this.title = title;
    }

    public Career() {
        this(0, "", "", "");
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

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public String toString() {
        return "Career{" + "id=" + id + ", code=" + code + ", name=" + name + ", title=" + title + '}';
    }

}
