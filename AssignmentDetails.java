package com.example.collegescheduler;

import android.os.Parcel;
import android.os.Parcelable;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcel;
import android.os.Parcelable;

public class AssignmentDetails implements Parcelable {
    private String title;
    private String courseName;
    private String dueDate;

    public AssignmentDetails(String title, String courseName, String dueDate) {
        this.title = title;
        this.courseName = courseName;
        this.dueDate = dueDate;
    }

    protected AssignmentDetails(Parcel in) {
        title = in.readString();
        courseName = in.readString();
        dueDate = in.readString();
    }

    public static final Creator<AssignmentDetails> CREATOR = new Creator<AssignmentDetails>() {
        @Override
        public AssignmentDetails createFromParcel(Parcel in) {
            return new AssignmentDetails(in);
        }

        @Override
        public AssignmentDetails[] newArray(int size) {
            return new AssignmentDetails[size];
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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(title);
        dest.writeString(courseName);
        dest.writeString(dueDate);
    }
}
