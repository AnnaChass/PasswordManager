package com.example.mobile3;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import androidx.appcompat.app.AppCompatActivity;

import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.content.Context;

public class ChangeKeyActivity extends AppCompatActivity {
    private EditText passwordText;
    private String passwordString = "";
    private String oldKey;
    private String newKey;
    private String[][] resources;
    private int resourcesCount;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_key);
        Bundle arguments = getIntent().getExtras();
        oldKey = new String(arguments.get("key").toString());
        Context context = this;

        FloatingActionButton button = findViewById(R.id.fab_save);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                passwordString = passwordText.getText().toString();
                if (passwordString.isEmpty())
                    return;
                newKey = passwordString;
                getData();
                setKey(passwordString);
                setNewData();
                finish();

                Intent intent = new Intent(context, WorkActivity.class);
                intent.putExtra("key", newKey);
                startActivity(intent);
            }
        });
        passwordText = (EditText) findViewById(R.id.password);
        TextView text = findViewById(R.id.textView);
        text.setText("Set new password.");
    }

    private void setKey(String key_) {
        Data data = new Data(this, oldKey);
        long i = data.setKey(key_);
        //if (data.createKey(key_) < 0) {
            ;
        //}
        data.close();
    }

    private void getData() {
        Data data = new Data(this, oldKey);
        resourcesCount = data.getSize();
        resources = new String[resourcesCount][];
        for (int i = 0; i < resourcesCount; i++) {
            resources[i] = data.getEntryByPosition(0);
            data.deleteEntry(resources[i][0]);
        }
        data.close();
    }

    private void setNewData() {
        Data data = new Data(this, newKey);
        for (int i = 0; i < resourcesCount; i++) {
            data.createEntry(resources[i][0], resources[i][1], resources[i][2], resources[i][3]);
        }
        data.close();
    }
}