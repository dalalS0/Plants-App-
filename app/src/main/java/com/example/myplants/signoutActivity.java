package com.example.myplants;


import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.UserHandle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myplants.Models.userModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.SignInMethodQueryResult;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

public class signoutActivity extends AppCompatActivity {

    Button signUp;
    EditText name,email,password,confirmPass;
    TextView signIn;
    FirebaseAuth auth;
    FirebaseDatabase database;
    ProgressBar progressBar;
    TextView msgConfirm,msgWrongEmailexsited;
    TextView msgEmailisEmbty,msgPasswordisempty,msgPasswordlength,msgConfirmPass,msgNotMatchPass;

    @Override
//    private FirebaseAuth mAuth;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signout);

        msgConfirmPass = findViewById(R.id.passConfirm);
        msgNotMatchPass =findViewById(R.id.PassNotMatch);

        msgEmailisEmbty = findViewById(R.id.Emailempty1);
        msgPasswordisempty =findViewById(R.id.PassEmpty1);
        msgPasswordlength = findViewById(R.id.PassLength1);
        //sp if the user click the button again the wrong note massages will gone
        msgEmailisEmbty.setVisibility(View.GONE);
        msgPasswordisempty.setVisibility(View.GONE);
        msgPasswordlength.setVisibility(View.GONE);



        progressBar = findViewById(R.id.proBar);
        progressBar.setVisibility(View.GONE);

        msgWrongEmailexsited =findViewById(R.id.msgWrong);
        msgConfirm = findViewById(R.id.msgConfirm);
        signUp = findViewById(R.id.loginBtn);
        email = findViewById(R.id.email);
        password = findViewById(R.id.etPassword);
        confirmPass = findViewById(R.id.confirmPass);
        signIn = findViewById(R.id.signIn);

        auth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();




        signIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(signoutActivity.this,loginActivity.class));

            }
        });

        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressBar.setVisibility(View.VISIBLE);

                createUser();



            }
        });

    }

    private void createUser() {
        String userEmail = email.getText().toString();
        String userPassword = password.getText().toString();
        String userConfirmPass = confirmPass.getText().toString();

        msgEmailisEmbty.setVisibility(View.GONE);
        msgPasswordisempty.setVisibility(View.GONE);
        msgPasswordlength.setVisibility(View.GONE);

        msgConfirm.setVisibility(View.GONE);
        msgWrongEmailexsited.setVisibility(View.GONE);

        msgConfirmPass.setVisibility(View.GONE);
        msgNotMatchPass.setVisibility(View.GONE);






        if (TextUtils.isEmpty(userEmail)) {
            msgEmailisEmbty.setVisibility(View.VISIBLE);//"Email is Empty!
            progressBar.setVisibility(View.GONE);
            return;
        }

        if (TextUtils.isEmpty(userPassword)) {
            msgPasswordisempty.setVisibility(View.VISIBLE);//Password is Empty!
            progressBar.setVisibility(View.GONE);
            return;
        }
        if (TextUtils.isEmpty(userConfirmPass)) {
            msgConfirmPass.setVisibility(View.VISIBLE);//confirm Password is Empty!
            progressBar.setVisibility(View.GONE);
            return;
        }
        if (!userPassword.equals(userConfirmPass)) {
            msgNotMatchPass.setVisibility(View.VISIBLE);//password does not match!
            password.setText("");
            confirmPass.setText("");
            progressBar.setVisibility(View.GONE);
            return;
        }

        if (userPassword.length() < 6) {
            msgPasswordlength.setVisibility(View.VISIBLE);//Password length must be greater then 6 letter
            progressBar.setVisibility(View.GONE);
            return;
        }

//create user
        auth.createUserWithEmailAndPassword(userEmail, userPassword)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {


                        userModel userModel = new userModel(userEmail, userPassword);


                        if (task.isSuccessful()) {

                            //check if the email existed or not
                            auth.getInstance().fetchSignInMethodsForEmail(userEmail)
                                    .addOnCompleteListener(new OnCompleteListener<SignInMethodQueryResult>() {
                                        @Override
                                        public void onComplete(@NonNull Task<SignInMethodQueryResult> task) {
                                            if (task.isSuccessful()) {
                                                List<String> methods = task.getResult().getSignInMethods();
                                                if (methods.isEmpty()) {

                                                } else {

                                                    //email msg Verification
                                                    auth.getCurrentUser().sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
                                                        @Override
                                                        public void onComplete(@NonNull Task<Void> task) {

                                                            if (task.isSuccessful()) {

                                                                msgConfirm.setVisibility(View.VISIBLE);//Registration Successfully. Please check your email for verification
                                                                progressBar.setVisibility(View.GONE);
                                                                email.setText("");
                                                                password.setText("");
                                                                confirmPass.setText("");

                                                            } else {
                                                                Toast.makeText(signoutActivity.this, "" + task.getException(), Toast.LENGTH_LONG).show();
                                                                progressBar.setVisibility(View.GONE);
                                                            }
                                                        }
                                                    });
                                                }
                                            }
                                        }
                                    });


                        } else {
                            //This email is already existed
                            msgWrongEmailexsited.setVisibility(View.VISIBLE);
                            email.setText("");
                            password.setText("");
                            confirmPass.setText("");
                            progressBar.setVisibility(View.GONE);


                        }
                    }
                });

    }


}