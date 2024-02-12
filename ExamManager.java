package com.example.collegescheduler;

import java.util.ArrayList;
import java.util.List;

public class ExamManager {

    private static List<ExamDetails> examList = new ArrayList<>();

    public static List<ExamDetails> getExamList() {
        return examList;
    }

    public static void addExam(ExamDetails examDetails) {
        examList.add(examDetails);
    }

    public static void removeExam(int position) {
        if (position >= 0 && position < examList.size()) {
            examList.remove(position);
        }
    }

    public static void updateExam(int position, ExamDetails updatedExam) {
        if (examList != null && position >= 0 && position < examList.size()) {
            examList.set(position, updatedExam);
        }
    }
}
