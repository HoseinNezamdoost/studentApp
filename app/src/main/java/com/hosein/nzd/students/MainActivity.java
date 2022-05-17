package com.hosein.nzd.students;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    ExtendedFloatingActionButton addNewStudent;
    AdapterStudent adapterStudent;
    public static final Integer REGISTER_CODE = 1001;
    ApiService apiService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        apiService = new ApiService(this);
        recyclerView = findViewById(R.id.recycler_student);

        Toolbar toolbar = findViewById(R.id.toolbar_main);
        setSupportActionBar(toolbar);

        ProgressBar progressBar = findViewById(R.id.progress);
        addNewStudent = findViewById(R.id.add_student_main);

        addNewStudent.setOnClickListener(view -> {
            startActivityForResult(new Intent(MainActivity.this , AddNewStudentActivity.class) , REGISTER_CODE);
        });

       /* apiService.getStudentInformation(new ApiService.listStudentCallBack() {
            @Override
            public void onSuccess(List<Student> students) {
                adapterStudent = new AdapterStudent(students);
                recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this , LinearLayoutManager.VERTICAL , false));
                recyclerView.setAdapter(adapterStudent);
                progressBar.setVisibility(View.GONE);
            }

            @Override
            public void onError(VolleyError error) {

            }
        });
*/

        apiService.getStudentInformation_RETROFIT(new ApiService.listStudentCallBack_RETROFIT() {
            @Override
            public void onSuccess(List<Student> students) {
                adapterStudent = new AdapterStudent(students);
                recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this , LinearLayoutManager.VERTICAL , false));
                recyclerView.setAdapter(adapterStudent);
                progressBar.setVisibility(View.GONE);
            }

            @Override
            public void onError(Exception error) {
                Toast.makeText(MainActivity.this, "خطای نامشخص!", Toast.LENGTH_LONG).show();
                progressBar.setVisibility(View.GONE);
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

        if (requestCode == REGISTER_CODE && resultCode == Activity.RESULT_OK){
            adapterStudent.addStudent(data.getParcelableExtra("studentObject"));
            recyclerView.smoothScrollToPosition(0);
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}