package com.hosein.nzd.students;

import com.google.gson.JsonObject;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface RetrofitApiService {

    @GET("getStudent.php")
    Call<List<Student>> getStudentInformation();

    @POST("putStudent.php")
    Call<Student> postStudentInformation(@Body JsonObject body);

}
