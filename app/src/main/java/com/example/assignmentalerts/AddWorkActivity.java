package com.example.assignmentalerts;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.assignmentalerts.databinding.AddWorkActivityBinding;
import com.example.assignmentalerts.ui.Models.WorkList;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class AddWorkActivity extends AppCompatActivity {

    AddWorkActivityBinding binding;
    private FirebaseDatabase database;
    private FirebaseAuth auth;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = AddWorkActivityBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        database = FirebaseDatabase.getInstance();
        auth = FirebaseAuth.getInstance();

        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Processing..");
        progressDialog.setMessage("Adding your Data..");

        String senderId = auth.getCurrentUser().getUid().toString();

        binding.btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressDialog.show();
                String type = binding.spinner.getSelectedItem().toString();
                String subject = binding.etSubject.getText().toString().trim();
                String topic = binding.etTopic.getText().toString().trim();
                String deadLine = binding.etDeadline.getText().toString().trim();

                WorkList list=new WorkList(topic,subject,deadLine,senderId,type);
                if (type.equals("Quiz")) {

                    database.getReference().child("Quizzes").push().setValue(list).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void unused) {
                            progressDialog.dismiss();


                            Toast.makeText(AddWorkActivity.this, "Quiz Added Successfully", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(getApplicationContext(),MainActivity.class));
                            finish();
                        }
                    });

                }
                else if(type.equals("Assignment")){

                    database.getReference().child("Assignments").push().setValue(list).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void unused) {

                            progressDialog.dismiss();

                            Toast.makeText(AddWorkActivity.this, "Assignment Added Successfully", Toast.LENGTH_SHORT).show();

                            startActivity(new Intent(getApplicationContext(),MainActivity.class));
                            finish();
                        }
                    });

                }
            }
        });


    }
}