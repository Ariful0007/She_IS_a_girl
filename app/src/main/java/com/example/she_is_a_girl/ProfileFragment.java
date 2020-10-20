package com.example.she_is_a_girl;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import android.text.InputType;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.UUID;

import static android.app.Activity.RESULT_OK;


public class ProfileFragment extends Fragment {
    FirebaseStorage firebaseStorage;
    FirebaseAuth firebaseAuth;
    FirebaseUser user;
    ImageView AVATER,coverImage;
    TextView avater_emailo,avater_phone,avater_name;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference,databaseReference2;
    FloatingActionButton flt;
    ProgressDialog progressDialog;
    private static  final int CAMERA_REQUEST_CODE=100;
    private static  final int STROAGE_REQUEST_CODE=200;
    private static  final int IMAGE_PIC_GALARY_CODE=300;
    private static  final int IMAGE_PIC_CODE=400;
    String cameraPermission[];
    String stroagePermissiion[];

    Uri image_uri,image_profile;
    String profileCoverPhoto;
    StorageReference storageReference;
    String stroagePath="User_Profilr_Cover_Image/";


    public ProfileFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_profile,container,false);
        firebaseStorage= FirebaseStorage.getInstance();
        storageReference=firebaseStorage.getReference("User");
        databaseReference= FirebaseDatabase.getInstance().getReference("Users");
        databaseReference2= FirebaseDatabase.getInstance().getReference("Number");
        cameraPermission=new String[]{Manifest.permission.CAMERA,Manifest.permission.WRITE_EXTERNAL_STORAGE};
        stroagePermissiion=new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE};
        avater_name=view.findViewById(R.id.avater_1);
        avater_emailo=view.findViewById(R.id.avater_2);
        avater_phone=view.findViewById(R.id.avater_3);
        flt=view.findViewById(R.id.floating);
        coverImage=view.findViewById(R.id.coverImage);
        progressDialog=new ProgressDialog(getActivity());
        flt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showEditProfile();
            }
        });
        return  view;
    }
    private void showEditProfile() {
        String options[]={"Edit CoverImage","Edit ProfileImage","Edit Name","Edit phoneNumber"};
        AlertDialog.Builder builder=new AlertDialog.Builder(getActivity());
        builder.setTitle("Choose Action");
        builder.setItems(options,new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (which==0) {
                    progressDialog.setMessage("Uploading Profile Picture");
                    profileCoverPhoto="image";
                    showGalary();


                }else if(which==1) {
                    progressDialog.setMessage("Uploading cover Picture");
                    profileCoverPhoto="cover";
                    showGalary();


                }
                else if (which==2) {
                    progressDialog.setMessage("Uploading Name ");
                    upDateName();

                }else if (which==3) {
                    progressDialog.setMessage("Uploading PhoneNumber");
                    ShowNumberDialouge();

                }

            }
        });
        builder.create().show();

    }

    private void ShowNumberDialouge() {
        AlertDialog.Builder builder=new AlertDialog.Builder(getActivity());
        builder.setTitle("Update PhoneNumber ");
        LinearLayout linearLayout=new LinearLayout(getActivity());
        final EditText editText2=new EditText(getActivity());
        editText2.setHint("PhoneNumber");
        editText2.setInputType(InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS);
        editText2.setEms(10);
        linearLayout.addView(editText2);
        linearLayout.setPadding(10,10,10,10);
        builder.setView(linearLayout);
        builder.setPositiveButton("Update", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String number_update=editText2.getText().toString().trim();
                if (!TextUtils.isEmpty(number_update)) {
                    // updateProcedure(name_update);
                    avater_phone.setText("");
                    String key=databaseReference2.push().getKey();
                    NumberUpdatte numberUpdatte=new NumberUpdatte(key,number_update);
                    databaseReference2.child(key).setValue(numberUpdatte);
                    avater_phone.setText(number_update);
                    Toast.makeText(getActivity(), "Updated", Toast.LENGTH_SHORT).show();

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

    private void upDateName() {
        AlertDialog.Builder builder=new AlertDialog.Builder(getActivity());
        builder.setTitle("Update Name ");
        LinearLayout linearLayout=new LinearLayout(getActivity());
        final EditText editText1=new EditText(getActivity());
        editText1.setHint("username");
        editText1.setInputType(InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS);
        editText1.setEms(10);
        linearLayout.addView(editText1);
        linearLayout.setPadding(10,10,10,10);
        builder.setView(linearLayout);
        builder.setPositiveButton("Update", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String name_update=editText1.getText().toString().trim();
                if (!TextUtils.isEmpty(name_update)) {
                   avater_name.setText("");
                   String key=databaseReference.push().getKey();
                   Update_name updateName=new Update_name(key,name_update);
                   databaseReference.child(key).setValue(updateName);
                   avater_name.setText(name_update);
                    Toast.makeText(getActivity(), "Updated", Toast.LENGTH_SHORT).show();


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

    private void showGalary() {
        Intent intent=new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent,1);
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode==RESULT_OK && requestCode==1 && data!=null && data.getData()!=null) {
            if(profileCoverPhoto=="image") {
                image_uri = data.getData();
                coverImage.setImageURI(image_uri);
                uploadProfile();
            }

        }
    }


    private void uploadProfile() {
        final ProgressDialog progressDialog=new ProgressDialog(getActivity());
        progressDialog.setTitle("uploading-----");
        progressDialog.show();
        final String Randomkey= UUID.randomUUID().toString();


        StorageReference riversRef = storageReference.child("images/"+Randomkey);

        riversRef.putFile(image_uri)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        progressDialog.dismiss();
                        Toast.makeText(getActivity(), "Complete", Toast.LENGTH_SHORT).show();

                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception exception) {
                        // Handle unsuccessful uploads
                        // ...
                        progressDialog.dismiss();
                        Toast.makeText(getActivity(), "Failed", Toast.LENGTH_SHORT).show();
                    }
                }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                double parcentage=(100*taskSnapshot.getBytesTransferred()/taskSnapshot.getTotalByteCount());
                progressDialog.setMessage("Percentage "+(int)+parcentage+" %");

            }
        });


    }

}


