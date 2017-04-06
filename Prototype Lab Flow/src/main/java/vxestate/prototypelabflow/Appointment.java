package vxestate.prototypelabflow;

/**
 * Created by Kristina on 2017-04-06.
 */

public class Appointment {
    private String Date, Time;

    public Appointment(String Date, String Time){
        this.setDate(Date);
        this.setTime(Time);
    }

    public String getTime() {
        return Time;
    }

    public void setTime(String time) {
        Time = time;
    }

    public String getDate() {
        return Date;
    }

    public void setDate(String date) {
        Date = date;
    }
}
