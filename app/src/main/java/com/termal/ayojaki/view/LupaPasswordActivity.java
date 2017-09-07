package com.termal.ayojaki.view;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.TextView;
import android.widget.Toast;

import com.termal.ayojaki.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class LupaPasswordActivity extends AppCompatActivity {

    @BindView(R.id.email)
    TextView inputEmail;

    private String email;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lupa_password);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.btn_lupa)
    public void sendEmailReset(){
        if (!isValidateForm()){
            return;
        }

        Toast.makeText(this, "Kami telah mengirimkan form reset password ke email anda", Toast.LENGTH_SHORT).show();

    }

    @OnClick(R.id.btn_login)
    public void doLogin(){
        startActivity(new Intent(LupaPasswordActivity.this, LoginActivity.class));
    }

    @OnClick(R.id.btn_register)
    public void doRegister(){
        startActivity(new Intent(LupaPasswordActivity.this, RegisterActivity.class));
    }

    private boolean isValidateForm() {
        boolean result = true;

        email = inputEmail.getText().toString().trim();

        if (TextUtils.isEmpty(email)) {
            inputEmail.setError("Enter email address!");
            result = false;
        } else if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            inputEmail.setError("Invalid email!");
            result = false;
        } else {
            inputEmail.setError(null);
        }

        return result;
    }
}
