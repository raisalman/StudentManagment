package com.example.assignmentalerts;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.assignmentalerts.databinding.ActivitySignupBinding;
import com.example.assignmentalerts.ui.Models.UserModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class SignUpActivity extends AppCompatActivity {

    private FirebaseAuth auth;
    private FirebaseDatabase database;
    private ProgressDialog progressDialog;
    private ActivitySignupBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        binding = ActivitySignupBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        auth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();

        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Creating Account...");
        progressDialog.setMessage("We are creating your Account...");

        //set password Visibility true/false
        binding.sigUpPassVisibility.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //   binding.imgPassVisibility.setSelected(!binding.imgPassVisibility.isPressed());

                binding.etPassword.setTransformationMethod(null);
                binding.sigUpPassVisibility.setImageResource(R.drawable.visibility_off);

                binding.etPassword.setSelection(binding.etPassword.getText().length());
            }
        });

        //set Confirm password Visibility true/false
        binding.sigUpConfPassVisibility.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //   binding.imgPassVisibility.setSelected(!binding.imgPassVisibility.isPressed());

                binding.etPassword.setTransformationMethod(null);
                binding.sigUpConfPassVisibility.setImageResource(R.drawable.visibility_off);

                binding.etPassword.setSelection(binding.etPassword.getText().length());
            }
        });


        binding.btnRegister.setOnClickListener(v -> {

            if (isValidUsername() && isValidEmail() && isValidPassword() && isValidConfirmPassword()) {
                progressDialog.show();


                String userName = binding.etUserName.getText().toString();
                String email = binding.etEmail.getText().toString();
                String password = binding.etPassword.getText().toString();
                String confirmPass = binding.etConfirmPass.getText().toString();

                auth.createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener(this, task -> {

                            if (task.isSuccessful()) {
                                progressDialog.dismiss();
                                String currentUser = auth.getCurrentUser().getUid();
                                UserModel userModel = new UserModel(userName, email, password, confirmPass);
                                database.getReference().child("Users").child(currentUser).setValue(userModel);
                                startActivity(new Intent(SignUpActivity.this, LoginActivity.class));
                                finish();
                                Toast.makeText(SignUpActivity.this, "User Created Successfully", Toast.LENGTH_SHORT).show();


                            } else {
                                Toast.makeText(SignUpActivity.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        });
            } else {
                Toast.makeText(this, "Field Validation Error", Toast.LENGTH_SHORT).show();
            }
        });


        binding.txtAlreadyAccount.setOnClickListener(v -> {
            Intent intent = new Intent(SignUpActivity.this, LoginActivity.class);
            startActivity(intent);
            finish();
        });
//        binding.btnMobileRegister.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                startActivity(new Intent(SignUpActivity.this, PhoneLoginActivity1.class));
//                finish();
//            }
//        });
    }

    private boolean isValidUsername() {
        String userName = binding.etUserName.getText().toString();
        if (userName.isEmpty()) {
            binding.etUserName.setError("Username Field Can't be Empty.!");
            return false;
        } else if (userName.contains(" ")) {
            binding.etUserName.setError("Invalid Username..! Username can't contain white spaces");
            return false;
        } else {
            return true;
        }
    }

    private boolean isValidEmail() {
        String emailCheck = "[a-zA-Z0-9._-]+@[a-z]+\\.[a-z]+";
        String email = binding.etEmail.getText().toString();
        if (email.isEmpty()) {
            binding.etEmail.setError("Email Field Can't be Empty.!");
            return false;
        } else if (!email.matches(emailCheck)) {
            binding.etEmail.setError("Invalid Email.! Please enter a valid Email");
            return false;
        } else if (email.contains(" ")) {
            binding.etEmail.setError("Invalid Email.! Email can't contain White Spaces");
            return false;
        } else {
            return true;
        }
    }

    private boolean isValidPassword() {
        String passwordCheck = "^"+"(?=.*[0-9])"+"(?=.*[a-zA-Z])"+"(?=.*[@#$%*^&+=])"+"(?=\\S+$)"+".{8,}"+"$";
        String password = binding.etPassword.getText().toString();
      if (!password.matches(passwordCheck)) {
            binding.etPassword.setError("Password Should contain a Digit , Alphabet , Special character , No white space and minimum 8 characters");
            return false;
        } else {
            return true;
        }
    }

    private boolean isValidConfirmPassword() {

        String password = binding.etPassword.getText().toString();
        String confirmPass = binding.etConfirmPass.getText().toString();
        if (!password.equals(confirmPass)) {
            binding.etConfirmPass.setError("Password does not match");
            return false;
        } else {
            return true;
        }
    }
}