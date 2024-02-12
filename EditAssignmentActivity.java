package com.example.collegescheduler.ui.gallery;

import androidx.appcompat.app.AppCompatActivity;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import androidx.lifecycle.ViewModelProvider;


import com.example.collegescheduler.AssignmentDetails;
import com.example.collegescheduler.R;

public class EditAssignmentActivity extends AppCompatActivity {

    private EditText titleEditText;
    private DatePicker datePicker;
    private EditText courseEditText;
    private AssignmentViewModel assignmentViewModel;
    private int position = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_assignment);

        titleEditText = findViewById(R.id.editTextTitle);
        datePicker = findViewById(R.id.datePicker);
        courseEditText = findViewById(R.id.editTextCourse);
        assignmentViewModel = new ViewModelProvider(this).get(AssignmentViewModel.class);

        Button saveButton = findViewById(R.id.saveAssignmentButton);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveAssignmentDetails();
            }
        });

        Intent intent = getIntent();
        if (intent.hasExtra("assignmentDetails")) {
            AssignmentDetails existingAssignment = intent.getParcelableExtra("assignmentDetails");
            position = intent.getIntExtra("position", -1);
            populateFields(existingAssignment);
        }
    }

    private void populateFields(AssignmentDetails existingAssignment) {
        titleEditText.setText(existingAssignment.getTitle());

        // Split the due date into year, month, and day
        String[] dueDateParts = existingAssignment.getDueDate().split("-");
        int year = Integer.parseInt(dueDateParts[0]);
        int month = Integer.parseInt(dueDateParts[1]) - 1; // Month is zero-based
        int day = Integer.parseInt(dueDateParts[2]);

        datePicker.updateDate(year, month, day);

        courseEditText.setText(existingAssignment.getCourseName());
    }

    public void saveAssignmentDetails() {
        String title = titleEditText.getText().toString();

        // Extract year, month, and day from DatePicker
        int year = datePicker.getYear();
        int month = datePicker.getMonth() + 1; // Month is zero-based
        int day = datePicker.getDayOfMonth();

        String dueDate = String.format("%04d-%02d-%02d", year, month, day);
        String courseName = courseEditText.getText().toString();

        AssignmentDetails assignmentDetails = new AssignmentDetails(title, courseName, dueDate);

        Intent resultIntent = new Intent();
        resultIntent.putExtra("assignmentDetails", assignmentDetails);
        resultIntent.putExtra("position", position);
        setResult(Activity.RESULT_OK, resultIntent);
        finish();
    }
}
