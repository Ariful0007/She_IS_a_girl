package com.example.she_is_a_girl;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.spark.submitbutton.SubmitButton;

import java.util.HashMap;

public class Register extends AppCompatActivity {
    EditText email,password;
    ProgressDialog progressDialog;
    Button register;
    private FirebaseAuth mAuth;
    TextView have_not;
    DatabaseReference reference;
    FirebaseDatabase firebaseDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        email=findViewById(R.id.Email_Main);
        password=findViewById(R.id.Password_Main);
        register=findViewById(R.id.Register);
        mAuth=FirebaseAuth.getInstance();

        progressDialog=new ProgressDialog(this);
        progressDialog.setMessage("Registering User....");

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email1=email.getText().toString().trim();
                String password1=password.getText().toString().trim();
                if (!Patterns.EMAIL_ADDRESS.matcher(email1).matches()) {
                    email.setError("Invalid Email.");
                    email.setFocusable(true);

                }
                else if(password1.length()<6) {
                    password.setError("Password must be 6 word...");
                    password.setFocusable(true);
                }
                else {
                    RegisterUser(email1,password1);
                }

            }
        });

    }

    private void RegisterUser(String email1, String password1) {
        progressDialog.show();
        mAuth.createUserWithEmailAndPassword(email1, password1)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            progressDialog.dismiss();;
                            FirebaseUser user = mAuth.getCurrentUser();
                            String email_h=user.getEmail();
                            String uid=user.getUid();
                            HashMap<Object,String> hashMap=new HashMap<>();
                            hashMap.put("email",email_h);
                            hashMap.put("uid",uid);
                            hashMap.put("name","");
                            hashMap.put("number","");
                            hashMap.put("image","");
                            FirebaseDatabase firebaseDatabase=FirebaseDatabase.getInstance();
                            DatabaseReference reference=firebaseDatabase.getReference("ma");

                            Toast.makeText(Register.this, "Registered....\n"+user.getEmail(), Toast.LENGTH_SHORT).show();
                            //startActivity(new Intent(getApplicationContext(),profileActivity.class));
                            finish();
                            return;
                        } else {
                            progressDialog.dismiss();;
                            Toast.makeText(Register.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                        }

                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                progressDialog.dismiss();;
                Toast.makeText(Register.this, ""+e.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return super.onSupportNavigateUp();

    }


}
