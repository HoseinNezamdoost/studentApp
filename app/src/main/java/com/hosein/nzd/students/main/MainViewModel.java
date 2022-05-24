package com.hosein.nzd.students.main;

import com.hosein.nzd.students.model.ApiService;
import com.hosein.nzd.students.model.Student;

import java.util.List;

import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.subjects.BehaviorSubject;

public class MainViewModel {

    private final ApiService apiService;
    private BehaviorSubject<Boolean> progressbarSubject = BehaviorSubject.create();

    public MainViewModel(ApiService apiService) {
        this.apiService = apiService;
    }

    public Single<List<Student>> students (){
        progressbarSubject.onNext(true);
        return apiService.getStudentInformation().doFinally(() -> progressbarSubject.onNext(false));
    }

    public BehaviorSubject<Boolean> getProgressbarSubject() {
        return progressbarSubject;
    }
}
