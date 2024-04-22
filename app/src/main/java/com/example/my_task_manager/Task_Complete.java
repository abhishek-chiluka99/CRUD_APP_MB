package com.example.my_task_manager;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationBarView;

import java.util.ArrayList;

public class Task_Complete extends AppCompatActivity {
    RecyclerView recyclerView;
    FloatingActionButton add_button;

    MyDatabaseHelper mydb;
    ArrayList<String> book_id, book_title, book_author, book_page;
    CustomAdapter customAdapter;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.task_complete);

        recyclerView = findViewById(R.id.recyclerView1);

        mydb = new MyDatabaseHelper(Task_Complete.this);
        book_id = new ArrayList<>();
        book_author = new ArrayList<>();
        book_title = new ArrayList<>();
        book_page = new ArrayList<>();
        storeAllData();
        customAdapter = new CustomAdapter(Task_Complete.this,this, book_id, book_title, book_author, book_page);
        recyclerView.setAdapter(customAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(Task_Complete.this));

        BottomNavigationView bottomNavigatonView = findViewById(R.id.bottom_navigation);
        bottomNavigatonView.setSelectedItemId(R.id.home);


        bottomNavigatonView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();

                if(id == R.id.dashboard)
                {
                    return true;
                }
                else if(id == R.id.home){
                    startActivity(new Intent(getApplicationContext(),MainActivity.class));
                    overridePendingTransition(0,0);
                    return true;
                }
                else {
                    return false;
                }
            }
        });

    }

    void storeAllData(){
        Cursor cursor = mydb.readAllComplData();
        if(cursor.getCount() == 0)
        {
            Toast.makeText(this, "No Task completed", Toast.LENGTH_SHORT).show();
        }else{
            while(cursor.moveToNext()){
                book_id.add(cursor.getString(0));
                book_title.add(cursor.getString(1));
                book_author.add(cursor.getString(2));
                book_page.add(cursor.getString(3));
            }
        }

    }
}
