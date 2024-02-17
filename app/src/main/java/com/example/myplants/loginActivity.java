package com.example.myplants;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class loginActivity extends AppCompatActivity {


    Button signIn;
    EditText email,password;;
    TextView signUp;
    FirebaseAuth auth;
    ProgressBar progressBar,progressBar2;
    TextView forgetPass;
    TextView msgCheck,msgError;
    TextView msgResetEmail,msgResetWrong,msgFillBlank;
    TextView msgEmailisEmbty,msgPasswordisempty,msgPasswordlength;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //put this in where u want the user loging in which page
        /*make the user inter to the main page with ther account if that code not existed it would be wrong and the
        user could login without virfited his account and the wrong so we writed that code so*/

        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
        if (firebaseUser != null && firebaseUser.isEmailVerified()) {
            Intent intent = new Intent(loginActivity.this,Mainpage.class);
            startActivity(intent);
            finish();

        }


        msgEmailisEmbty = findViewById(R.id.Emailempty);
        msgPasswordisempty =findViewById(R.id.PassEmpty);
        msgPasswordlength = findViewById(R.id.PassLength);
        //sp if the user click the button again the wrong note massages will gone
        msgEmailisEmbty.setVisibility(View.GONE);
        msgPasswordisempty.setVisibility(View.GONE);
        msgPasswordlength.setVisibility(View.GONE);


        msgCheck =findViewById(R.id.msgCheck);
        msgCheck.setVisibility(View.GONE);

        msgError = findViewById(R.id.msgError);
        msgError.setVisibility(View.GONE);

        forgetPass = findViewById(R.id.forgetPassword);
        progressBar = findViewById(R.id.proBar);
        progressBar.setVisibility(View.GONE);
        signIn = findViewById(R.id.loginBtn);
        signUp = findViewById(R.id.tvRegisterLink);
        email = findViewById(R.id.email);
        password = findViewById(R.id.etPassword);
        auth = FirebaseAuth.getInstance();




        forgetPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(loginActivity.this);
                LayoutInflater inflater = loginActivity.this.getLayoutInflater();
                View dialogView = inflater.inflate(R.layout.rest_pass, null);
                dialogBuilder.setView(dialogView);

                EditText email2 = (EditText) dialogView.findViewById(R.id.email);
                Button button  = (Button) dialogView.findViewById(R.id.reset);
                button.setText("Send");



                //icon loading
                progressBar2 =(ProgressBar)dialogView.findViewById(R.id.proBar2);
                progressBar2.setVisibility(View.GONE);

                //massage wrong fill the Email blank
                msgFillBlank =(TextView) dialogView.findViewById(R.id.msgFill);
                msgFillBlank.setVisibility(View.GONE);

                //massage reset email
                msgResetEmail = (TextView) dialogView.findViewById(R.id.msgReset);
                msgResetEmail.setVisibility(View.GONE);

                //massage Wrong send the reset email
                msgResetWrong = (TextView) dialogView.findViewById(R.id.msgResetWrong);
                msgResetWrong.setVisibility(View.GONE);

                dialogBuilder.setMessage("")
                        .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.dismiss();
                            }
                        });
                AlertDialog alertDialog = dialogBuilder.create();
                alertDialog.show();




                button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        msgResetEmail.setVisibility(View.GONE);
                        msgResetWrong.setVisibility(View.GONE);
                        msgFillBlank.setVisibility(View.GONE);

                        progressBar2.setVisibility(View.VISIBLE);

                        email2.getText().toString();
                        if (TextUtils.isEmpty(email2.getText().toString())) {
                            msgFillBlank.setVisibility(View.VISIBLE);//massage Fill the email
                            email2.setText("");
                            progressBar2.setVisibility(View.GONE);

                        }else {
                            firebaseAuth.sendPasswordResetEmail(email2.getText().toString()).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {

                                    if (task.isSuccessful()) {
                                       msgResetEmail.setVisibility(View.VISIBLE);//massage Please check your email for reset your password
                                        email2.setText("");
                                        progressBar2.setVisibility(View.GONE);


                                    } else {
                                        msgResetWrong.setVisibility(View.VISIBLE);//massage Failed to Send, The email is wrong
                                        email2.setText("");
                                        progressBar2.setVisibility(View.GONE);


                                    }
                                }
                            });
                        }


                    }
                });


            }
        });






        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(loginActivity.this,signoutActivity.class));

            }
        });

        signIn.setOnClickListener((new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressBar.setVisibility(View.VISIBLE);
                msgCheck.setVisibility(View.GONE);
                msgError.setVisibility(View.GONE);
                //sp if the user click the button again the wrong note massages will gone
                msgEmailisEmbty.setVisibility(View.GONE);
                msgPasswordisempty.setVisibility(View.GONE);
                msgPasswordlength.setVisibility(View.GONE);

                loginUser();


            }
        }));

    }


    private void loginUser() {
        String userEmail = email.getText().toString();
        String userPassword = password.getText().toString();




        if(TextUtils.isEmpty(userEmail)){
            msgEmailisEmbty.setVisibility(View.VISIBLE);//Email is Empty!
            email.setText("");
            password.setText("");
            progressBar.setVisibility(View.GONE);
            return;
        }

        if(TextUtils.isEmpty(userPassword)){
            msgPasswordisempty.setVisibility(View.VISIBLE); //"Password is Empty!
            email.setText("");
            password.setText("");
            progressBar.setVisibility(View.GONE);

            return;
        }

        //Login user
        auth.signInWithEmailAndPassword(userEmail,userPassword)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    if(auth.getCurrentUser().isEmailVerified()){
                        //Toast.makeText(loginActivity.this,"login is successfully!",Toast.LENGTH_LONG).show();
                        Intent intent = new Intent(loginActivity.this,Mainpage.class);
                        startActivity(intent);
                        finish();

                        email.setText("");
                        password.setText("");
                        progressBar.setVisibility(View.GONE);
                    }else{
                        msgCheck.setVisibility(View.VISIBLE);
                        email.setText("");
                        password.setText("");
                        //you have to verify your email address first
                        progressBar.setVisibility(View.GONE);
                    }


                }else{
                    msgError.setVisibility(View.VISIBLE);//Error maby the email is wrong or the password or its not exist
                    password.setText("");
                    progressBar.setVisibility(View.GONE);


                }

            }
        });
    }
}