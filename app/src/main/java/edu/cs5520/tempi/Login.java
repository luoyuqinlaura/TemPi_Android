package edu.cs5520.tempi;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.facebook.AccessToken;
import com.facebook.AccessTokenTracker;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.appevents.AppEventsLogger;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;

public class Login extends AppCompatActivity {
    CallbackManager callbackManager;
    AccessToken accessToken;
    Boolean isLoggedIn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(getApplicationContext());
        AppEventsLogger.activateApp(this);
        setContentView(R.layout.activity_login);
        LinearLayout layout = findViewById(R.id.login);
        Button button = findViewById(R.id.move);
        TextView text = findViewById(R.id.slogan_name);
        button.setVisibility(View.INVISIBLE);

        callbackManager = CallbackManager.Factory.create();
        LoginButton loginButton = (LoginButton) findViewById(R.id.facebook);
        LoginManager.getInstance().logOut();

        AccessTokenTracker fbTracker = new AccessTokenTracker() {
            @Override
            protected void onCurrentAccessTokenChanged(AccessToken accessToken, AccessToken accessToken2) {
                if (accessToken2 == null) {
                    layout.setVisibility(View.VISIBLE);
                    button.setVisibility(View.INVISIBLE);
                    text.setVisibility(View.INVISIBLE);
                }
            }
        };

        LoginManager.getInstance().registerCallback(callbackManager,
                new FacebookCallback<LoginResult>() {
                    @Override
                    public void onSuccess(LoginResult loginResult) {
                        // App code
                        accessToken = AccessToken.getCurrentAccessToken();
                        isLoggedIn = accessToken != null && !accessToken .isExpired();
                        showTable();
                        layout.setVisibility(View.INVISIBLE);
                        button.setVisibility(View.VISIBLE);
                        text.setVisibility(View.INVISIBLE);
                    }

                    @Override
                    public void onCancel() {
                        // App code
                        layout.setVisibility(View.VISIBLE);
                        button.setVisibility(View.INVISIBLE);
                        text.setVisibility(View.INVISIBLE);
                    }

                    @Override
                    public void onError(FacebookException exception) {
                        // App code
                    }
                });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        callbackManager.onActivityResult(requestCode, resultCode, data);
        super.onActivityResult(requestCode, resultCode, data);
    }

    public void showTable() {
        Intent intent = new Intent(this, Dashboard.class);
        startActivity(intent);
    }

    public void showTable(View view) {
        Intent intent = new Intent(this, Dashboard.class);
        startActivity(intent);
    }
}