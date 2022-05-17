package com.hosein.nzd.students;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;

public class AddNewStudentActivity extends AppCompatActivity {

    ExtendedFloatingActionButton saveStudentFab;
    TextInputEditText firstName , lastName , course , score;
    private static final String TAG = "AddNewStudentActivity";
    ApiService apiService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_student);

        apiService = new ApiService();

        firstName = findViewById(R.id.firstName_edt);
        lastName = findViewById(R.id.lastName_edt);
        course = findViewById(R.id.course_edt);
        score = findViewById(R.id.score_edt);

        Toolbar toolbar = findViewById(R.id.toolbar_addNewStudent);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_back_white_24dp);

        saveStudentFab = findViewById(R.id.saveStudentFab);
        saveStudentFab.setOnClickListener(view -> {

            if (firstName.length() > 0 && lastName.length() > 0 && course.length() > 0 && score.length() > 0) {

                apiService.postStudentInformation(firstName.getText().toString(),
                        lastName.getText().toString(), course.getText().toString(),
                        score.getText().toString(), new ApiService.getStudentAdded() {
                            @Override
                            public void onSuccess(Student student) {
                                Intent intent = new Intent();
                                intent.putExtra("studentObject" , student);
                                setResult(Activity.RESULT_OK , intent);
                                finish();
                            }

                            @Override
                            public void onError(Exception error) {
                                Toast.makeText(AddNewStudentActivity.this, "خطای نامشخص!", Toast.LENGTH_LONG).show();
                            }
                        });

            }else {
                Toast.makeText(this, " items is empty ! ", Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return super.onSupportNavigateUp();
    }
}