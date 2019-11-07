package com.example.quikr_dummy;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;

public class LoginActivity extends AppCompatActivity {

    FirebaseAuth mAuth;
    TextView name,email1,email2,pass1,pass2,pass3;
    ProgressDialog progressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mAuth = FirebaseAuth.getInstance();
        name=findViewById(R.id.name);
        email1=findViewById(R.id.email);
        email2=findViewById(R.id.email2);
        pass1=findViewById(R.id.pass);
        pass2=findViewById(R.id.pass1);
        pass3=findViewById(R.id.pass2);


    }

    @Override
    public void onStart() {
        super.onStart();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        updateUI(currentUser);
    }

    public void signUp(View  View) {
        progressBar.setCancelable(false);
        progressBar.show();
        if(name.getText()!=null && pass1.getText().toString().equals(pass2.getText().toString()) && pass1.getText().length()>6)
        {
            mAuth.createUserWithEmailAndPassword(email1.getText().toString(), pass1.getText().toString())
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                // Sign in success, update UI with the signed-in user's information
                                Log.e("signup", "createUserWithEmail:success");
                                progressBar.hide();
                                Toast.makeText(LoginActivity.this, "Success",
                                        Toast.LENGTH_SHORT).show();
                                FirebaseUser user = mAuth.getCurrentUser();
                                updateUI(user);
                            } else {
                                // If sign in fails, display a message to the user.
                                Log.e("signup", "createUserWithEmail:failure", task.getException());
                                progressBar.hide();
                                Toast.makeText(LoginActivity.this, "Authentication failed.",
                                        Toast.LENGTH_SHORT).show();
                                updateUI(null);
                            }
                        }
                    });
        }
        else
            Toast.makeText(LoginActivity.this,"Please fill the form properly",Toast.LENGTH_LONG).show();
    }

    public void signIn(View view)
    {
        mAuth.signInWithEmailAndPassword(email2.getText().toString(), pass3.getText().toString())
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.e("signin", "signInWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            updateUI(user);
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.e("signin", "signInWithEmail:failure", task.getException());
                            Toast.makeText(LoginActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                            updateUI(null);
                        }
                    }
                });
    }

    public void updateUI(FirebaseUser user)
    {
        if(user==null)
            Log.e("login","not");
        else
        {
            Log.e("login",user.getUid()+user.getEmail());
            Intent intent=new Intent(this,Profile.class);
            intent.putExtra("id",user.getUid());
            startActivity(intent);
            finish();
        }
    }

}
