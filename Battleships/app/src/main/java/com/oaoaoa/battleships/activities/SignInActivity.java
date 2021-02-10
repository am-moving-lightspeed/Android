package com.oaoaoa.battleships.activities;


import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.oaoaoa.battleships.R;
import com.google.firebase.auth.FirebaseAuth;
import com.oaoaoa.battleships.misc.AnimationsProvider;

import java.util.regex.Pattern;

import es.dmoral.toasty.Toasty;



public class SignInActivity extends AppCompatActivity {

    private       ProgressBar                   mPbLoading;
    private final OnSuccessListener<AuthResult> mListener = new OnSuccessListener<AuthResult>() {

        @Override
        public void onSuccess(AuthResult authResult) {

            Intent intent =
                new Intent(SignInActivity.this, MainActivity.class);
            startActivity(intent);

            mPbLoading.setVisibility(View.GONE);
            SignInActivity.this.finish();
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signin);

        AnimationsProvider.startBackgroundGradientAnimation(
            findViewById(R.id.constraintLayout_signIn_container)
        );

        final EditText etEmail    = findViewById(R.id.editText_signIn_email);
        final EditText etPassword = findViewById(R.id.editText_signIn_password);
        Button         bSignIn    = findViewById(R.id.button_signIn);

        mPbLoading = findViewById(R.id.progressBar_signIn);

        bSignIn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                final String email = etEmail.getText()
                                            .toString();
                final String password = etPassword.getText()
                                                  .toString();

                mPbLoading.setVisibility(View.VISIBLE);

                if (isValidEmail(email) &&
                    !password.isEmpty()) {

                    final FirebaseAuth auth = FirebaseAuth.getInstance();

                    auth.signInWithEmailAndPassword(email, password)
                        .addOnSuccessListener(mListener)
                        .addOnFailureListener(new OnFailureListener() {

                            @Override
                            public void onFailure(@NonNull Exception e) {

                                auth.createUserWithEmailAndPassword(email, password)
                                    .addOnSuccessListener(mListener)
                                    .addOnFailureListener(new OnFailureListener() {

                                        @Override
                                        public void onFailure(@NonNull Exception e) {

                                            showToast(R.string.signIn_toasty_failed);
                                            mPbLoading.setVisibility(View.GONE);
                                        }
                                    });
                            }
                        });
                }
                else {
                    showToast(R.string.signIn_toasty_invalid);
                }
            }
        });
    }


    private boolean isValidEmail(String email) {

        Pattern pattern = Patterns.EMAIL_ADDRESS;
        return pattern.matcher(email)
                      .matches();
    }


    private void showToast(int text) {

        Toasty.custom(SignInActivity.this,
                      text,
                      null,
                      R.color.colour_maroon,
                      Toast.LENGTH_SHORT,
                      false,
                      true)
              .show();
    }

}
