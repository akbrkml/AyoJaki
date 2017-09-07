package com.termal.ayojaki;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.termal.ayojaki.view.JalanActivity;
import com.termal.ayojaki.view.LoginActivity;
import com.termal.ayojaki.view.PejalanActivity;
import com.termal.ayojaki.view.PoinActivity;
import com.termal.ayojaki.view.ProfilActivity;
import com.termal.ayojaki.view.ProgressActivity;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

    private FirebaseAuth.AuthStateListener authListener;
    private FirebaseAuth auth;
    private FirebaseUser user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        authUserListener();
    }

    public void profilButton(View view)
    {
        Intent intent = new  Intent(MainActivity.this, ProfilActivity.class);
        startActivity(intent);
    }

    public void jalanButton(View view)
    {
        Intent intent = new  Intent(MainActivity.this, JalanActivity.class);
        startActivity(intent);
    }

    public void pejalanButton(View view)
    {
        Intent intent = new  Intent(MainActivity.this, PejalanActivity.class);
        startActivity(intent);
    }

    public void progressButton(View view)
    {
        Intent intent = new  Intent(MainActivity.this, ProgressActivity.class);
        startActivity(intent);
    }

    public void poinButton(View view)
    {
        Intent intent = new  Intent(MainActivity.this, PoinActivity.class);
        startActivity(intent);
    }

    public void keluarButton(View view)
    {
        new AlertDialog.Builder(this)
                .setMessage("Apa anda ingin Keluar?")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        auth.signOut();
                    }
                })
                .setNegativeButton("No", null)
                .show();
    }

    @Override
    public void onStart() {
        super.onStart();
        auth.addAuthStateListener(authListener);
    }

    @Override
    public void onStop() {
        super.onStop();
        if (authListener != null) {
            auth.removeAuthStateListener(authListener);
        }
    }

    private void authUserListener(){
        //get firebase auth instance
        auth = FirebaseAuth.getInstance();

        //get current user
        user = FirebaseAuth.getInstance().getCurrentUser();

        authListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                user = firebaseAuth.getCurrentUser();
                if (user == null) {
                    // user auth state is changed - user is null
                    // launch login activity
                    startActivity(new Intent(MainActivity.this, LoginActivity.class));
                    finish();
                } else {
//                    email.setText(user.getEmail());
//                    name.setText(SessionManager.grabString("name"));
                }
            }
        };
    }
}
