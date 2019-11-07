package com.example.quikr_dummy;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class Profile extends AppCompatActivity {
    String uid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        Intent intent=getIntent();
        uid=intent.getStringExtra("id");
    }

//    public void onAuthStateChanged (FirebaseAuth auth)
//    {
//
//    }
    public void SignInResultNotifier(View view)
    {
        FirebaseAuth.getInstance().signOut();
        finish();
    }
    public void signInAnonymously(View view) {
//        Toast.makeText(this, "Signing in...", Toast.LENGTH_SHORT).show();
        FirebaseAuth.getInstance().signInAnonymously().addOnSuccessListener(this, new OnSuccessListener<AuthResult>() {

            @Override
            public void onSuccess(AuthResult result) {
                Log.e("login",FirebaseAuth.getInstance().getCurrentUser().getUid()+FirebaseAuth.getInstance().getCurrentUser().getEmail());
                FirebaseAuth.getInstance().signOut();
            }
        });
//        .addOnCompleteListener(this);
    }
}
