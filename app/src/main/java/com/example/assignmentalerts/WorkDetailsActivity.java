package com.example.assignmentalerts;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.assignmentalerts.ui.Adapters.WorkListAdapter;
import com.example.assignmentalerts.ui.Models.WorkList;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class WorkDetailsActivity extends AppCompatActivity {


    FirebaseDatabase database;
    RecyclerView recyclerView;
    ArrayList<WorkList> workLists = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_work_details);

        database = FirebaseDatabase.getInstance();
        recyclerView = findViewById(R.id.detailRecView);

        WorkListAdapter adapter = new WorkListAdapter(workLists, this);
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
        recyclerView.setLayoutManager(new LinearLayoutManager(this));


        String type = getIntent().getStringExtra("type");

        if (type.equals("Quizzes")) {
            database.getReference().child("Quizzes").
                    addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                            workLists.clear();
                            for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                                WorkList workList = dataSnapshot.getValue(WorkList.class);
                                workList.setWork_id(dataSnapshot.getKey());
                                workLists.add(workList);
                            }

                            adapter.notifyDataSetChanged();
                        }
                        @Override
                        public void onCancelled(@NonNull @NotNull DatabaseError error) {

                            Toast.makeText(WorkDetailsActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
        } else if (type.equals("Assignments")) {
            database.getReference().child("Assignments").
                    addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                            workLists.clear();
                            for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                                WorkList workList = dataSnapshot.getValue(WorkList.class);
                                workList.setWork_id(dataSnapshot.getKey());
                                workLists.add(workList);
                            }
                            adapter.notifyDataSetChanged();
                        }

                        @Override
                        public void onCancelled(@NonNull @NotNull DatabaseError error) {

                            Toast.makeText(WorkDetailsActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();

                        }
                    });
        } else {
            Toast.makeText(this, "Error Happened", Toast.LENGTH_SHORT).show();
        }
    }
}