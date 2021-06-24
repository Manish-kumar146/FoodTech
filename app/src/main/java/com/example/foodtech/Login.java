package com.example.foodtech;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.foodtech.R;
import com.example.foodtech.views.MainActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Login extends AppCompatActivity {
Button Login;
EditText Email, Password;
public FirebaseAuth mAuth;
TextView Registrations,forgot;
    private static final String TAG = "SignInActivity";
    Button sendVerifyMailAgainButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initilize();
        reg();

        sendVerifyMailAgainButton.setVisibility(View.INVISIBLE);
    }



    public void initilize(){
        mAuth = FirebaseAuth.getInstance();
        sendVerifyMailAgainButton = findViewById(R.id.verifyEmailAgainButton);
        Login=(Button)findViewById(R.id.login);
        Email=(EditText)findViewById(R.id.email);
        Password=(EditText)findViewById(R.id.password);
        forgot = (TextView)findViewById(R.id.forgot);
        Login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               final String pass = Password.getText().toString();
              final String email = Email.getText().toString();

                if(email.length()==0) {
                    Email.requestFocus();
                    Email.setError("Field Can't be Empty");

                }else if(!email.matches("^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$")){
                    Email.requestFocus();
                    Email.setError("test@someone.com");
                }
                else if(pass.length()==0){
                    Password.requestFocus();
                    Password.setError("Field Can't be Empty");
           }
                else if(!pass.matches("^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[a-zA-Z]).{8,}$")){
                    Password.requestFocus();
                    Password.setError("- at least 8 characters\n" +
                            "- must contain at least 1 uppercase letter, 1 lowercase letter, and 1 number");
                }
               else{




                    mAuth.signInWithEmailAndPassword(email, pass)
                            .addOnCompleteListener(com.example.foodtech.Login.this, new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {
                                        // Sign in success, update UI with the signed-in user's information
                                        Log.d(TAG, "signInWithEmail:success");

                                        FirebaseUser user = mAuth.getCurrentUser();

                                        if (user != null) {
                                            if (user.isEmailVerified()) {


                                                System.out.println("Email Verified : " + user.isEmailVerified());
                                                Intent HomeActivity = new Intent(com.example.foodtech.Login.this, MainActivity.class);
                                                setResult(RESULT_OK, null);
                                                startActivity(HomeActivity);
                                                com.example.foodtech.Login.this.finish();


                                            } else {

                                                sendVerifyMailAgainButton.setVisibility(View.VISIBLE);

                                            }
                                        }

                                    } else {
                                        // If sign in fails, display a message to the user.
                                        Log.w(TAG, "signInWithEmail:failure", task.getException());
                                        Toast.makeText(com.example.foodtech.Login.this, "Authentication failed.",
                                                Toast.LENGTH_SHORT).show();
                                        if (task.getException() != null) {
                                        }

                                    }

                                }
                            });


                }


            }
        });


//        forgot.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//                Intent forgotPasswordActivity = new Intent(MainActivity.this, ForgotPasswordActivity.class);
//                startActivity(forgotPasswordActivity);
//                SignInActivity.this.finish();
//
//            }
//        });


    }


    public void reg(){
        Registrations = (TextView)findViewById(R.id.registration);

        Registrations.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(com.example.foodtech.Login.this, Registration.class);
                startActivity(i);
                finish();
                Toast.makeText(getApplicationContext(),"!Working",Toast.LENGTH_LONG).show();
            }
        });
    }


}