package com.hosein.nzd.students.main;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.hosein.nzd.students.R;
import com.hosein.nzd.students.model.Student;

import java.util.ArrayList;
import java.util.List;

public class AdapterStudent extends RecyclerView.Adapter<AdapterStudent.StudentViewHolder> {

    List<Student> students = new ArrayList<>();

    public AdapterStudent(List<Student> students) {
        this.students = students;
    }

    public void addStudent(Student student){
        this.students.add(0 , student);
        notifyItemInserted(0);
    }

    @NonNull
    @Override
    public StudentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new StudentViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_student_rv , parent , false));
    }

    @Override
    public void onBindViewHolder(@NonNull StudentViewHolder holder, int position) {
        holder.bind(students.get(position));
    }

    @Override
    public int getItemCount() {
        return students.size();
    }

    public class StudentViewHolder extends RecyclerView.ViewHolder{

        TextView fullNameTV, firstNameCharacterTV , courseTV, scoreTV;

        public StudentViewHolder(@NonNull View itemView) {
            super(itemView);

            fullNameTV = itemView.findViewById(R.id.full_name_item);
            firstNameCharacterTV = itemView.findViewById(R.id.firstname_character_item);
            courseTV = itemView.findViewById(R.id.course_item);
            scoreTV = itemView.findViewById(R.id.score_item);
        }

        public void bind (Student student){
            fullNameTV.setText(student.getFirstName() + " " + student.getLastName());
            firstNameCharacterTV.setText(student.getFirstName().substring(0 , 1));
            courseTV.setText(student.getCourse());
            scoreTV.setText(String.valueOf(student.getScore()));
        }

    }

}
