package com.example.mobile3;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import android.os.Environment;

public class ImportActivity extends AppCompatActivity {
    private String key;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_import);

        Bundle arguments = getIntent().getExtras();
        key = arguments.get("key").toString();

        importData();
        finish();
    }

    private void importData() {
        File databaseFile = new File(Environment.getExternalStorageDirectory() + "/Android/data/", "dataFile.txt");
        if (!databaseFile.exists()) {
            return;
        }
        BufferedReader br;

        try {
            br = new BufferedReader(new FileReader(databaseFile));
        }
        catch (IOException e) {
            return;
        }

        Data database = new Data(this, key);
        String line;

        try {
            while((line = br.readLine()) != null) {

                String[] parameters = decrypt(line).split(" ");
                if(parameters.length < 4) {
                    continue;
                }

                database.createEntry(parameters[0], parameters[1], parameters[2], parameters[3]);
            }
        }
        catch(IOException e) {
            return;
        }

        database.close();

        try {
            br.close();
        }
        catch(IOException e) {
            return;
        }
    }

    private String decrypt(String cipherText) {
        CryptoProvider cp = new CryptoProvider();
        String openedText = "";

        try {
            openedText = cp.decryptMessage(cipherText, key);
        }
        catch (Exception e) { }
        return openedText;
    }
}