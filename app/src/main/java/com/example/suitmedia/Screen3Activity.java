package com.example.suitmedia;
import android.os.Bundle;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Screen3Activity extends AppCompatActivity {

    private ListView listViewUsers;
    private UserListAdapter userListAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_screen3);

        listViewUsers = findViewById(R.id.listViewUsers);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://reqres.in/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ApiService apiService = retrofit.create(ApiService.class);
        Call<UserResponse> call = apiService.getUsers();

        call.enqueue(new Callback<UserResponse>() {
            @Override
            public void onResponse(Call<UserResponse> call, Response<UserResponse> response) {
                if (response.isSuccessful()) {
                    List<User> userList = response.body().getData();
                    userListAdapter = new UserListAdapter(Screen3Activity.this, userList);
                    listViewUsers.setAdapter(userListAdapter);
                }
            }

            @Override
            public void onFailure(Call<UserResponse> call, Throwable t) {
                // Error handling
            }
        });
    }
}