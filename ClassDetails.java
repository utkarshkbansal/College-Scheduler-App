package com.example.collegescheduler;

import android.os.Parcel;
import android.os.Parcelable;

import android.os.Parcel;
import android.os.Parcelable;

public class ClassDetails implements Parcelable {
    private String courseName;
    private String day;
    private int hour;
    private int minute;
    private String time;
    private String instructor;

    public ClassDetails(String courseName, String day, int hour, int minute, String instructor) {
        this.courseName = courseName;
        this.day = day;
        this.hour = hour;
        this.minute = minute;
        this.instructor = instructor;
        setTime();
    }

    protected ClassDetails(Parcel in) {
        courseName = in.readString();
        day = in.readString();
        hour = in.readInt();
        minute = in.readInt();
        instructor = in.readString();
        time = in.readString();
    }

    public static final Creator<ClassDetails> CREATOR = new Creator<ClassDetails>() {
        @Override
        public ClassDetails createFromParcel(Parcel in) {
            return new ClassDetails(in);
        }

        @Override
        public ClassDetails[] newArray(int size) {
            return new ClassDetails[size];
        }
    };

    public String getCourseName() {
        return courseName;
    }

    public String getDay() {
        return day;
    }

    public int getHour() {
        return hour;
    }

    public int getMinute() {
        return minute;
    }

    public String getInstructor() {
        return instructor;
    }

    public String getTime() {
        return time;
    }

    public void setTime() {
        this.time = String.format("%02d:%02d", hour, minute);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(courseName);
        dest.writeString(day);
        dest.writeInt(hour);
        dest.writeInt(minute);
        dest.writeString(instructor);
        dest.writeString(time);
    }
}
