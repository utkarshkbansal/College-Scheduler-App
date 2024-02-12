package com.example.collegescheduler.ui.home;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import com.example.collegescheduler.ClassAdapter;
import com.example.collegescheduler.ClassDetails;
import com.example.collegescheduler.ClassManager;
import com.example.collegescheduler.ui.home.ClassViewModel;
import com.example.collegescheduler.R;
import com.example.collegescheduler.ui.home.EditClassActivity;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {

    private ClassViewModel classViewModel;
    private ClassAdapter classAdapter;
    private static final int ADD_CLASS_REQUEST_CODE = 1;
    private static final int EDIT_CLASS_REQUEST_CODE = 2;
    private static final String CLASS_DETAILS_KEY = "classDetailsList";

    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        ListView classListView = view.findViewById(R.id.classListView);
        Button addButton = view.findViewById(R.id.addButton);

        classAdapter = new ClassAdapter(getActivity(), new ArrayList<>());
        classListView.setAdapter(classAdapter);

        classViewModel = new ViewModelProvider(this).get(ClassViewModel.class);
        classViewModel.getClassList().observe(getViewLifecycleOwner(), new Observer<List<ClassDetails>>() {
            @Override
            public void onChanged(List<ClassDetails> classes) {
                classAdapter.setClassList(classes);
                classAdapter.notifyDataSetChanged();
            }
        });

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showAddClassDialog();
            }
        });

        classListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                showEditDeleteDialog(position);
            }
        });

        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == ADD_CLASS_REQUEST_CODE) {
                if (data != null) {
                    ClassDetails newClass = data.getParcelableExtra("classDetails");
                    if (newClass != null) {
                        classViewModel.addClass(newClass);
                        Toast.makeText(getActivity(), "Class added successfully", Toast.LENGTH_SHORT).show();
                    }
                }
            } else if (requestCode == EDIT_CLASS_REQUEST_CODE) {
                if (data != null) {
                    ClassDetails updatedClass = data.getParcelableExtra("classDetails");
                    int position = data.getIntExtra("position", -1);
                    if (updatedClass != null && position != -1) {
                        classViewModel.updateClass(position, updatedClass);
                        Toast.makeText(getActivity(), "Class updated successfully", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        }
    }

    private void showEditDeleteDialog(final int position) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Edit or Delete");

        builder.setPositiveButton("Edit", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (position >= 0 && position < classAdapter.getCount()) {
                    Intent intent = new Intent(getActivity(), EditClassActivity.class);
                    intent.putExtra("classDetails", classAdapter.getItem(position));
                    intent.putExtra("position", position);
                    startActivityForResult(intent, EDIT_CLASS_REQUEST_CODE);
                } else {
                    Toast.makeText(getActivity(), "Invalid position for Edit", Toast.LENGTH_SHORT).show();
                }
            }
        });

        builder.setNegativeButton("Delete", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (position >= 0 && position < classAdapter.getCount()) {
                    classViewModel.removeClass(position); // Use classViewModel for removal
                    Toast.makeText(getActivity(), "Class deleted successfully", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getActivity(), "Invalid position for Delete", Toast.LENGTH_SHORT).show();
                }
            }
        });

        builder.show();
    }

    private void showAddClassDialog() {
        Intent intent = new Intent(getActivity(), EditClassActivity.class);
        startActivityForResult(intent, ADD_CLASS_REQUEST_CODE);
    }





    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putParcelableArrayList(CLASS_DETAILS_KEY, new ArrayList<>(ClassManager.getClassList()));
        super.onSaveInstanceState(outState);
    }
}
