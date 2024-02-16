package com.example.connectingtoserver;

import java.util.List;

import android.content.Context;

import com.google.gson.JsonObject;

import io.reactivex.Single;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiService {

        private static final String BASE_URL="http://expertdevelopers.ir/api/v1/";
        private RetrofitApiService retrofitApi;
        public ApiService(Context context,String requestTag) {
            Retrofit retrofit=new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .build();


            retrofitApi=retrofit.create(RetrofitApiService.class);
        }

        public Single<Student> saveStudent(String firstName, String lastName, String course, int score) {
            JsonObject jsonObject=new JsonObject();
            jsonObject.addProperty("first_name",firstName);
            jsonObject.addProperty("last_name",lastName);
            jsonObject.addProperty("course",course);
            jsonObject.addProperty("score",score);

            return retrofitApi.saveStudent(jsonObject);

        }

        public Single<List<Student>> getStudents() {

            return retrofitApi.getStudent();
        }


    }

