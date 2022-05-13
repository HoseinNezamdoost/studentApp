package com.hosein.nzd.students;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class ApiService {

    RequestQueue requestQueue;
    public static final String BASE_URL = "https://hosein-nzd.ir/android_app/student/";
    public static final String GET_STUDENT_URL = "getStudent.php";
    public static final String POST_STUDENT_URL = "putStudent.php";

    public ApiService(Context context) {
        if (requestQueue == null)
        requestQueue = Volley.newRequestQueue(context.getApplicationContext());
    }

    public void getStudent(listStudentCallBack callBack){

        List<Student> students = new ArrayList<>();

        StringRequest request = new StringRequest(Request.Method.GET, BASE_URL + GET_STUDENT_URL,
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

                            callBack.onSuccess(students);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        callBack.onError(error);
                    }
                });

        requestQueue.add(request);
    }

    public void postStudentInformation(String firstName , String lastName , String course , String  score , getStudentAdded getStudentAdded){

        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("firstName", firstName);
            jsonObject.put("lastName" , lastName);
            jsonObject.put("course" , course);
            jsonObject.put("score" , score);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, BASE_URL + POST_STUDENT_URL, jsonObject,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Student student = new Student();
                        try {
                            student.setFirstName(response.getString("firstName"));
                            student.setLastName(response.getString("lastName"));
                            student.setCourse(response.getString("course"));
                            student.setScore(response.getInt("score"));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        getStudentAdded.onSuccess(student);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        getStudentAdded.onError(error);
                    }
                });

        requestQueue.add(jsonObjectRequest);
    }

    public interface listStudentCallBack{
        void onSuccess(List<Student> students);
        void onError(VolleyError error);
    }

    public interface getStudentAdded{
        void onSuccess(Student student);
        void onError(VolleyError error);
    }

}
