package com.example.quikr_dummy;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {
    FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mAuth = FirebaseAuth.getInstance();
    }

    @Override
    public void onStart() {
        super.onStart();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        updateUI(currentUser);
    }

    public void updateUI(FirebaseUser user)
    {
        if(user==null)
        {
            Intent intent = new Intent(this,LoginActivity.class);
            startActivity(intent);
        }
        else
        {
//            Log.e("login",user.getUid()+user.getEmail());
//            Intent intent=new Intent(this,list.class);
//            intent.putExtra("id",user.getUid());
//            startActivity(intent);
//            finish();
        }
    }

    public void buy(View view)
    {
        Intent intent=new Intent(this,list.class);
        intent.putExtra("id",mAuth.getCurrentUser().getUid());
        startActivity(intent);
    }

    public void sell(View view)
    {
        Intent intent=new Intent(this,sell.class);
        //intent.putExtra("id",mAuth.getCurrentUser().getUid());
        startActivity(intent);
    }

    public void signout(View view)
    {
        FirebaseAuth.getInstance().signOut();
        finish();
    }
}
