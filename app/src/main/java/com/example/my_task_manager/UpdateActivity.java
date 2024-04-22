package com.example.my_task_manager;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.Calendar;

public class UpdateActivity extends AppCompatActivity {

    EditText title_input, author_input, pages_input;
    Button update_button, del_button, compl_button;
    String id, title, author, pages;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);

        title_input = findViewById(R.id.title_input2);
        author_input = findViewById(R.id.author_input2);
        pages_input = findViewById(R.id.pages_input2);
        update_button = findViewById(R.id.update_button);
        del_button = findViewById(R.id.del_button);
        compl_button = findViewById(R.id.compl_button);

        getTaskData();

        pages_input.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final Calendar c = Calendar.getInstance();


                int year = c.get(Calendar.YEAR);
                int month = c.get(Calendar.MONTH);
                int day = c.get(Calendar.DAY_OF_MONTH);


                DatePickerDialog datePickerDialog = new DatePickerDialog(

                        UpdateActivity.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {

                                pages_input.setText(year + "-" + (monthOfYear + 1) + "-" + dayOfMonth);

                            }
                        },
                        year, month, day);
                datePickerDialog.show();
            }
        });

        update_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MyDatabaseHelper myDB = new MyDatabaseHelper(UpdateActivity.this);
                title = title_input.getText().toString();
                author = author_input.getText().toString();
                pages = pages_input.getText().toString();
                myDB.updateData(id,title,author,pages,0);

            }
        });

        del_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MyDatabaseHelper myDB = new MyDatabaseHelper(UpdateActivity.this);
                myDB.deleteData(id);

                finish();

            }
        });

        compl_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MyDatabaseHelper myDB = new MyDatabaseHelper(UpdateActivity.this);
                title = title_input.getText().toString();
                author = author_input.getText().toString();
                pages = pages_input.getText().toString();
                myDB.updateData(id,title,author,pages,1);

            }
        });



    }

    void getTaskData(){
        if(getIntent().hasExtra("id") && getIntent().hasExtra("title")
        && getIntent().hasExtra("pages") && getIntent().hasExtra("author") ){

            id = getIntent().getStringExtra("id");
            title = getIntent().getStringExtra("title");
            author = getIntent().getStringExtra("author");
            pages = getIntent().getStringExtra("pages");


            title_input.setText(title);
            author_input.setText(author);
            pages_input.setText(pages);

        }else {
            Toast.makeText(this, "No data", Toast.LENGTH_SHORT).show();
        }


    }
}