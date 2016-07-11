package com.deenysoft.schoolbox.firebase;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v7.widget.AppCompatButton;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.deenysoft.schoolbox.R;
import com.deenysoft.schoolbox.account.activity.RegisterActivity;
import com.deenysoft.schoolbox.dashboard.DashboardActivity;
import com.deenysoft.schoolbox.firebase.model.UserItem;
import com.deenysoft.schoolbox.intro.SchooBoxIntro;
import com.deenysoft.schoolbox.nest.ui.base.BaseActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * Created by shamsadam on 13/06/16.
 */
public class AuthLoginActivity extends BaseActivity {

    private static final String TAG = "AuthLoginActivity";

    private AppCompatButton btnLogin;
    private AppCompatButton btnLinkRegister;
    private TextInputLayout inputLayoutEmail, inputLayoutPassword;
    private TextInputEditText inputEmail;
    private TextInputEditText inputPassword;

    private DatabaseReference mDatabase;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.auth_login_activity);

        inputLayoutEmail = (TextInputLayout) findViewById(R.id.input_layout_email);
        inputLayoutPassword = (TextInputLayout) findViewById(R.id.input_layout_password);

        inputEmail = (TextInputEditText) findViewById(R.id.input_email);
        inputPassword = (TextInputEditText) findViewById(R.id.input_password);
        inputEmail = (TextInputEditText) findViewById(R.id.input_email);

        btnLogin = (AppCompatButton) findViewById(R.id.commitLogin);
        btnLinkRegister = (AppCompatButton) findViewById(R.id.btnLinkRegister);

        // Firebase Database & Auth Init
        mDatabase = FirebaseDatabase.getInstance().getReference();
        mAuth = FirebaseAuth.getInstance();

        // Sign in on button Click
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!validateForm()) {
                    return;
                }
                showProgressDialog();
                String email = inputEmail.getText().toString();
                String password = inputPassword.getText().toString();

                mAuth.signInWithEmailAndPassword(email, password)
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                Log.d(TAG, "signIn:onComplete:" + task.isSuccessful());
                                hideProgressDialog();

                                if (task.isSuccessful()) {
                                    onAuthSuccess(task.getResult().getUser());
                                } else {
                                    Toast.makeText(AuthLoginActivity.this, "Invalid email or password",
                                            Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
            }
        });


        // Link to Register Activity
        btnLinkRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AuthLoginActivity.this, RegisterActivity.class));
            }
        });

    }


    @Override
    public void onStart() {
        super.onStart();

        // Check auth on Activity start
        if (mAuth.getCurrentUser() != null) {
            onAuthSuccess(mAuth.getCurrentUser());
        }
    }


    // [START basic_write]
    private void writeNewUser(String userId, String name, String email) {
        UserItem mUser = new UserItem(name, email);
        // Storing in database child table 'users'
        mDatabase.child("users").child(userId).setValue(mUser);
    }
    // [END basic_write]


    private void onAuthSuccess(FirebaseUser user) {
        String username = usernameFromEmail(user.getEmail());
        // Write new user
        writeNewUser(user.getUid(), username, user.getEmail());

        // Go to MainActivity
        startActivity(new Intent(AuthLoginActivity.this, DashboardActivity.class));
        finish();
    }



    private String usernameFromEmail(String email) {
        if (email.contains("@")) {
            return email.split("@")[0];
        } else {
            return email;
        }
    }


    // Validate TextFields
    private boolean validateForm() {
        boolean result = true;
        if (TextUtils.isEmpty(inputEmail.getText().toString())) {
            inputEmail.setError("Required");
            result = false;
        } else {
            inputEmail.setError(null);
        }
        if (TextUtils.isEmpty(inputPassword.getText().toString())) {
            inputPassword.setError("Required");
            result = false;
        } else {
            inputPassword.setError(null);
        }

        return result;
    }


    @Override
    public boolean providesActivityToolbar() {
        return false;
    }
}
