package com.example.collegescheduler;

import android.os.Parcel;
import android.os.Parcelable;

public class ExamDetails implements Parcelable {
    private String title;
    private String courseName;
    private String dueDate;
    private int hour;
    private int minute;
    private String time;
    private String location;

    public ExamDetails(String title, String courseName, String dueDate, int hour, int minute) {
        this.title = title;
        this.courseName = courseName;
        this.dueDate = dueDate;
        this.hour = hour;
        this.minute = minute;
        this.location = location;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    protected ExamDetails(Parcel in) {
        title = in.readString();
        courseName = in.readString();
        dueDate = in.readString();
        hour = in.readInt();
        minute = in.readInt();
        time = in.readString();
    }

    public static final Creator<ExamDetails> CREATOR = new Creator<ExamDetails>() {
        @Override
        public ExamDetails createFromParcel(Parcel in) {
            return new ExamDetails(in);
        }

        @Override
        public ExamDetails[] newArray(int size) {
            return new ExamDetails[size];
        }
    };

    public String getTitle() {
        return title;
    }

    public String getCourseName() {
        return courseName;
    }

    public String getDueDate() {
        return dueDate;
    }

    public int getHour() {
        return hour;
    }

    public int getMinute() {
        return minute;
    }

    public void setTime() {
        this.time = String.format("%02d:%02d", hour, minute);
    }

    public String getTime() {
        return time;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(title);
        dest.writeString(courseName);
        dest.writeString(dueDate);
        dest.writeInt(hour);
        dest.writeInt(minute);
        dest.writeString(time);
    }
}
