package com.example.mobile3;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import android.widget.EditText;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import android.view.View;
import android.content.Context;

public class NewEntryActivity extends AppCompatActivity {

    private String key;
    private EditText resourceText;
    private EditText loginText;
    private EditText passwordText;
    private EditText noteText;
    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_entry);
        Bundle arguments = getIntent().getExtras();
        key = arguments.get("key").toString();
        context = this;

        resourceText = findViewById(R.id.text_resource);
        loginText = findViewById(R.id.text_login);
        passwordText = findViewById(R.id.text_password);
        noteText = findViewById(R.id.text_note);

        FloatingActionButton saveButton = findViewById(R.id.fab_save);
        saveButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view) {
                Data data = new Data(context, key);
                long result = data.createEntry(
                        resourceText.getText().toString(),
                        loginText.getText().toString(),
                        passwordText.getText().toString(),
                        noteText.getText().toString()
                );

                if(result < 0) {
                    ;
                }

                data.close();
                finish();
            }
        });
    }
}