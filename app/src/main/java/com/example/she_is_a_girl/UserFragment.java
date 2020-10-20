package com.example.she_is_a_girl;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import com.example.she_is_a_girl.Models.Tamim;
import com.example.she_is_a_girl.adapter.TamimAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;


public class UserFragment extends Fragment {

    ListView view1;
    List<Tamim> studentsList;
    DatabaseReference studentsRef;



    public UserFragment() {
        // Required empty public constructor
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_user, container, false);
        view1=view.findViewById(R.id.list);

        studentsList=new ArrayList<>();
        studentsRef=FirebaseDatabase.getInstance().getReference("Users");
        studentsRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                studentsList.clear();
                for (DataSnapshot ds:snapshot.getChildren()) {
                    Tamim tamim=ds.getValue(Tamim.class);
                    studentsList.add(tamim);
                }
                TamimAdapter adapter=new TamimAdapter(getActivity(),studentsList);

                view1.setAdapter(adapter);


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getActivity(), "Error", Toast.LENGTH_SHORT).show();

            }
        });



        return  view;
    }


    }
