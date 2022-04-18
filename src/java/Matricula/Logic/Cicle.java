
package Matricula.Logic;

import java.util.Date;


public class Cicle {
    
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

    @Override
    public String toString() {
        return "Cicle{" + "id=" + id + ", year=" + year + ", cicleNumber=" + cicleNumber + ", initDate=" + initDate + ", finishDate=" + finishDate + '}';
    }
}
