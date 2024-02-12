package com.example.collegescheduler.ui.gallery;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.collegescheduler.AssignmentDetails;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class AssignmentViewModel extends AndroidViewModel {

    private MutableLiveData<List<AssignmentDetails>> assignmentList;

    public AssignmentViewModel(Application application) {
        super(application);
        assignmentList = new MutableLiveData<>(new ArrayList<>());
        // Initialize or fetch data as needed
    }

    public LiveData<List<AssignmentDetails>> getAssignmentList() {
        return assignmentList;
    }

    public void addAssignment(AssignmentDetails assignmentDetails) {
        List<AssignmentDetails> currentList = new ArrayList<>(assignmentList.getValue());
        currentList.add(assignmentDetails);
        assignmentList.setValue(currentList);
        sortAssignmentsByDueDate();
    }

    public void updateAssignment(int position, AssignmentDetails updatedAssignment) {
        List<AssignmentDetails> currentList = new ArrayList<>(assignmentList.getValue());
        if (position >= 0 && position < currentList.size()) {
            currentList.set(position, updatedAssignment);
            assignmentList.setValue(currentList);
            sortAssignmentsByDueDate();
        }
    }

    public void removeAssignment(int position) {
        List<AssignmentDetails> currentList = new ArrayList<>(assignmentList.getValue());
        if (position >= 0 && position < currentList.size()) {
            currentList.remove(position);
            assignmentList.setValue(currentList);
            sortAssignmentsByDueDate();
        }
    }

    public void removeAllAssignments() {
        assignmentList.setValue(new ArrayList<>());
    }

    public void sortAssignmentsByDueDate() {
        List<AssignmentDetails> currentList = new ArrayList<>(assignmentList.getValue());
        Collections.sort(currentList, new Comparator<AssignmentDetails>() {
            @Override
            public int compare(AssignmentDetails assignment1, AssignmentDetails assignment2) {
                // Parse the due dates and compare them
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
                try {
                    Date date1 = sdf.parse(assignment1.getDueDate());
                    Date date2 = sdf.parse(assignment2.getDueDate());
                    return date1.compareTo(date2);
                } catch (ParseException e) {
                    e.printStackTrace();
                    return 0;
                }
            }
        });
        assignmentList.setValue(currentList);
    }
}
