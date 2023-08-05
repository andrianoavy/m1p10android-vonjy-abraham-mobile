package mg.itu.m1p10android;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import mg.itu.m1p10android.models.User;

public class SplashScreenActivity extends AppCompatActivity {

    private final int SPLASH_SCREEN_TIMEOUT = 3000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        //rediriger vers la page MainActivity

        User user = new User(getApplicationContext());
        Boolean isLoginIn = user.getLogin();
        if(isLoginIn){
            Runnable runnable = new Runnable() {
                @Override
                public void run() {
                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(intent);
                    finish();
                }
            };
            //        handler post delayer
            new Handler().postDelayed(runnable,SPLASH_SCREEN_TIMEOUT);
        }else {
            Runnable runnable = new Runnable() {
                @Override
                public void run() {
                    Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                    startActivity(intent);
                    finish();
                }
            };
            //        handler post delayer
            new Handler().postDelayed(runnable,SPLASH_SCREEN_TIMEOUT);
        }


    }
}