package com.termal.ayojaki.view;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.widget.EditText;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.termal.ayojaki.BaseActivity;
import com.termal.ayojaki.MainActivity;
import com.termal.ayojaki.R;
import com.termal.ayojaki.utils.SessionManager;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.termal.ayojaki.utils.AlertDialogManager.showAlertDialog;

public class LoginActivity extends BaseActivity {

    private static final String TAG = LoginActivity.class.getSimpleName();

    @BindView(R.id.email)EditText inputEmail;
    @BindView(R.id.password)EditText inputPassword;

    private FirebaseAuth auth;

    private String email, password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //Get Firebase auth instance
        auth = FirebaseAuth.getInstance();

        if (auth.getCurrentUser() != null) {
            startActivity(new Intent(LoginActivity.this, MainActivity.class));
            finish();
        }

        ButterKnife.bind(this);

    }

    private void getData(){
        email = inputEmail.getText().toString().trim();
        password = inputPassword.getText().toString().trim();
    }

    @OnClick(R.id.btn_login)
    public void doLogin(){
        getData();

        authenticateUser(email, password);
    }

    @OnClick(R.id.btn_forget)
    public void doRemember(){
        startActivity(new Intent(LoginActivity.this, LupaPasswordActivity.class));
    }

    @OnClick(R.id.btn_register)
    public void doRegister(){
        startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
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
        } else if (password.length() < 8){
            inputPassword.setError(getString(R.string.warning_minimum_password));
            result = false;
        } else {
            inputPassword.setError(null);
        }

        return result;
    }

    private void authenticateUser(final String email, String password){
        //Get Firebase auth instance
        auth = FirebaseAuth.getInstance();

        if (!isValidateForm()){
            return;
        }

        showProgressDialog("Login. Please wait...");

        //authenticate user
        auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        // If sign in fails, display a message to the user. If sign in succeeds
                        // the auth state listener will be notified and logic to handle the
                        // signed in user can be handled in the listener.
                        hideProgressDialog();
                        if (!task.isSuccessful()) {
                            showAlertDialog(LoginActivity.this, getString(R.string.app_name), getString(R.string.auth_failed), false);
                        } else {
                            startActivity(new Intent(LoginActivity.this, MainActivity.class));
                            onSuccessLogin(email);
                            finish();
                        }
                    }
                });
    }

    private void onSuccessLogin(String result){
        SessionManager.save("login", true);
        SessionManager.save("email", result);
    }
}
