package com.example.collegescheduler.ui.home;

import androidx.appcompat.app.AppCompatActivity;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TimePicker;
import androidx.lifecycle.ViewModelProvider;
import com.example.collegescheduler.ClassDetails;
import com.example.collegescheduler.ui.home.ClassViewModel;
import com.example.collegescheduler.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TimePicker;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import com.example.collegescheduler.ClassDetails;
import com.example.collegescheduler.ui.home.ClassViewModel;
import com.example.collegescheduler.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TimePicker;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

public class EditClassActivity extends AppCompatActivity {

    private EditText courseNameEditText;
    private Spinner daySpinner;
    private TimePicker timePicker;
    private EditText instructorEditText;
    private ClassViewModel classViewModel;
    private int position = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_class);

        courseNameEditText = findViewById(R.id.editTextCourseName);
        daySpinner = findViewById(R.id.spinnerDay);
        timePicker = findViewById(R.id.timePicker);
        instructorEditText = findViewById(R.id.editTextInstructor);
        classViewModel = new ViewModelProvider(this).get(ClassViewModel.class);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.days_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        daySpinner.setAdapter(adapter);

        Button saveButton = findViewById(R.id.saveButton);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveClassDetails();
            }
        });

        Intent intent = getIntent();
        if (intent.hasExtra("classDetails")) {
            ClassDetails existingClass = intent.getParcelableExtra("classDetails");
            position = intent.getIntExtra("position", -1);
            populateFields(existingClass);
        }
    }

    private void populateFields(ClassDetails existingClass) {
        courseNameEditText.setText(existingClass.getCourseName());
        daySpinner.setSelection(((ArrayAdapter<String>) daySpinner.getAdapter()).getPosition(existingClass.getDay()));

        // Set the time for the TimePicker
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
            timePicker.setHour(existingClass.getHour());
            timePicker.setMinute(existingClass.getMinute());
        } else {
            timePicker.setCurrentHour(existingClass.getHour());
            timePicker.setCurrentMinute(existingClass.getMinute());
        }

        instructorEditText.setText(existingClass.getInstructor());
    }

    private void saveClassDetails() {
        String courseName = courseNameEditText.getText().toString();
        String day = daySpinner.getSelectedItem().toString();
        int hour;
        int minute;

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
            hour = timePicker.getHour();
            minute = timePicker.getMinute();
        } else {
            hour = timePicker.getCurrentHour();
            minute = timePicker.getCurrentMinute();
        }

        String instructor = instructorEditText.getText().toString();

        ClassDetails classDetails = new ClassDetails(courseName, day, hour, minute, instructor);
        classDetails.setTime();

        Intent resultIntent = new Intent();
        resultIntent.putExtra("classDetails", classDetails);
        resultIntent.putExtra("position", position);
        setResult(Activity.RESULT_OK, resultIntent);
        finish();
    }
}
