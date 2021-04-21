package com.mila.rationhelper;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.drawable.DrawableCompat;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;

import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.TimeUnit;

public class LoginActivity extends AppCompatActivity {
    private TextView title;
    private ImageView banner;
    private EditText usernameET, passwordET, repeatPasswordET;
    private Button loginButton, registerButton, googleSignIn;
    private boolean displayingLogin, displayBanner = true;

    private FirebaseAuth auth;
    private static final int RC_SIGN_IN = 9001;

    private final int displayBannerFor = 2000; //milliseconds
    private String TAG = getClass().getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        findReferences();
        if (displayBanner) {
            displayBanner();
            new Handler().postDelayed(() -> displayLogin(), displayBannerFor);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

    }

    private void findReferences(){
        title = findViewById(R.id.loginTitle);
        banner = findViewById(R.id.banner);
        usernameET = findViewById(R.id.usernameET);
        passwordET = findViewById(R.id.passwordET);
        repeatPasswordET = findViewById(R.id.repeatPasswordET);
        loginButton = findViewById(R.id.loginButton);
        registerButton = findViewById(R.id.registerButton);
        googleSignIn = findViewById(R.id.googleSignIn);

        displayingLogin = true;
        auth = FirebaseAuth.getInstance();
    }


    private void displayBanner(){
        banner.setImageResource(R.drawable.banner_text);

        title.setVisibility(View.GONE);
        banner.setVisibility(View.VISIBLE);
        usernameET.setVisibility(View.GONE);
        passwordET.setVisibility(View.GONE);
        repeatPasswordET.setVisibility(View.GONE);
        loginButton.setVisibility(View.GONE);
        registerButton.setVisibility(View.GONE);
        googleSignIn.setVisibility(View.GONE);

        displayBanner = false;
    }


    private void displayLogin(){
        title.setVisibility(View.VISIBLE);
        banner.setVisibility(View.GONE);
        usernameET.setVisibility(View.VISIBLE);
        passwordET.setVisibility(View.VISIBLE);
        repeatPasswordET.setVisibility(View.INVISIBLE);
        loginButton.setVisibility(View.VISIBLE);
        registerButton.setVisibility(View.VISIBLE);

        loginButton.setText(R.string.login_button);
        registerButton.setText(R.string.register_button);
        googleSignIn.setVisibility(View.VISIBLE);

        DrawableCompat.setTint(repeatPasswordET.getBackground(), Color.GRAY);
        repeatPasswordET.setText("");
    }


    private void displayRegister(){
        title.setVisibility(View.VISIBLE);
        banner.setVisibility(View.GONE);
        usernameET.setVisibility(View.VISIBLE);
        passwordET.setVisibility(View.VISIBLE);
        repeatPasswordET.setVisibility(View.VISIBLE);
        loginButton.setVisibility(View.VISIBLE);
        registerButton.setVisibility(View.VISIBLE);
        googleSignIn.setVisibility(View.VISIBLE);

        loginButton.setText(R.string.back_to_login);
        registerButton.setText(R.string.finish_registration);
    }

    public void loginButtonListener(View view){
        if (displayingLogin){
            String username = usernameET.getText().toString();
            String password = passwordET.getText().toString();
            logUserIn(username, password);
        }
        else {
            displayLogin();
            displayingLogin = true;
        }
    }

    public void registerButtonListener(View view){
        if (displayingLogin){
            displayRegister();
            displayingLogin = false;
        }
        else {
            String username = usernameET.getText().toString();
            String password1 = passwordET.getText().toString();
            String password2 = repeatPasswordET.getText().toString();

            if (password1.matches("")){
                Toast.makeText(this, "Give me password!", Toast.LENGTH_LONG).show();
            }
            else if (!password1.equals(password2)){
                DrawableCompat.setTint(repeatPasswordET.getBackground(), Color.RED);
                Toast.makeText(this, "Passwords don't match!", Toast.LENGTH_LONG).show();
            }
            else
                registerUser(username, password1);

        }
    }

    public void googleSignInButtonListener(View view){
        // Configure Google Sign In
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        GoogleSignInClient mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    private void logUserIn(String username, String password){
        auth.signInWithEmailAndPassword(username, password)
                .addOnCompleteListener(LoginActivity.this, task -> {
                    if (task.isSuccessful())
                        goToMenu();
                    else
                        Toast.makeText(LoginActivity.this, task.getException().getLocalizedMessage(), Toast.LENGTH_LONG).show();
                });
    }

    private void registerUser(String username, String password){
        auth.createUserWithEmailAndPassword(username, password)
                .addOnCompleteListener(LoginActivity.this, task -> {
                    if (task.isSuccessful())
                        goToMenu();
                    else
                        Toast.makeText(LoginActivity.this, task.getException().getLocalizedMessage(), Toast.LENGTH_LONG).show();
                });
    }

    private void goToMenu(){
        Intent intent = new Intent(this, MenuActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                // Google Sign In was successful, authenticate with Firebase
                GoogleSignInAccount account = task.getResult(ApiException.class);
                Log.d(TAG, "firebaseAuthWithGoogle:" + account.getId());
                firebaseAuthWithGoogle(account.getIdToken());
            } catch (ApiException e) {
                // Google Sign In failed, update UI appropriately
                Log.w(TAG, "Google sign in failed", e);
            }
        }
    }

    private void firebaseAuthWithGoogle(String idToken) {
        AuthCredential credential = GoogleAuthProvider.getCredential(idToken, null);
        auth.signInWithCredential(credential)
                .addOnCompleteListener(this, task -> {
                    if (task.isSuccessful()) {
                        // Sign in success, update UI with the signed-in user's information
                        Log.d(TAG, "signInWithCredential:success");
                        FirebaseUser user = auth.getCurrentUser();
                        goToMenu();
                    } else {
                        // If sign in fails, display a message to the user.
                        Log.w(TAG, "signInWithCredential:failure", task.getException());
                    }
                });
    }
}