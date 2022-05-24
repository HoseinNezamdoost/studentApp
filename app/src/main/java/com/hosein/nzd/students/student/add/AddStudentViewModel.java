package com.hosein.nzd.students.student.add;

import com.hosein.nzd.students.model.ApiService;
import com.hosein.nzd.students.model.Student;

import io.reactivex.rxjava3.core.Single;

public class AddStudentViewModel {

    private ApiService apiService;

    public AddStudentViewModel(ApiService apiService) {
        this.apiService = apiService;
    }

    public Single<Student> saveStudent(String firstName , String lastName , String course , String score){
        return apiService.postStudentInformation(firstName , lastName , course , score);
    }

}
