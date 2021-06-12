package com.example.assignmentalerts;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.assignmentalerts.databinding.ActivityPhoneLogin2Binding;
import com.example.assignmentalerts.databinding.ActivityPhonelogin1Binding;
import com.hbb20.CountryCodePicker;

public class PhoneLoginActivity1 extends AppCompatActivity {
    CountryCodePicker ccp;
    EditText etPhoneNumber;
    Button btnSendOTP;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phonelogin1);
        etPhoneNumber = findViewById(R.id.etPhoneNumber);
        btnSendOTP = findViewById(R.id.btnSendOTP);
        ccp = findViewById(R.id.ccp);
        ccp.registerCarrierNumberEditText(etPhoneNumber);

        btnSendOTP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PhoneLoginActivity1.this,VerifyPhoneActivity.class);
                intent.putExtra("mobile",ccp.getFullNumberWithPlus().replace(" ",""));
                startActivity(intent);
            }
        });
    }
}