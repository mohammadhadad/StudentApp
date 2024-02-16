package com.example.connectingtoserver;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import java.util.List;
import io.reactivex.Scheduler;
import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

private static final String TAG = "MainActivity";

    private static final Integer ADD_STUDENT_REQUEST_ID=1001;
    private studentAdapter studentAdapter;
    private RecyclerView recyclerView;
    private ApiService apiService;
    private Disposable disposable;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);




            apiService=new ApiService(this,TAG);
            Toolbar toolbar=findViewById(R.id.toolbar_main);
            setSupportActionBar(toolbar);

            View addNewStudentBtn=findViewById(R.id.addStudentbtn);
            addNewStudentBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivityForResult(new Intent(MainActivity.this,add_new_student.class),ADD_STUDENT_REQUEST_ID);
                }
            });




            apiService.getStudents()
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new SingleObserver<List<Student>>() {
                        @Override
                        public void onSubscribe(Disposable d) {
                            disposable=d;
                        }

                        @Override
                        public void onSuccess(List<Student> students) {
                            recyclerView=findViewById(R.id.rv_main);
                            recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this, RecyclerView.VERTICAL,false));
                            studentAdapter=new studentAdapter(students);
                            recyclerView.setAdapter(studentAdapter);
                        }

                        @Override
                        public void onError(Throwable e) {
                    Toast.makeText(MainActivity.this,"خطای نامشخص",Toast.LENGTH_SHORT).show();

                        }
                    });

        }

        @Override
        protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
            if (requestCode==ADD_STUDENT_REQUEST_ID && resultCode== Activity.RESULT_OK){
                if (data!=null && studentAdapter!=null && recyclerView!=null){
                    Student student=data.getParcelableExtra("student");
                    studentAdapter.addStudent(student);
                    recyclerView.smoothScrollToPosition(0);
                }
            }
            super.onActivityResult(requestCode, resultCode, data);
        }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(disposable!=null)
        disposable.dispose();
    }
}