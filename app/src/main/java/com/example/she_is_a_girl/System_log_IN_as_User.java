package com.example.she_is_a_girl;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.spark.submitbutton.SubmitButton;

public class System_log_IN_as_User extends AppCompatActivity {
    EditText name,password,location,PhoneNumber;
    SubmitButton UserReg;
    DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_system_log__i_n_as__user);
        databaseReference= FirebaseDatabase.getInstance().getReference("arif");

        name=findViewById(R.id.name);
        password=findViewById(R.id.password);
        location=findViewById(R.id.Location);
        PhoneNumber=findViewById(R.id.phoneNumber);
        UserReg=findViewById(R.id.button1);
        UserReg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UpdateUser();
            }
        });
    }

    private void UpdateUser() {
        String a,b,c,d;
        a=name.getText().toString();
        b=password.getText().toString();
        c=location.getText().toString();
        d=PhoneNumber.getText().toString();
        if (!TextUtils.isEmpty(a)&& !TextUtils.isEmpty(b)&& !TextUtils.isEmpty(c)&& !TextUtils.isEmpty(d)) {
            String  uid;
            uid=databaseReference.push().getKey();
            user_reg userReg=new user_reg(uid,a,b,c,d);
            databaseReference.child(uid).setValue(userReg);
            startActivity(new Intent(getApplicationContext(),UserSystem.class));
            Toast.makeText(this, "User Registered..", Toast.LENGTH_SHORT).show();

        }
        else {
            Toast.makeText(this, "Some Fields are empty..", Toast.LENGTH_SHORT).show();
        }
    }
}