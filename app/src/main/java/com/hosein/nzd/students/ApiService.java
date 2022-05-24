package com.hosein.nzd.students;

import com.google.gson.JsonObject;

import java.util.List;

import hu.akarnokd.rxjava3.retrofit.RxJava3CallAdapterFactory;
import io.reactivex.rxjava3.core.Single;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiService {

    public static final String BASE_URL = "https://hosein-nzd.ir/android_app/student/";
    Retrofit retrofit;
    RetrofitApiService retrofitApiService;

    //--------------------------------------------------------------------constructor-------------------------------------------------------------------

    public ApiService() {

        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
                .build();

        retrofitApiService = retrofit.create(RetrofitApiService.class);
    }

    //------------------------------------------------------------getStudentInformation---------------------------------------------------------------------------
    public Single<List<Student>> getStudentInformation(){
        return retrofitApiService.getStudentInformation();
    }

    //------------------------------------------------------------postStudentInformation---------------------------------------------------------------------------

    public Single<Student> postStudentInformation(String firstName , String lastName , String course , String  score){

            JsonObject jsonObject = new JsonObject();
            jsonObject.addProperty("firstName", firstName);
            jsonObject.addProperty("lastName" , lastName);
            jsonObject.addProperty("course" , course);
            jsonObject.addProperty("score" , score);

           return retrofitApiService.postStudentInformation(jsonObject);
    }

}
