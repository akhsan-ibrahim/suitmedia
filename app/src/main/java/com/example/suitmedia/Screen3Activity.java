package com.example.suitmedia;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Screen3Activity extends AppCompatActivity {

    private SwipeRefreshLayout swipeRefreshLayout;
    private RecyclerView recyclerViewUsers;
    private UserListAdapter userListAdapter;
    private List<User> userList;
    private int currentPage = 1;
    private int perPage = 8;
    private boolean isLoading = false;
    private ProgressBar progressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_screen3);

        swipeRefreshLayout = findViewById(R.id.swipeRefreshLayout);
        recyclerViewUsers = findViewById(R.id.recyclerViewUsers);
        progressBar = findViewById(R.id.progressBar);

        userList = new ArrayList<>();
        userListAdapter = new UserListAdapter(Screen3Activity.this, userList);
        recyclerViewUsers.setAdapter((RecyclerView.Adapter) userListAdapter);
        recyclerViewUsers.setLayoutManager(new LinearLayoutManager(this));
        recyclerViewUsers.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                LinearLayoutManager layoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
                int visibleItemCount = layoutManager.getChildCount();
                int totalItemCount = layoutManager.getItemCount();
                int firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition();

                if (!isLoading) {
                    if ((visibleItemCount + firstVisibleItemPosition) >= totalItemCount
                            && firstVisibleItemPosition >= 0) {
                        loadMoreUsers();
                    }
                }
            }
        });

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                currentPage = 1;
                userList.clear();
                loadUsers();
            }
        });

        loadUsers();
    }

    private void loadUsers() {
        swipeRefreshLayout.setRefreshing(true);
        progressBar.setVisibility(View.VISIBLE);
        isLoading = true;

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://reqres.in/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ApiService apiService = retrofit.create(ApiService.class);
        Call<UserResponse> call = apiService.getUsers(currentPage, perPage);

        call.enqueue(new Callback<UserResponse>() {
            @Override
            public void onResponse(Call<UserResponse> call, Response<UserResponse> response) {
                if (response.isSuccessful()) {
                    UserResponse userResponse = response.body();
                    List<User> fetchedUsers = userResponse.getData();
                    userList.addAll(fetchedUsers);
                    userListAdapter.notifyDataSetChanged();
                } else {
                    Toast.makeText(Screen3Activity.this, "Failed to load users", Toast.LENGTH_SHORT).show();
                }
                swipeRefreshLayout.setRefreshing(false);
                progressBar.setVisibility(View.GONE);
                isLoading = false;
            }

            @Override
            public void onFailure(Call<UserResponse> call, Throwable t) {
                Log.e("Screen3Activity", "Error: " + t.getMessage());
                Toast.makeText(Screen3Activity.this, "Failed to load users", Toast.LENGTH_SHORT).show();
                swipeRefreshLayout.setRefreshing(false);
                progressBar.setVisibility(View.GONE);
                isLoading = false;
            }
        });
    }

    private void loadMoreUsers() {
        progressBar.setVisibility(View.VISIBLE);
        isLoading = true;
        currentPage++;

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://reqres.in/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ApiService apiService = retrofit.create(ApiService.class);
        Call<UserResponse> call = apiService.getUsers(currentPage, perPage);

        call.enqueue(new Callback<UserResponse>() {
            @Override
            public void onResponse(Call<UserResponse> call, Response<UserResponse> response) {
                if (response.isSuccessful()) {
                    UserResponse userResponse = response.body();
                    List<User> fetchedUsers = userResponse.getData();
                    userList.addAll(fetchedUsers);
                    userListAdapter.notifyDataSetChanged();
                } else {
                    Toast.makeText(Screen3Activity.this, "Failed to load more users", Toast.LENGTH_SHORT).show();
                }
                progressBar.setVisibility(View.GONE);
                isLoading = false;
            }

            @Override
            public void onFailure(Call<UserResponse> call, Throwable t) {
                Log.e("Screen3Activity", "Error: " + t.getMessage());
                Toast.makeText(Screen3Activity.this, "Failed to load more users", Toast.LENGTH_SHORT).show();
                progressBar.setVisibility(View.GONE);
                isLoading = false;
            }
        });
    }
}