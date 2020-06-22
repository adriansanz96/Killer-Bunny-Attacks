package com.example.adrin.juegoproyecto;

import android.app.Activity;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.GoogleAuthProvider;

public class SplashScreen extends AppCompatActivity implements Runnable{

    ProgressBar barraProgreso;
    private SignInButton googleinicio;
    private  final int RC_SIGN_IN = 1;
    private GoogleApiClient mGoogleApiClient;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private static final String TAG = "MAIN_ACTIVITY";
    private ImageView titulo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        titulo = (ImageView) findViewById(R.id.imageView7);
        //Ventana completa
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
            new Thread(this).start();
        Animation dentro;
        dentro = AnimationUtils.loadAnimation(getApplication(), R.anim.mover);
        dentro.setDuration(300);
        titulo.startAnimation(dentro);
        }

    public void run() {

        try {
            for (int i = 0; i < 10; i++) {
                //   if (interrumpido) return;
                Thread.sleep(100);
            }
            Finalizacion();
        } catch (InterruptedException e) {
            return;
        }
    }

    private void Finalizacion() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        overridePendingTransition(R.anim.left_in, R.anim.left_out);
        finish();
    }

}
