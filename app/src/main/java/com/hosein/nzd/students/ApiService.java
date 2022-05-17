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
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiService {

    RequestQueue requestQueue;
    Gson gson;
    public static final String BASE_URL = "https://hosein-nzd.ir/android_app/student/";
    public static final String GET_STUDENT_URL = "getStudent.php";
    public static final String POST_STUDENT_URL = "putStudent.php";

    Retrofit retrofit;
    RetrofitApiService retrofitApiService;

    //--------------------------------------------------------------------constructor-------------------------------------------------------------------

    public ApiService(Context context) {
        if (requestQueue == null)
        requestQueue = Volley.newRequestQueue(context.getApplicationContext());
        //---------------
        gson = new Gson();
        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
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
    //------------------------------------------------------------retrofit---------------------------------------------------------------------------
    public void getStudentInformation_RETROFIT(listStudentCallBack_RETROFIT callBack){
        retrofit.create(RetrofitApiService.class).getStudentInformation().enqueue(new Callback<List<Student>>() {
            @Override
            public void onResponse(Call<List<Student>> call, retrofit2.Response<List<Student>> response) {
                callBack.onSuccess(response.body());
            }

            @Override
            public void onFailure(Call<List<Student>> call, Throwable t) {
                callBack.onError(new Exception(t));
            }
        });
    }

    //------------------------------------------------------------retrofit---------------------------------------------------------------------------

    public void postStudentInformation_RETROFIT(String firstName , String lastName , String course , String  score , getStudentAdded_RETROFIT callBack){

            JsonObject jsonObject = new JsonObject();
            jsonObject.addProperty("firstName", firstName);
            jsonObject.addProperty("lastName" , lastName);
            jsonObject.addProperty("course" , course);
            jsonObject.addProperty("score" , score);

            retrofit.create(RetrofitApiService.class).postStudentInformation(jsonObject).enqueue(new Callback<Student>() {
                @Override
                public void onResponse(Call<Student> call, retrofit2.Response<Student> response) {
                    callBack.onSuccess(response.body());
                }

                @Override
                public void onFailure(Call<Student> call, Throwable t) {
                    callBack.onError(new Exception(t));
                }
            });

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

    public interface listStudentCallBack_RETROFIT{
        void onSuccess(List<Student> students);
        void onError(Exception error);
    }

    public interface getStudentAdded_RETROFIT{
        void onSuccess(Student student);
        void onError(Exception error);
    }

}
