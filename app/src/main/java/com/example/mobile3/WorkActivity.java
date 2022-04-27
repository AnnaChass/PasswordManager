package com.example.mobile3;

import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import androidx.appcompat.app.AppCompatActivity;

import android.view.View;

import android.widget.ListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import android.content.Intent;
import android.content.Context;

public class WorkActivity extends AppCompatActivity {
    private String key;
    private ListView resourceList;
    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_work);
        context = this;

        resourceList = findViewById(R.id.resourceList);
        Bundle arguments = getIntent().getExtras();
        key = arguments.get("key").toString();

        createList();
        resourceList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                startEntryActivity(position);
            }
        });

        FloatingActionButton addButton = findViewById(R.id.fab_add);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startNewActivity();
            }
        });

        FloatingActionButton reloadButton = findViewById(R.id.fab_reload);
        reloadButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createList();
            }
        });

        FloatingActionButton changeKeyButton = findViewById(R.id.fab_change);
        changeKeyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startKeyActivity();
                finish();
            }
        });

        FloatingActionButton importButton = findViewById(R.id.fab_import);
        importButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startImportActivity();
            }
        });

        FloatingActionButton exportButton = findViewById(R.id.fab_export);
        exportButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startExportActivity();
            }
        });
    }

    private void createList() {
        Data data = new Data(this, key);
        if(data.getAllResources().length == 0) {
            ;
        }
        ArrayAdapter <String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, data.getAllResources());
        resourceList.setAdapter(adapter);
        data.close();
    }

    private void startNewActivity() {
        Intent intent = new Intent(this, NewEntryActivity.class);
        intent.putExtra("key", key);
        startActivity(intent);
    }

    private void startEntryActivity(int position) {
        Intent intent = new Intent(this, ChangeEntryActivity.class);
        intent.putExtra("position", position);
        intent.putExtra("key", key);
        startActivity(intent);
    }

    private void startKeyActivity() {
        Intent intent = new Intent(this, ChangeKeyActivity.class);
        intent.putExtra("key", key);
        startActivity(intent);
    }

    private void startImportActivity() {
        Intent intent = new Intent(this, ImportActivity.class);
        intent.putExtra("key", key);
        startActivity(intent);
    }

    private void startExportActivity() {
        Intent intent = new Intent(this, ExportActivity.class);
        intent.putExtra("key", key);
        startActivity(intent);
    }
}