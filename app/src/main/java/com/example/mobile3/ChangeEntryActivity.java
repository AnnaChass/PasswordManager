package com.example.mobile3;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class ChangeEntryActivity extends AppCompatActivity {

    private String key;
    private EditText resourceText;
    private EditText loginText;
    private EditText passwordText;
    private EditText noteText;
    private Context context;
    private int position;
    private String oldResourceName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_entry);
        Bundle arguments = getIntent().getExtras();
        key = arguments.get("key").toString();
        position = Integer.parseInt(arguments.get("position").toString());
        context = this;

        setData();

        FloatingActionButton saveButton = findViewById(R.id.fab_save);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateData();
            }
        });

        FloatingActionButton deleteButton = findViewById(R.id.fab_delete);
        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deleteData();
            }
        });
    }

    private void setData() {
        resourceText = findViewById(R.id.text_resource);
        loginText = findViewById(R.id.text_login);
        passwordText = findViewById(R.id.text_password);
        noteText = findViewById(R.id.text_note);

        Data data = new Data(this, key);
        String[] resources = data.getEntryByPosition(position);
        data.close();

        oldResourceName = resources[0];

        resourceText.setText(resources[0]);
        loginText.setText(resources[1]);
        passwordText.setText(resources[2]);
        noteText.setText(resources[3]);
    }

    private void updateData() {
        resourceText = findViewById(R.id.text_resource);
        loginText = findViewById(R.id.text_login);
        passwordText = findViewById(R.id.text_password);
        noteText = findViewById(R.id.text_note);

        Data data = new Data(context, key);
        long result = data.updateEntry(
                oldResourceName,
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

    private void deleteData() {
        resourceText = findViewById(R.id.text_resource);
        Data data = new Data(context, key);
        data.deleteEntry(resourceText.getText().toString());
        data.close();
        finish();
    }
}