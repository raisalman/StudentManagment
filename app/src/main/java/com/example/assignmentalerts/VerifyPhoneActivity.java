package com.example.assignmentalerts;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.assignmentalerts.databinding.ActivityVerifyPhoneBinding;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;

import org.jetbrains.annotations.NotNull;

import java.util.Objects;
import java.util.concurrent.TimeUnit;

public class VerifyPhoneActivity extends AppCompatActivity {

    ActivityVerifyPhoneBinding binding;
    FirebaseAuth auth;
    private String mVerificationId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verify_phone);
        auth = FirebaseAuth.getInstance();
        String phoneNumber = getIntent().getStringExtra("mobile");

        generateOTP(phoneNumber);

        binding.btnVerify.setOnClickListener(new View.OnClickListener() {

            final String verifyCode = Objects.requireNonNull(binding.etVerifyCode.getText()).toString();
            @Override
            public void onClick(View v) {
                if(!verifyCode.isEmpty()){
                    PhoneAuthCredential credential = PhoneAuthProvider.getCredential(mVerificationId,verifyCode);
                    signInWithPhoneAuthCredential(credential);
                }else{
                    Toast.makeText(VerifyPhoneActivity.this, "Invalid Verification Code", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void generateOTP(String phone) {
        PhoneAuthOptions options =
                PhoneAuthOptions.newBuilder(auth)
                        .setPhoneNumber(phone)       // Phone number to verify
                        .setTimeout(60L, TimeUnit.SECONDS) // Timeout and unit
                        .setActivity(this)                 // Activity (for callback binding)
                        .setCallbacks(mCallbacks)          // OnVerificationStateChangedCallbacks
                        .build();
        PhoneAuthProvider.verifyPhoneNumber(options);
    }


  PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {


        @Override
        public void onVerificationCompleted(@NotNull PhoneAuthCredential credential) {
            Toast.makeText(VerifyPhoneActivity.this, "Phone Number Verified Successfully", Toast.LENGTH_SHORT).show();

            signInWithPhoneAuthCredential(credential);
        }

        @Override
        public void onVerificationFailed(FirebaseException e) {

            Toast.makeText(VerifyPhoneActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onCodeSent(@NonNull String verificationId,
                @NonNull PhoneAuthProvider.ForceResendingToken token) {

            mVerificationId = verificationId;
        }
    };

    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {
        auth.signInWithCredential(credential)
                .addOnCompleteListener(this, task -> {
                    if (task.isSuccessful()) {
                        Intent intent = new Intent(VerifyPhoneActivity.this,PhoneLoginActivity2.class);
                        startActivity(intent);
                        finish();
                    } else {
                        Toast.makeText(VerifyPhoneActivity.this, "Sign In Code Error ", Toast.LENGTH_SHORT).show();
                    }
                });
    }


}