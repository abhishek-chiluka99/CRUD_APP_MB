package com.example.my_task_manager;

import android.database.Cursor;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationBarView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    FloatingActionButton add_button;

    MyDatabaseHelper mydb;
    ArrayList<String> book_id, book_title, book_author, book_page;
    CustomAdapter customAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recyclerView);
        add_button = findViewById(R.id.add_button);

        add_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, AddActivity.class);
                startActivity(intent);
            }
        });

        mydb = new MyDatabaseHelper(MainActivity.this);
        book_id = new ArrayList<>();
        book_author = new ArrayList<>();
        book_title = new ArrayList<>();
        book_page = new ArrayList<>();

        storeAllData();

        customAdapter = new CustomAdapter(MainActivity.this,this, book_id, book_title, book_author, book_page);
        recyclerView.setAdapter(customAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));

        BottomNavigationView bottomNavigatonView = findViewById(R.id.bottom_navigation);
        bottomNavigatonView.setSelectedItemId(R.id.home);


        bottomNavigatonView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();

                if(id == R.id.home)
                {
                    return true;
                }
                else if(id == R.id.dashboard){
                    startActivity(new Intent(getApplicationContext(),Task_Complete.class));
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
        Cursor cursor = mydb.readAllData();
        if(cursor.getCount() == 0)
        {
            Toast.makeText(this, "No Task assigned", Toast.LENGTH_SHORT).show();
        }else{
            while(cursor.moveToNext()){
                book_id.add(cursor.getString(0));
                book_title.add(cursor.getString(1));
                book_author.add(cursor.getString(2));
                book_page.add(cursor.getString(3));
            }
        }

    }
    public void onRestart()
    {
        super.onRestart();
        finish();
        startActivity(getIntent());
    }
}