package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

public class Main2Activity extends AppCompatActivity {
    EditText EditText1;
    EditText EditTextTitle;
    String fileName;

    //if new note, filename == null
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        fileName = getIntent().getStringExtra("file");

        FloatingActionButton fab = findViewById(R.id.fab);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Save(fileName);
            }
        });

        EditText1 = findViewById(R.id.EditText1);
        EditTextTitle = findViewById(R.id.EditTextTitle);
        if(fileName!=null) {
            EditTextTitle.setText(fileName);
            EditText1.setText(Open(fileName));
        }

    }

    public void Save(String fileName) {
        try {
            fileName=EditTextTitle.getText().toString();
            if(fileName!=null) {
                OutputStreamWriter out =
                        new OutputStreamWriter(openFileOutput(fileName, 0));
                out.write(EditText1.getText().toString());
                out.close();
                Toast.makeText(this, "Note Saved!", Toast.LENGTH_SHORT).show();
            }
            // Prepare data intent
            Intent data = new Intent();
            // Pass relevant data back as a result
            data.putExtra("fileName", fileName);

            setResult(RESULT_OK, data); // set result code and bundle data for response
            finish();
        } catch (Throwable t) {
            Toast.makeText(this, "Exception: " + t.toString(), Toast.LENGTH_LONG).show();
        }
    }
    public String Open(String fileName) {
        String content = "";
        if (FileExists(fileName)) {
            try {
                InputStream in = openFileInput(fileName);
                if ( in != null) {
                    InputStreamReader tmp = new InputStreamReader( in );
                    BufferedReader reader = new BufferedReader(tmp);
                    String str;
                    StringBuilder buf = new StringBuilder();
                    while ((str = reader.readLine()) != null) {
                        buf.append(str + "\n");
                    } in .close();
                    content = buf.toString();
                }
            } catch (java.io.FileNotFoundException e) {} catch (Throwable t) {
                Toast.makeText(this, "Exception: " + t.toString(), Toast.LENGTH_LONG).show();
            }
        }
        return content;
    }

    public boolean FileExists(String fname) {
        File file = getBaseContext().getFileStreamPath(fname);
        return file.exists();
    }
}
