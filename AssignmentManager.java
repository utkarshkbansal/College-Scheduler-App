package com.example.collegescheduler;

import java.util.ArrayList;
import java.util.List;


import java.util.ArrayList;
import java.util.List;

import java.util.ArrayList;
import java.util.List;

public class AssignmentManager {

    private static List<AssignmentDetails> assignmentList = new ArrayList<>();

    public static List<AssignmentDetails> getAssignmentList() {
        return assignmentList;
    }

    public static void addAssignment(AssignmentDetails assignmentDetails) {
        assignmentList.add(assignmentDetails);
    }

    public static void removeAssignment(int position) {
        if (position >= 0 && position < assignmentList.size()) {
            assignmentList.remove(position);
        }
    }

    public static void updateAssignment(int position, AssignmentDetails updatedAssignment) {
        if (assignmentList != null && position >= 0 && position < assignmentList.size()) {
            assignmentList.set(position, updatedAssignment);
        }
    }
}
