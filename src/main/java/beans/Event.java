package beans;

import java.sql.Time;
import java.text.DateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Timer;
import java.util.concurrent.TimeUnit;

public class Event {

    public Event(Date date, DateFormat dateFormat) {
        this.date = date;
        this.dateFormat = dateFormat;
//        this.id
    }

    private int id = (int) (Math.random() * 100);

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    private String msg;
    private Date date;
    private DateFormat dateFormat;

    public String toString() {
        return "id: " + this.id + ", Date: " + this.dateFormat.format(this.date) + ", Messege: " + this.msg;
    }

    public static boolean isDay(){
        Date date = new Date();
        Calendar calendar = Calendar.getInstance();
        Time time = new Time(date.getTime());
        if(date.after(new Date(calendar.get(Calendar.YEAR) - 1900, calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH), 8, 0, 0))
                && date.before(new Date(calendar.get(Calendar.YEAR) - 1900, calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH), 17, 0, 0))){
            return true;
        }else {
            return false;
        }
    }
}
