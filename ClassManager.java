// ClassManager.java
package com.example.collegescheduler;

import java.util.ArrayList;
import java.util.List;

public class ClassManager {

    private static List<ClassDetails> classList = new ArrayList<>();

    public static List<ClassDetails> getClassList() {
        return classList;
    }

    public static void addClass(ClassDetails classDetails) {
        classList.add(classDetails);
    }

    public static void removeClass(int position) {
        if (position >= 0 && position < classList.size()) {
            classList.remove(position);
        }
    }

    public static void updateClass(int position, ClassDetails updatedClass) {
        if (classList != null && position >= 0 && position < classList.size()) {
            classList.set(position, updatedClass);
        }
    }


}
