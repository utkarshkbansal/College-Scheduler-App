package com.example.collegescheduler.ui.home;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.collegescheduler.ClassDetails;

import java.util.ArrayList;
import java.util.List;
public class ClassViewModel extends AndroidViewModel {

    private MutableLiveData<List<ClassDetails>> classList;

    public ClassViewModel(Application application) {
        super(application);
        classList = new MutableLiveData<>(new ArrayList<>());
        // Initialize or fetch data as needed
    }

    public LiveData<List<ClassDetails>> getClassList() {
        return classList;
    }

    public void addClass(ClassDetails classDetails) {
        List<ClassDetails> currentList = new ArrayList<>(classList.getValue());
        currentList.add(classDetails);
        classList.setValue(currentList);
    }

    public void updateClass(int position, ClassDetails updatedClass) {
        List<ClassDetails> currentList = new ArrayList<>(classList.getValue());
        if (position >= 0 && position < currentList.size()) {
            currentList.set(position, updatedClass);
            classList.setValue(currentList);
        }
    }

    public void removeClass(int position) {
        List<ClassDetails> currentList = new ArrayList<>(classList.getValue());
        if (position >= 0 && position < currentList.size()) {
            currentList.remove(position);
            classList.setValue(currentList);
        }
    }

    public void removeAllClasses() {
        classList.setValue(new ArrayList<>());
    }
}
