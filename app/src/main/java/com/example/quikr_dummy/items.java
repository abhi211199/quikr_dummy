package com.example.quikr_dummy;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.VideoView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.SetOptions;
import com.squareup.picasso.Picasso;

import java.util.HashMap;
import java.util.Map;

public class items extends AppCompatActivity {
    String nam,des,pric,im,vi,comments,id;
    TextView name,desc,price;
    EditText add;
    ImageView img;
    VideoView video;
    FirebaseFirestore  db;
    Map<String,String> m;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_items);
        Intent intent=getIntent();
        nam=intent.getStringExtra("name");
        des=intent.getStringExtra("desc");
        pric=intent.getStringExtra("price");
        im=intent.getStringExtra("img");
        vi=intent.getStringExtra("vid");
        comments=intent.getStringExtra("comments");
        id=intent.getStringExtra("id");
//        intent.get
        name=findViewById(R.id.name);
        desc=findViewById(R.id.desc);
        price=findViewById(R.id.price);
        img=findViewById(R.id.image);
        video=findViewById(R.id.video);
        add=findViewById(R.id.edit);
        db = FirebaseFirestore.getInstance();
        fill();
    }

    public void fill()
    {
        name.setText("Name: "+nam);
        desc.setText("Description: "+des);
        price.setText("Price: "+pric);
        Picasso
                .get()
                .load(im)
                .into(img);
        video.setVideoPath(vi);
        video.start();

    }

    public void comment(View view)
    {
        new AlertDialog.Builder(this)
                .setTitle("Comments")
                .setMessage(comments)
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                    }
                })
                .setNegativeButton(android.R.string.no, null)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();
    }

    public void add(View view)
    {
        Map<String, String> data = new HashMap<>();
        data.put(id,add.getText().toString());
//        db.collection("items").document("3dRNxwLsqr2KxWhT3eZk")
//                .set( data,SetOptions.merge())
//                .addOnSuccessListener(new OnSuccessListener<Void>() {
//                    @Override
//                    public void onSuccess(Void aVoid) {
//                     //   Log.d(TAG, "DocumentSnapshot successfully written!");
//                    }
//                })
//                .addOnFailureListener(new OnFailureListener() {
//                    @Override
//                    public void onFailure(@NonNull Exception e) {
//                       // Log.w(TAG, "Error writing document", e);
//                    }
//                });
        db.collection("items").document("3dRNxwLsqr2KxWhT3eZk")
                .update(
//                        "comments", data,
                        "comments."+id, add.getText().toString()
                );
    }

}
