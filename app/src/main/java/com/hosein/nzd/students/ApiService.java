package com.hosein.nzd.students;

import com.google.gson.JsonObject;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiService {

    public static final String BASE_URL = "https://hosein-nzd.ir/android_app/student/";
    Retrofit retrofit;

    //--------------------------------------------------------------------constructor-------------------------------------------------------------------

    public ApiService() {

        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    //------------------------------------------------------------getStudentInformation---------------------------------------------------------------------------
    public void getStudentInformation(listStudentCallBack callBack){
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

    //------------------------------------------------------------postStudentInformation---------------------------------------------------------------------------

    public void postStudentInformation(String firstName , String lastName , String course , String  score , getStudentAdded callBack){

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

    public interface listStudentCallBack {
        void onSuccess(List<Student> students);
        void onError(Exception error);
    }

    public interface getStudentAdded {
        void onSuccess(Student student);
        void onError(Exception error);
    }

}
