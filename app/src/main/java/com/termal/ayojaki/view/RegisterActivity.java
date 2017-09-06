package com.termal.ayojaki.view;

import android.support.annotation.NonNull;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.widget.EditText;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.termal.ayojaki.BaseActivity;
import com.termal.ayojaki.R;
import com.termal.ayojaki.model.User;
import com.termal.ayojaki.utils.SessionManager;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.termal.ayojaki.utils.AlertDialogManager.showAlertDialog;

public class RegisterActivity extends BaseActivity {

    private static final String TAG = RegisterActivity.class.getSimpleName();

    @BindView(R.id.email)EditText inputEmail;
    @BindView(R.id.password)EditText inputPassword;
    @BindView(R.id.name)EditText inputName;
    @BindView(R.id.confirm_password)EditText inputConfirmPassword;

    private FirebaseAuth auth;
    private DatabaseReference mDatabase;

    private String email, password, name, confirm_password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        ButterKnife.bind(this);

        initFirebase();

    }

    private void initFirebase(){
        //Get Firebase auth instance
        auth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();
    }

    private boolean isValidateForm(){
        boolean result = true;

        getData();

        if (TextUtils.isEmpty(email)) {
            inputEmail.setError("Enter email address!");
            result = false;
        } else if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            inputEmail.setError("Invalid email!");
            result = false;
        } else {
            inputEmail.setError(null);
        }

        if (TextUtils.isEmpty(password)) {
            inputPassword.setError("Enter password!");
            result = false;
        } else if (password.length() < 8) {
            inputPassword.setError(getString(R.string.warning_minimum_password));
            result = false;
        } else {
            inputPassword.setError(null);
        }

        if (TextUtils.isEmpty(name)) {
            inputName.setError("Enter name!");
            result = false;
        } else {
            inputName.setError(null);
        }

        return result;
    }

    private void getData(){
        name = inputName.getText().toString();
        email = inputEmail.getText().toString().trim();
        password = inputPassword.getText().toString().trim();
        confirm_password = inputConfirmPassword.getText().toString().trim();
    }

    @OnClick(R.id.btn_login)
    public void doLogin(){
        finish();
    }

    @OnClick(R.id.btn_forget)
    public void doRemember(){

    }

    @OnClick(R.id.btn_register)
    public void doRegister(){
        getData();
        registerUser(email, password);
    }

    private void registerUser(String email, String password){

        if (!isValidateForm()){
            return;
        }

        showProgressDialog("Registering your account. Please wait...");

        //create user
        auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        // If sign in fails, display a message to the user. If sign in succeeds
                        // the auth state listener will be notified and logic to handle the
                        // signed in user can be handled in the listener.
                        if (!task.isSuccessful()) {
                            showAlertDialog(RegisterActivity.this, getString(R.string.app_name), "Registration has been failed! Please try again", false);
                        } else {
                            onAuthSuccess(task.getResult().getUser());
                            showAlertDialog(RegisterActivity.this, getString(R.string.app_name), "Your account has been registered. Please sign in with email and password.", true);
                        }
                        hideProgressDialog();
                    }
                });
    }

    private void onAuthSuccess(FirebaseUser user) {
        // Write new user
        createNewUser(user.getUid(), user.getEmail(), inputName.getText().toString());
    }

    private void createNewUser(String userId, String email, String name) {
        User user = new User(email, name);

        mDatabase.child("users").child(userId).setValue(user);
    }
}
