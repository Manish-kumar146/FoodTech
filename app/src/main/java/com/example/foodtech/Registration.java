package com.example.foodtech;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.foodtech.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Registration extends AppCompatActivity {
EditText FullName, Address, Email, Password;
Button SignUp;
TextView Back;
    String userID;
    public static final String TAG = "TAG";
private FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
//                if(mAuth.getCurrentUser() != null){
//            startActivity(new Intent(getApplicationContext(),MainActivity.class));
//            finish();
//        }
        inililize();
    }



    public void inililize(){
        mAuth=FirebaseAuth.getInstance();
        FullName=(EditText) findViewById(R.id.Name);
        Address=(EditText) findViewById(R.id.Address);
        Email=(EditText) findViewById(R.id.email);
        Password=(EditText) findViewById(R.id.password);
        Back=(TextView) findViewById(R.id.back);
        SignUp=(Button)findViewById(R.id.SignUp);
         Password.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
         Password.setTransformationMethod(PasswordTransformationMethod.getInstance());


        SignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = FullName.getText().toString();
                String address = Address.getText().toString();
                String email = Email.getText().toString();
                String pass = Password.getText().toString();
                if (name.length() == 0) {
                    FullName.requestFocus();
                    FullName.setError("Field Can't be Empty");
                } else if (!name.matches("^[A-Z][a-zA-Z]*$")) {
                    FullName.requestFocus();
                    FullName.setError("Only Alphabats");
                } else if (email.length() == 0) {
                    Email.requestFocus();
                    Email.setError("Field Can't be Empty");

                } else if (!email.matches("^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$")) {
                    Email.requestFocus();
                    Email.setError("test@someone.com");
                } else if (pass.length() == 0) {
                    Password.requestFocus();
                    Password.setError("Field Can't be Empty");
                } else if (!pass.matches("^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[a-zA-Z]).{8,}$")) {
                    Password.requestFocus();
                    Password.setError("- at least 8 characters\n" +
                            "- must contain at least 1 uppercase letter, 1 lowercase letter, and 1 number");
                } else if (address.length() == 0) {
                    Address.requestFocus();
                    Address.setError("Field can't be Empty");

                } else {
                    mAuth.createUserWithEmailAndPassword(email, pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                // Sign in success, update UI with the signed-in user's information
                                Log.d(TAG, "createUserWithEmail:success");
                                FirebaseUser user = mAuth.getCurrentUser();

                                try {
                                    if (user != null)
                                        user.sendEmailVerification()
                                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                    @Override
                                                    public void onComplete(@NonNull Task<Void> task) {
                                                        if (task.isSuccessful()) {
                                                            Log.d(TAG, "Email sent.");

                                                            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                                                                    Registration.this);

                                                            // set title
                                                            alertDialogBuilder.setTitle("Please Verify Your EmailID");

                                                            // set dialog message
                                                            alertDialogBuilder
                                                                    .setMessage("A verification Email Is Sent To Your Registered EmailID, please click on the link and Sign in again!")
                                                                    .setCancelable(false)
                                                                    .setPositiveButton("Sign In", new DialogInterface.OnClickListener() {
                                                                        public void onClick(DialogInterface dialog, int id) {

                                                                            Intent signInIntent = new Intent(Registration.this, Login.class);
                                                                            startActivity(signInIntent);
                                                                        }
                                                                    });

                                                            // create alert dialog
                                                            AlertDialog alertDialog = alertDialogBuilder.create();

                                                            // show it
                                                            alertDialog.show();


                                                        }
                                                    }
                                                });

                                } catch (Exception e) {

                                }
                            } else {
                                // If sign in fails, display a message to the user.
                                Log.w(TAG, "createUserWithEmail:failure", task.getException());
                                Toast.makeText(Registration.this, "Authentication failed.",
                                        Toast.LENGTH_SHORT).show();

                                if (task.getException() != null) {
                                }

                            }

                        }
                    });

                }

            }
        });



        Back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Registration.this, Login.class);
                startActivity(i);
                finish();
            }
        });




    }


}