package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class NoteSelect extends AppCompatActivity {


    private final int REQUEST_CODE = 20;
    private List<NotesBuilder> notesList = new ArrayList<>();
    private NotesAdapter nAdapter;
    private RecyclerView notesRecycler;
    int fileCount = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(NoteSelect.this, Main2Activity.class);
                startActivityForResult(i, REQUEST_CODE);
            }
        });

        notesRecycler = (RecyclerView) findViewById(R.id.notes);


        nAdapter = new NotesAdapter(notesList, new NotesAdapter.OnItemClickListener() {
            @Override public void onItemClick(String fileName) {
                Toast.makeText(getApplicationContext(), "Item Clicked" + fileName, Toast.LENGTH_LONG).show();
                Intent i = new Intent(NoteSelect.this, Main2Activity.class);
                i.putExtra("file", fileName); // pass arbitrary data to launched activity
                startActivityForResult(i, REQUEST_CODE);
            }});
        RecyclerView.LayoutManager mLayoutManager =
                new LinearLayoutManager(getApplicationContext());
        notesRecycler.setLayoutManager(mLayoutManager);
        notesRecycler.setItemAnimator(new DefaultItemAnimator());
        notesRecycler.setAdapter(nAdapter);



    }

    // ActivityOne.java, time to handle the result of the sub-activity
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // REQUEST_CODE is defined above
        if (resultCode == RESULT_OK && requestCode == REQUEST_CODE) {

            String f = data.getExtras().getString("fileName");

            addNote(f);
            File directory = getFilesDir();
            File[] files = directory.listFiles();
            System.out.println("files.length "+ files.length);
            System.out.println();
            nAdapter.notifyDataSetChanged();
        }
    }

    private void addNote(String f){
        String theFile = f;
        NotesBuilder note = new NotesBuilder(theFile, Open(theFile));
        notesList.add(note);
    }

    public String Open(String fileName) {
        String content = "";
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

        return content;
    }
}