package com.example.assignmentalerts;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {

    TextView addWork,assignment,quizzes;
    private Toolbar toolbar;
    private FirebaseAuth auth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        addWork = findViewById(R.id.addWork);
        assignment = findViewById(R.id.assignment);
        quizzes = findViewById(R.id.quizzes);

        auth = FirebaseAuth.getInstance();

        toolbar = findViewById(R.id.mainToolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Student Management");


        addWork.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this,AddWorkActivity.class);
            startActivity(intent);
        });
        assignment.setOnClickListener(v -> {
            Intent intent  =new Intent(MainActivity.this,WorkDetailsActivity.class);
            intent.putExtra("type","Assignments");
            startActivity(intent);
        });

        quizzes.setOnClickListener(v -> {
            Intent intent  =new Intent(MainActivity.this,WorkDetailsActivity.class);
            intent.putExtra("type","Quizzes");
            startActivity(intent);
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu,menu);
        return super.onCreateOptionsMenu(menu);

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId())
        {
            case R.id.logOut:
                startActivity(new Intent(getApplicationContext(),LoginActivity.class));
                finish();
                auth.signOut();
                break;
        }

        return super.onOptionsItemSelected(item);
    }
}