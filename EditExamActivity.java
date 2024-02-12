package com.example.collegescheduler.ui.exams;

import androidx.appcompat.app.AppCompatActivity;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;
import androidx.lifecycle.ViewModelProvider;

import com.example.collegescheduler.ExamDetails;
import com.example.collegescheduler.R;

public class EditExamActivity extends AppCompatActivity {

    private EditText titleEditText;
    private DatePicker datePicker;
    private TimePicker timePicker;
    private EditText locationEditText;

    private ExamViewModel examViewModel;
    private int position = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_exam);

        titleEditText = findViewById(R.id.editTextTitleExam);
        locationEditText = findViewById(R.id.editTextLocation);
        datePicker = findViewById(R.id.datePickerExam);
        timePicker = findViewById(R.id.timePickerExam);
        examViewModel = new ViewModelProvider(this).get(ExamViewModel.class);


        Button saveButton = findViewById(R.id.saveExamButton);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveExamDetails();
            }
        });

        Intent intent = getIntent();
        if (intent.hasExtra("examDetails")) {
            ExamDetails existingExam = intent.getParcelableExtra("examDetails");
            position = intent.getIntExtra("position", -1);
            populateFields(existingExam);
        }
    }

    private void populateFields(ExamDetails existingExam) {
        titleEditText.setText(existingExam.getTitle());

        // Split the due date into year, month, and day
        String[] dueDateParts = existingExam.getDueDate().split("-");
        int year = Integer.parseInt(dueDateParts[0]);
        int month = Integer.parseInt(dueDateParts[1]) - 1; // Month is zero-based
        int day = Integer.parseInt(dueDateParts[2]);

        datePicker.updateDate(year, month, day);

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
            timePicker.setHour(existingExam.getHour());
            timePicker.setMinute(existingExam.getMinute());
        } else {
            timePicker.setCurrentHour(existingExam.getHour());
            timePicker.setCurrentMinute(existingExam.getMinute());
        }

        locationEditText.setText(existingExam.getLocation());

    }

    private void saveExamDetails() {
        String title = titleEditText.getText().toString();

        // Extract year, month, and day from DatePicker
        int year = datePicker.getYear();
        int month = datePicker.getMonth() + 1; // Month is zero-based
        int day = datePicker.getDayOfMonth();
        String dueDate = String.format("%04d-%02d-%02d", year, month, day);

        String location = locationEditText.getText().toString();

        int hour;
        int minute;

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
            hour = timePicker.getHour();
            minute = timePicker.getMinute();
        } else {
            hour = timePicker.getCurrentHour();
            minute = timePicker.getCurrentMinute();
        }

        String courseName = "";

        ExamDetails examDetails = new ExamDetails(title, courseName, dueDate, hour, minute);
        examDetails.setTime();
        examDetails.setLocation(location);


        Intent resultIntent = new Intent();
        resultIntent.putExtra("examDetails", examDetails);
        resultIntent.putExtra("position", position);
        setResult(Activity.RESULT_OK, resultIntent);
        finish();
    }
}
