package ps.wwbtraining.teacher_group2.Activities;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import ps.wwbtraining.teacher_group2.R;
import ps.wwbtraining.teacher_group2.Utils.SessionManager;

public class Splash extends AppCompatActivity {
    private final int SPLASH_DISPLAY_LENGTH = 3000;
    SessionManager sessionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        sessionManager=new SessionManager(this);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                try {
                    if (sessionManager.getUserDetails().get(SessionManager.KEY_EMAIL).equals("") && sessionManager.getUserDetails().get(SessionManager.KEY_PASSWORD).equals("")) {
                        Intent mainIntent = new Intent(Splash.this, LoginActivity.class);
                        startActivity(mainIntent);
                        finish();
                    } else {
                        Intent mainIntent = new Intent(Splash.this, MainActivity.class);
                        startActivity(mainIntent);
                        finish();
                    }
                }catch (Exception e){
                    Intent mainIntent = new Intent(Splash.this, LoginActivity.class);
                    startActivity(mainIntent);
                    finish();
                }
            }
        }, SPLASH_DISPLAY_LENGTH);


    }
}
