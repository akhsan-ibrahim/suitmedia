package com.example.suitmedia;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class Screen2Activity extends AppCompatActivity {

    private TextView textViewName;
    private Button button_chooseUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_screen2);

        textViewName = findViewById(R.id.textViewName);

        Intent intent = getIntent();
        String name = intent.getStringExtra("name");

        textViewName.setText(name);

        button_chooseUser = findViewById(R.id.buttonChooseUser);
        button_chooseUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Screen2Activity.this, Screen3Activity.class);
                startActivity(intent);
            }
        });
    }
}