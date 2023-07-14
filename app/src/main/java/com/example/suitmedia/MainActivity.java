package com.example.suitmedia;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private EditText editTextKalimat;
    private Button buttonCheck;
    private Button next_button;
    private EditText editTextName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editTextKalimat = findViewById(R.id.textPalindrome);
        buttonCheck = findViewById(R.id.buttonCheck);

        buttonCheck.setOnClickListener(view -> {
            String kalimat = editTextKalimat.getText().toString().toLowerCase();
            boolean isPalindrome = checkPalindrome(kalimat);

            String resultMessage;
            if (isPalindrome) {
                resultMessage = "isPalindrome";
            } else {
                resultMessage = "not palindrome";
            }

            Toast.makeText(MainActivity.this, resultMessage, Toast.LENGTH_SHORT).show();
        });

        editTextKalimat.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void afterTextChanged(Editable editable) {
                buttonCheck.setEnabled(editable.length() > 0);
            }
        });

        editTextName = findViewById(R.id.textName);
        next_button = findViewById(R.id.buttonNext);
        next_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = editTextName.getText().toString();

                Intent intent = new Intent(MainActivity.this, activity_screen2.class);
                intent.putExtra("name", name);
                startActivity(intent);
            }
        });
    }

    private boolean checkPalindrome(String kalimat) {
        String cleanedKalimat = kalimat.replaceAll("\\s+", "").toLowerCase();
        int length = cleanedKalimat.length();
        int forward = 0;
        int backward = length - 1;

        while (backward > forward) {
            char forwardChar = cleanedKalimat.charAt(forward++);
            char backwardChar = cleanedKalimat.charAt(backward--);

            if (forwardChar != backwardChar) {
                return false;
            }
        }

        return true;
    }
}
