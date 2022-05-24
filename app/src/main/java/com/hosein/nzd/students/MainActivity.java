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

import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;

import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Scheduler;
import io.reactivex.rxjava3.core.SingleObserver;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    ExtendedFloatingActionButton addNewStudent;
    AdapterStudent adapterStudent;
    public static final Integer REGISTER_CODE = 1001;
    ApiService apiService;
    Disposable disposable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        apiService = new ApiService();
        recyclerView = findViewById(R.id.recycler_student);

        Toolbar toolbar = findViewById(R.id.toolbar_main);
        setSupportActionBar(toolbar);

        ProgressBar progressBar = findViewById(R.id.progress);
        addNewStudent = findViewById(R.id.add_student_main);

        addNewStudent.setOnClickListener(view -> {
            startActivityForResult(new Intent(MainActivity.this , AddNewStudentActivity.class) , REGISTER_CODE);
        });

        apiService.getStudentInformation()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<List<Student>>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        disposable = d;
                    }

                    @Override
                    public void onSuccess(@NonNull List<Student> students) {
                        adapterStudent = new AdapterStudent(students);
                        recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this , LinearLayoutManager.VERTICAL , false));
                        recyclerView.setAdapter(adapterStudent);
                        progressBar.setVisibility(View.GONE);
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        Toast.makeText(MainActivity.this, "خطای نامشخص!", Toast.LENGTH_LONG).show();
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

    @Override
    protected void onDestroy() {
        super.onDestroy();
        disposable.dispose();
    }
}