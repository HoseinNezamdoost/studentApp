package com.hosein.nzd.students;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class ApiService {

    RequestQueue requestQueue;
    Gson gson;
    public static final String BASE_URL = "https://hosein-nzd.ir/android_app/student/";
    public static final String GET_STUDENT_URL = "getStudent.php";
    public static final String POST_STUDENT_URL = "putStudent.php";

    //--------------------------------------------------------------------constructor-------------------------------------------------------------------

    public ApiService(Context context) {
        if (requestQueue == null)
        requestQueue = Volley.newRequestQueue(context.getApplicationContext());
        //---------------
        gson = new Gson();
    }

    //------------------------------------------------------------------getStudentInformation---------------------------------------------------------------------

    public void getStudentInformation(listStudentCallBack callBack){

        List<Student> students = new ArrayList<>();

        StringRequest request = new StringRequest(Request.Method.GET, BASE_URL + GET_STUDENT_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        List<Student> students = gson.fromJson(response , new TypeToken<List<Student>>(){}.getType());
                        callBack.onSuccess(students);
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

    //--------------------------------------------------------------------postStudentInformation-------------------------------------------------------------------

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

                        Student student = gson.fromJson(response.toString() , Student.class);
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

    //------------------------------------------------------------interfaces---------------------------------------------------------------------------

    public interface listStudentCallBack{
        void onSuccess(List<Student> students);
        void onError(VolleyError error);
    }

    public interface getStudentAdded{
        void onSuccess(Student student);
        void onError(VolleyError error);
    }

}
