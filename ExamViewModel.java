package com.example.collegescheduler.ui.exams;

import android.app.Application;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import com.example.collegescheduler.ExamDetails;

import java.util.ArrayList;
import java.util.List;

public class ExamViewModel extends AndroidViewModel {

    private MutableLiveData<List<ExamDetails>> examList;

    public ExamViewModel(Application application) {
        super(application);
        examList = new MutableLiveData<>(new ArrayList<>());
        // Initialize or fetch data as needed
    }

    public LiveData<List<ExamDetails>> getExamList() {
        return examList;
    }

    public void addExam(ExamDetails examDetails) {
        List<ExamDetails> currentList = new ArrayList<>(examList.getValue());
        currentList.add(examDetails);
        examList.setValue(currentList);
    }

    public void updateExam(int position, ExamDetails updatedExam) {
        List<ExamDetails> currentList = new ArrayList<>(examList.getValue());
        if (position >= 0 && position < currentList.size()) {
            currentList.set(position, updatedExam);
            examList.setValue(currentList);
        }
    }

    public void removeExam(int position) {
        List<ExamDetails> currentList = new ArrayList<>(examList.getValue());
        if (position >= 0 && position < currentList.size()) {
            currentList.remove(position);
            examList.setValue(currentList);
        }
    }

    public void removeAllExams() {
        examList.setValue(new ArrayList<>());
    }
}
