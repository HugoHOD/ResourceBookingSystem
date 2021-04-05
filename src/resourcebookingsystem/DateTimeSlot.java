package resourcebookingsystem;

public class DateTimeSlot {
    private String date;
    private String time;

    public DateTimeSlot(String date, String time) {
        this.date = date;
        this.time = time;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String toString() {
        return this.date + " " + this.time;
    }
    



}
