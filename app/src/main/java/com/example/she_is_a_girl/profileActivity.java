package com.example.she_is_a_girl;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class profileActivity extends AppCompatActivity {
    FirebaseAuth firebaseAuthh;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        firebaseAuthh=FirebaseAuth.getInstance();
        BottomNavigationView bottomNavigationView=findViewById(R.id.second);
        bottomNavigationView.setOnNavigationItemSelectedListener(selectlistner);
        HomeFragment fragment1=new HomeFragment();
        FragmentTransaction ft1=getSupportFragmentManager().beginTransaction();
        ft1.replace(R.id.content,fragment1,"");
        ft1.commit();
    }
    private  BottomNavigationView.OnNavigationItemSelectedListener selectlistner=
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                    switch (menuItem.getItemId()) {
                        case  R.id.home_1:
                            HomeFragment fragment1=new HomeFragment();
                            FragmentTransaction ft1=getSupportFragmentManager().beginTransaction();
                            ft1.replace(R.id.content,fragment1,"");
                            ft1.commit();

                            return true;
                        case  R.id.profile:
                            ProfileFragment fragment2=new ProfileFragment();
                            FragmentTransaction ft2=getSupportFragmentManager().beginTransaction();
                            ft2.replace(R.id.content,fragment2,"");
                            ft2.commit();
                            // startActivity(new Intent(getApplicationContext(),Profile_MainActivity3.class));
                            return true;
                        case  R.id.users:
                            UserFragment fragment3=new UserFragment();
                            FragmentTransaction ft3=getSupportFragmentManager().beginTransaction();
                            ft3.replace(R.id.content,fragment3,"");
                            ft3.commit();
                            return true;
                        case  R.id.chat:
                            ChatListFragment fragment4=new ChatListFragment();
                            FragmentTransaction ft4=getSupportFragmentManager().beginTransaction();
                            ft4.replace(R.id.content,fragment4,"");
                            ft4.commit();
                            return true;

                    }
                    return false;
                }
            };

    private void CheckUserStatus()
    {
        FirebaseUser user=firebaseAuthh.getCurrentUser();
        if (user!=null) {

        }
        else {
            startActivity(new Intent(getApplicationContext(),BasicActivity2.class));
        }
    }

    @Override
    protected void onStart() {
        CheckUserStatus();
        super.onStart();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int  id=item.getItemId();
        if(id==R.id.logout2) {
            firebaseAuthh.signOut();
            CheckUserStatus();
        }

        return super.onOptionsItemSelected(item);
    }
}