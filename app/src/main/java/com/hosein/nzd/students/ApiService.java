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

        GsonRequest<List<Student>> request = new GsonRequest<>(Request.Method.GET, BASE_URL + GET_STUDENT_URL,
                new TypeToken<List<Student>>() {
                }.getType(),
                new Response.Listener<List<Student>>() {
                    @Override
                    public void onResponse(List<Student> response) {
                        callBack.onSuccess(response);
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

    public void postStudentInformation(String firstName , String lastName , String course , String  score , getStudentAdded callBack){

        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("firstName", firstName);
            jsonObject.put("lastName" , lastName);
            jsonObject.put("course" , course);
            jsonObject.put("score" , score);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        GsonRequest<Student> studentGsonRequest = new GsonRequest<>(Request.Method.POST, BASE_URL + POST_STUDENT_URL, Student.class,
                jsonObject,
                new Response.Listener<Student>() {
                    @Override
                    public void onResponse(Student response) {
                        callBack.onSuccess(response);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        callBack.onError(error);
                    }
                });
        requestQueue.add(studentGsonRequest);
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
