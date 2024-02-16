package com.example.connectingtoserver;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class studentAdapter extends RecyclerView.Adapter<studentAdapter.studentViewHolder> {
    private List<Student> students;
    public studentAdapter(List<Student> students) {
        this.students = students;
    }




    @NonNull
    @Override
    public studentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new studentViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_student ,parent , false));
    }

    public void addStudent(Student student){
        this.students.add(0,student);
        notifyItemInserted(0);
    }

    @Override
    public void onBindViewHolder(@NonNull studentViewHolder holder, int position) {
            holder.bind(students.get(position));
    }

    @Override
    public int getItemCount() {
        return students.size();
    }

    public class studentViewHolder extends RecyclerView.ViewHolder{
        private TextView firstCh;
        private TextView fullName;
        private TextView score;
        private TextView course;
        public studentViewHolder(@NonNull View itemView) {
            super(itemView);
           firstCh=itemView.findViewById(R.id.firstCharTv);
           fullName=itemView.findViewById(R.id.fullnameTv);
           score=itemView.findViewById(R.id.scoreTv) ;
            course=itemView.findViewById(R.id.courseTv);
        }


        public void bind(Student student){
            firstCh.setText(student.getFirstName().substring(0,1));
            fullName.setText(student.getFirstName()+" "+student.getLastName());
            score.setText(student.getScore());
            course.setText(student.getCourse());


        }
    }
}
