package com.example.mobile3;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import android.os.Environment;
import java.nio.file.StandardOpenOption;

public class ExportActivity extends AppCompatActivity {
    private String key;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_export);
        Bundle arguments = getIntent().getExtras();
        key = arguments.get("key").toString();

        exportData();
        finish();
    }

    private void exportData() {
        /*File externalAppDir = new File(Environment.getExternalStorageDirectory() + "/Android/data/" +"dataFile.txt");
        if (!externalAppDir.exists()) {
            externalAppDir.mkdir();
        }*/
        File databaseFile = new File(Environment.getExternalStorageDirectory() + "/Android/data/", "dataFile.txt");
        BufferedWriter bw;

        try {
            bw = new BufferedWriter(new FileWriter(databaseFile));
        }
        catch(IOException e) {
            System.out.println(e.getMessage());
            return;
        }

        Data data = new Data(this, key);
        int size = data.getSize();

        for(int i = 0; i < size; i++) {
            String[] resources = data.getEntryByPosition(i);
            writeEntryToFile(resources, bw);
        }

        data.close();

        try {
            bw.close();
        }
        catch(IOException e) {
            return;
        }
    }

    private void writeEntryToFile(String[] resource, BufferedWriter bw) {
        try {
            bw.write(encrypt(resource[0] + " " +
                    resource[1] + " " +
                    resource[2] + " " +
                    resource[3]) + '\n');
        }
        catch(IOException e) {
            return;
        }
    }

    private String encrypt(String openedText) {

        CryptoProvider cp = new CryptoProvider();
        String cipherText = "";

        try {
            cipherText = cp.encryptMessage(openedText, key);
        }
        catch (Exception e) {
            ;
        }

        return cipherText;
    }
}