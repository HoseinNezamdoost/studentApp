package com.hosein.nzd.students;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    RequestQueue requestQueue;
    List<Student> students = new ArrayList<>();
    RecyclerView recyclerView;
    ExtendedFloatingActionButton addNewStudent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar_main);
        setSupportActionBar(toolbar);

        ProgressBar progressBar = findViewById(R.id.progress);
        addNewStudent = findViewById(R.id.add_student_main);

        addNewStudent.setOnClickListener(view -> {
            startActivity(new Intent(MainActivity.this , AddNewStudentActivity.class));
        });

        StringRequest request = new StringRequest(Request.Method.GET, "https://hosein-nzd.ir/android_app/student/getStudent.php",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {
                            JSONArray jsonArray = new JSONArray(response);

                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject jsonObject = jsonArray.getJSONObject(i);
                                Student student = new Student();
                                student.setFirstName(jsonObject.getString("firstName"));
                                student.setLastName(jsonObject.getString("lastName"));
                                student.setCourse(jsonObject.getString("course"));
                                student.setScore(jsonObject.getInt("score"));
                                student.setId(jsonObject.getInt("id"));

                                students.add(student);

                            }

                            progressBar.setVisibility(View.GONE);
                            recyclerView = findViewById(R.id.recycler_student);
                            recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this , RecyclerView.VERTICAL , false));
                            recyclerView.setAdapter(new AdapterStudent(students));

                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(MainActivity.this,"1"+ e.getMessage(), Toast.LENGTH_SHORT).show();
                            progressBar.setVisibility(View.GONE);
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(MainActivity.this, "2"+error.getMessage(), Toast.LENGTH_SHORT).show();
                progressBar.setVisibility(View.GONE);
            }
        });

        requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(request);

    }
}