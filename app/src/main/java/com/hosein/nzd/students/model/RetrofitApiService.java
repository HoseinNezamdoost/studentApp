package com.hosein.nzd.students.model;

import com.google.gson.JsonObject;

import java.util.List;

import io.reactivex.rxjava3.core.Single;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface RetrofitApiService {

    @GET("getStudent.php")
    Single<List<Student>> getStudentInformation();

    @POST("putStudent.php")
    Single<Student> postStudentInformation(@Body JsonObject body);

}
