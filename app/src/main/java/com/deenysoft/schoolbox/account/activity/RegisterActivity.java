package com.deenysoft.schoolbox.account.activity;

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
import com.deenysoft.schoolbox.dashboard.DashboardActivity;
import com.deenysoft.schoolbox.firebase.AuthLoginActivity;
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
 * Created by shamsadam on 09/04/16.
 */
public class RegisterActivity extends BaseActivity {

    private static final String TAG = "RegisterActivity";

    private AppCompatButton btnRegister;
    private AppCompatButton btnLinkToLogin;
    private TextInputLayout inputLayoutFullName, inputLayoutInstitution, inputLayoutEmail, inputLayoutPassword, inputLayoutLocation;
    private TextInputEditText inputFullName;
    private TextInputEditText inputInstitution;
    private TextInputEditText inputEmail;
    private TextInputEditText inputPassword;
    private TextInputEditText inputLocation;

    private DatabaseReference mDatabase;
    private FirebaseAuth mAuth;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.registeration_activity);

        inputLayoutFullName = (TextInputLayout) findViewById(R.id.input_layout_name);
        inputLayoutInstitution = (TextInputLayout) findViewById(R.id.input_layout_institution);
        inputLayoutEmail = (TextInputLayout) findViewById(R.id.input_layout_password);
        inputLayoutLocation = (TextInputLayout) findViewById(R.id.input_layout_location);

        inputFullName = (TextInputEditText) findViewById(R.id.input_name);
        inputInstitution = (TextInputEditText) findViewById(R.id.input_institution);
        inputEmail = (TextInputEditText) findViewById(R.id.input_email);
        inputPassword = (TextInputEditText) findViewById(R.id.input_password);
        inputLocation = (TextInputEditText) findViewById(R.id.input_location);
        btnRegister = (AppCompatButton) findViewById(R.id.commitRegister);
        btnLinkToLogin = (AppCompatButton) findViewById(R.id.btnLinkToLoginScreen);


        // Firebase Database & Auth Init
        mDatabase = FirebaseDatabase.getInstance().getReference();
        mAuth = FirebaseAuth.getInstance();





        // Register Button Click event
        btnRegister.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                // Check Validation
                if (!validateForm()) {
                    return;
                }

                showProgressDialog();
                String name = inputFullName.getText().toString(); // Dumped
                String institution = inputInstitution.getText().toString(); // Dumped
                String location = inputLocation.getText().toString(); // Dumped
                String email = inputEmail.getText().toString();
                String password = inputPassword.getText().toString();

                mAuth.createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                Log.d(TAG, "createUser:onComplete:" + task.isSuccessful());
                                hideProgressDialog();

                                if (task.isSuccessful()) {
                                    onAuthSuccess(task.getResult().getUser());
                                } else {
                                    Toast.makeText(RegisterActivity.this, "Sign Up Failed",
                                            Toast.LENGTH_SHORT).show();
                                }
                            }
                        });

            }
        });


        // Link to Login Screen
        btnLinkToLogin.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(),
                        AuthLoginActivity.class);
                startActivity(i);
                finish();
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


    private void onAuthSuccess(FirebaseUser user) {
        String username = usernameFromEmail(user.getEmail());
        // Write new user
        writeNewUser(user.getUid(), username, user.getEmail());

        // Go to MainActivity
        startActivity(new Intent(RegisterActivity.this, SchooBoxIntro.class));
        finish();
    }


    private String usernameFromEmail(String email) {
        if (email.contains("@")) {
            return email.split("@")[0];
        } else {
            return email;
        }
    }


    // [START basic_write]
    private void writeNewUser(String userId, String name, String email) {
        UserItem mUser = new UserItem(name, email);
        // Storing in database child table 'users'
        mDatabase.child("users").child(userId).setValue(mUser);
    }
    // [END basic_write]


    // Validate TextFields
    private boolean validateForm() {
        boolean result = true;
        if (TextUtils.isEmpty(inputFullName.getText().toString())) {
            inputFullName.setError("Required");
            result = false;
        } else {
            inputFullName.setError(null);
        }

        if (TextUtils.isEmpty(inputInstitution.getText().toString())) {
            inputInstitution.setError("Required");
            result = false;
        } else {
            inputInstitution.setError(null);
        }

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

        if (TextUtils.isEmpty(inputLocation.getText().toString())) {
            inputLocation.setError("Required");
            result = false;
        } else {
            inputLocation.setError(null);
        }

        return result;
    }



    @Override
    public boolean providesActivityToolbar() {
        return false;
    }
}
