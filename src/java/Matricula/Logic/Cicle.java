
package Matricula.Logic;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.Locale;

public class Cicle {
    transient SimpleDateFormat formatter = new SimpleDateFormat("yyyy-mm-dd", Locale.ENGLISH);
    private int id;
    private int year;
    private int cicleNumber;
    private Date initDate;
    private Date finishDate;

    public Cicle(int id, int year, int cicleNumber, Date initDate, Date finishDate) {
        this.id = id;
        this.year = year;
        this.cicleNumber = cicleNumber;
        this.initDate = initDate;
        this.finishDate = finishDate;
    }

    public Cicle() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getCicleNumber() {
        return cicleNumber;
    }

    public void setCicleNumber(int cicleNumber) {
        this.cicleNumber = cicleNumber;
    }

    public Date getInitDate() {
        return initDate;
    }

    public void setInitDate(Date initDate) {
        this.initDate = initDate;
    }
    
    public void setInitDate(String initDate) {
        this.initDate = Date.valueOf(initDate);
    }

    public Date getFinishDate() {
        return finishDate;
    }

    public void setFinishDate(Date finishDate) {
        this.finishDate = finishDate;
    }
    
    public void setFinishDate(String finishDate) {
        this.finishDate = Date.valueOf(finishDate);
    }
    
    @Override
    public String toString() {
        return "Cicle{" + "id=" + id + ", year=" + year + ", cicleNumber=" + cicleNumber + ", initDate=" + initDate + ", finishDate=" + finishDate + '}';
    }
}
