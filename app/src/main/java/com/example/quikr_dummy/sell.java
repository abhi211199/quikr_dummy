package com.example.quikr_dummy;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

public class sell extends AppCompatActivity {

    TextView name,desc,price;
    StorageReference storageRef;
    FirebaseAuth mAuth;
    String id="124",img=null,vid=null,imgurl=null,vidurl=null;
    Uri URI1,URI2;
    ProgressDialog progressDialog;
    FirebaseFirestore db;
//    FirebaseUser currentUser;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sell);
        name=findViewById(R.id.name);
        desc=findViewById(R.id.desc);
        price=findViewById(R.id.price);
        storageRef = FirebaseStorage.getInstance().getReference();
        progressDialog=new ProgressDialog(this );
        db = FirebaseFirestore.getInstance();
    }

//    @Override
//    public void onStart() {
//        super.onStart();
//        currentUser = mAuth.getCurrentUser();
//        //id=currentUser.getUid();
//        //updateUI(currentUser);
//    }

    public void chooseVideo(View view)
    {
       Intent intent = new Intent(Intent.ACTION_PICK,
                android.provider.MediaStore.Video.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, 2);
    }

    public void chooseImage(View view)
    {
        Intent intent = new Intent(Intent.ACTION_PICK,
                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, 1);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        try {

            if (requestCode == 1 && resultCode == RESULT_OK
                    && null != data) {
                URI1 = data.getData();

                File file= new File(URI1.getPath());
                img = file.getName();
                Log.e("uri",img);
            }
            else if (requestCode == 2 && resultCode == RESULT_OK
                    && null != data) {
                URI2 = data.getData();
                File file= new File(URI2.getPath());
                vid = file.getName();
                Log.e("uri",vid);
            }
        } catch (Exception e) {
            Toast.makeText(this, "Please try again", Toast.LENGTH_LONG)
                    .show();
        }

    }

    public void uploadVideo()
    {
        progressDialog.setMessage("Uploading Video...");
        final StorageReference ref = storageRef.child("videos/"+id+"/"+vid+".mp4");
        UploadTask uploadTask = ref.putFile(URI2);
//        uploadTask = ref.putFile(file);

        Task<Uri> urlTask = uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
            @Override
            public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                if (!task.isSuccessful()) {
                    throw task.getException();
                }
                vidurl=ref.getDownloadUrl().toString();
                Log.e("aa",ref.getDownloadUrl()+"");
                // Continue with the task to get the download URL
                return ref.getDownloadUrl();
            }
        }).addOnCompleteListener(new OnCompleteListener<Uri>() {
            @Override
            public void onComplete(@NonNull Task<Uri> task) {
                if (task.isSuccessful()) {
                    Uri downloadUri = task.getResult();
                    vidurl=downloadUri.toString();
                    Log.e("bb",downloadUri+"");
                    uploadImage();
                } else {
                    // Handle failures
                    // ...
                }
            }
        });
    }

    public void uploadImage()
    {
        progressDialog.setMessage("Uploading Image...");
        final StorageReference ref = storageRef.child("images/"+id+"/"+img+".jpg");
        UploadTask uploadTask = ref.putFile(URI1);
//        uploadTask = ref.putFile(file);

        Task<Uri> urlTask = uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
            @Override
            public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                if (!task.isSuccessful()) {
                    throw task.getException();
                }
                imgurl=ref.getDownloadUrl().toString();
                Log.e("aa",ref.getDownloadUrl()+"");
                // Continue with the task to get the download URL
                return ref.getDownloadUrl();
            }
        }).addOnCompleteListener(new OnCompleteListener<Uri>() {
            @Override
            public void onComplete(@NonNull Task<Uri> task) {
                if (task.isSuccessful()) {
                    Uri downloadUri = task.getResult();
                    imgurl=downloadUri.toString();
                    Log.e("aa",downloadUri+"");
                    db1();
                } else {
                    // Handle failures
                    // ...
                }
            }
        });
    }

    public void db1()
    {
        progressDialog.setMessage("Uploading Data...");
        Map<String, Object> city = new HashMap<>();
        city.put("name",name.getText().toString());
        city.put("desc", desc.getText().toString());
        city.put("price", price.getText().toString());
        city.put("vid",vidurl);
        city.put("img",imgurl);

//        DocumentReference newCityRef = db.collection("items").document();
//        newCityRef.set(city);
        String ids = db.collection("items").document().getId();
        db.collection("items").document(ids)
                .set(city)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        progressDialog.hide();
                        Toast.makeText(sell.this,"Upload Complete!",Toast.LENGTH_LONG).show();
                       // Log.d(TAG, "DocumentSnapshot successfully written!");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                      //  Log.w(TAG, "Error writing document", e);
                    }
                });


    }

    public void submit(View view)
    {
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Uploading...");
        progressDialog.show();
        uploadVideo();
//        db1();

    }

}
