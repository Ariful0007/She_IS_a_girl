package com.example.she_is_a_girl;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

public class ChatActivity extends AppCompatActivity {
    Toolbar toolbar;
    RecyclerView recyclerView;
    EditText message;
    ImageButton send;
    TextView user_name,online;
    ImageView imageView;
    FirebaseAuth firebaseAuth;
    String HisUid;
    String MyUid;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference UserRef;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        toolbar=findViewById(R.id.toolbar);
        toolbar.setTitle("");
        recyclerView=findViewById(R.id.list_11);
        message=findViewById(R.id.messageEt);
        send=findViewById(R.id.sendbtn);
        user_name=findViewById(R.id.profile_name);
        online=findViewById(R.id.online);
        imageView=findViewById(R.id.profileImage);
        Intent intent=getIntent();
        HisUid=intent.getStringExtra("UId");
        //firebase

        firebaseAuth= FirebaseAuth.getInstance();
        firebaseDatabase=FirebaseDatabase.getInstance();
        UserRef=firebaseDatabase.getReference("Users");
        Query query=UserRef.orderByChild("id").equalTo(HisUid);
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot ds:dataSnapshot.getChildren())
                {
                    String name=" "+ds.child("name").getValue();
                    user_name.setText(name);

                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String message1=message.getText().toString().trim();
                //condition
                if (TextUtils.isEmpty(message1)) {
                    Toast.makeText(ChatActivity.this, "Write a message", Toast.LENGTH_SHORT).show();
                }else {
                    SendMessage(message1);

                }
            }
        });


    }

    private void SendMessage(String message1) {
       DatabaseReference databaseReference=FirebaseDatabase.getInstance().getReference();
        HashMap<String,Object>hashMap=new HashMap<>();
        hashMap.put("sender",MyUid);
        hashMap.put("recevier",HisUid);
        hashMap.put("message",message1);
        databaseReference.child("Chats").push().setValue(hashMap);
        message.setText(null);
        Toast.makeText(this, "Message Send", Toast.LENGTH_SHORT).show();


    }

    @Override
    protected void onStart() {
        CheckUserStatus();
        super.onStart();
    }

    private void CheckUserStatus()
    {
        FirebaseUser user=firebaseAuth.getCurrentUser();
        if (user!=null) {
            MyUid=user.getUid();


        }
        else {
            startActivity(new Intent(getApplicationContext(),BasicActivity2.class));
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main,menu);
        menu.findItem(R.id.search_item).setVisible(false);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int  id=item.getItemId();
        if(id==R.id.logout2) {
            firebaseAuth.signOut();
            CheckUserStatus();
        }
        return super.onOptionsItemSelected(item);
    }
}