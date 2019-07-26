package com.example.avnis.ontime;

/**
 * Created by avnis on 7/26/2019.
 */
public class DetailType {
    private String time;
    private String status;
    private String date;
    public DetailType(String date,String time,String status)
    {
        this.date=date;
        this.status=status;
        this.time=time;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
