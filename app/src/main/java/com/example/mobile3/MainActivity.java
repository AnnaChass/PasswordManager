package com.example.mobile3;

import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import androidx.appcompat.app.AppCompatActivity;

import android.view.View;

import android.content.Intent;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private EditText keyText;
    private String keyString = "";
    private String realKeyString;
    private boolean isKeyExist;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FloatingActionButton button = findViewById(R.id.fab);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                keyString = keyText.getText().toString();
                if (keyString.isEmpty())
                    return;
                realKeyString = getKey();
                if (realKeyString == null) {
                    realKeyString = keyString;
                    setKey(keyString);
                    startNewActivity(keyString);
                }
                else {
                    if (keyString.compareTo(realKeyString) == 0) {
                        startNewActivity(keyString);
                        finish();
                    }
                }
            }
        });
        keyText = (EditText) findViewById(R.id.editPassword);
        TextView text = findViewById(R.id.textView);
        text.setText("Enter the password.");
    }

    private String getKey() {
        Data data = new Data(this, keyString);
        String result = data.getKey();
        data.close();
        return result;
    }

    private void setKey(String key_) {
        Data data = new Data(this, key_);
        long i = data.setKey(key_);
        data.close();
    }

    private void startNewActivity(String key) {
        Intent intent = new Intent(this, WorkActivity.class);
        intent.putExtra("key", key);
        startActivity(intent);
    }
}