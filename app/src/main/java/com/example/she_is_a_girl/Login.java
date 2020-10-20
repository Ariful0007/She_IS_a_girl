package com.example.she_is_a_girl;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.spark.submitbutton.SubmitButton;

import java.util.HashMap;

public class Login extends AppCompatActivity {
    private static  final int RC_SIGN_IN=100;
    GoogleSignInClient mGoogleSignInClient;
    EditText username, password;
    Button okey;
    private FirebaseAuth mAuth;
    TextView have_not,recoverPassword;
    ProgressDialog progressDialog;
    SignInButton google;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        //google
        // Configure Google Sign In
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();
        mGoogleSignInClient= GoogleSignIn.getClient(this,gso);
        //firebase

        mAuth = FirebaseAuth.getInstance();
        username = findViewById(R.id.Email_Log);
        password = findViewById(R.id.Password_Log);
        okey = findViewById(R.id.Login);
        progressDialog=new ProgressDialog(this);
        recoverPassword=findViewById(R.id.recoverPassword);
        google=findViewById(R.id.googleLOgin);
        google.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent signInIntent = mGoogleSignInClient.getSignInIntent();
                startActivityForResult(signInIntent, RC_SIGN_IN);

            }
        });
        recoverPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //intent
                ShoeRecoverPasswordDialog();
            }
        });

        okey.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email1 = username.getText().toString().trim();
                String password1 = password.getText().toString().trim();
                if (!Patterns.EMAIL_ADDRESS.matcher(email1).matches()) {
                    username.setError("Invalid Email.");
                    username.setFocusable(true);

                } else {
                    LoginUser(email1, password1);
                }
            }
        });
    }

    private void ShoeRecoverPasswordDialog() {
        AlertDialog.Builder builder=new AlertDialog.Builder(this);
        builder.setTitle("Password Recover ");
        LinearLayout linearLayout=new LinearLayout(this);
        final EditText editText=new EditText(this);
        editText.setHint("email");
        editText.setInputType(InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS);
        editText.setEms(10);
        linearLayout.addView(editText);
        linearLayout.setPadding(10,10,10,10);
        builder.setView(linearLayout);
        builder.setPositiveButton("Recover", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String Email_recover=editText.getText().toString().trim();
                if (!TextUtils.isEmpty(Email_recover)) {
                    startRecovery(Email_recover);

                }

            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                progressDialog.dismiss();

            }
        });
        builder.create().show();

    }

    private void startRecovery(String email_recover)
    {
        progressDialog.setMessage("Sending email.....");
        progressDialog.show();
        mAuth.sendPasswordResetEmail(email_recover).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                progressDialog.dismiss();
                if(task.isSuccessful()) {
                    Toast.makeText(Login.this, "Email send", Toast.LENGTH_SHORT).show();
                }
                else {
                    Toast.makeText(Login.this, "Failed", Toast.LENGTH_SHORT).show();
                }

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(Login.this, ""+e.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });
    }


    private void LoginUser(String email1, String password1) {
        progressDialog.setMessage("login.....");
        progressDialog.show();
        mAuth.signInWithEmailAndPassword(email1, password1)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information

                            FirebaseUser user = mAuth.getCurrentUser();

                            progressDialog.dismiss();
                            startActivity(new Intent(getApplicationContext(),profileActivity.class));

                        } else {
                            // If sign in fails, display a message to the user.
                            Toast.makeText(Login.this, "Login Failed", Toast.LENGTH_SHORT).show();
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(Login.this, ""+e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });


    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                // Google Sign In was successful, authenticate with Firebase
                GoogleSignInAccount account = task.getResult(ApiException.class);
                //Log.d(TAG, "firebaseAuthWithGoogle:" + account.getId());
                firebaseAuthWithGoogle(account.getIdToken());
            } catch (ApiException e) {
                // Google Sign In failed, update UI appropriately
                //  Log.w(TAG, "Google sign in failed", e);
                // ...
            }
        }
    }
    private void firebaseAuthWithGoogle(String idToken) {
        AuthCredential credential = GoogleAuthProvider.getCredential(idToken, null);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            //Log.d(TAG, "signInWithCredential:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            if (task.getResult().getAdditionalUserInfo().isNewUser()) {

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
                                reference.child(uid).setValue(hashMap);
                            }

                            Toast.makeText(Login.this, "Login done..", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(getApplicationContext(),profileActivity.class));
                            //updateUI(user);
                        } else {
                            // If sign in fails, display a message to the user.
                            // Log.w(TAG, "signInWithCredential:failure", task.getException());
                            // Snackbar.make(findViewById(R.id.register_main), "Authentication Failed.", Snackbar.LENGTH_SHORT).show();
                            //updateUI(null);
                            Toast.makeText(Login.this, "Login Failed", Toast.LENGTH_SHORT).show();
                        }

                        // ...
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(Login.this, ""+e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}