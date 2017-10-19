package ps.wwbtraining.teacher_group2.Activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import ps.wwbtraining.teacher_group2.Models.LoginResponse;
import ps.wwbtraining.teacher_group2.Models.User;
import ps.wwbtraining.teacher_group2.R;
import ps.wwbtraining.teacher_group2.Utils.FragmentUtil;
import ps.wwbtraining.teacher_group2.Utils.SessionManager;
import ps.wwbtraining.teacher_group2.WebService.ApiInterface;
import ps.wwbtraining.teacher_group2.WebService.ApiRetrofit;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {


    TextView et_email, et_pass;
    Button login;
    String user, pass;
    User currentUser;
    SessionManager sessionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        // Inflate the layout for this fragment
        sessionManager = new SessionManager(this);

        et_email = (TextView) findViewById(R.id.email2);
        et_pass = (TextView) findViewById(R.id.pass2);
        login = (Button) findViewById(R.id.login);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                user = et_email.getText().toString().trim();
                pass = et_pass.getText().toString().trim();

                if (!user.equals("") && !pass.equals("")) {
                    ApiInterface service = ApiRetrofit.getRetrofitObject().create(ApiInterface.class);
                    Call<LoginResponse> call = service.checkUser(user, pass);
                    call.enqueue(new Callback<LoginResponse>() {
                        @Override
                        public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {

                            if (response.body().getState().getStatus().equals("true")) {

                                sessionManager.createLoginSession(user, pass);

                                currentUser = response.body().getUser();
//                                SharedPreferences.Editor editor = getSharedPreferences("app", MODE_PRIVATE).edit();
//                                editor.putString("name", currentUser.getUser_email());
//                                editor.putString("token", currentUser.getToken());
//                                editor.apply();

                                Intent i = new Intent(LoginActivity.this, MainActivity.class);
                                startActivity(i);

                            } else {
                                currentUser = null;
                            }
                        }

                        @Override
                        public void onFailure(Call<LoginResponse> call, Throwable t) {
                            Toast.makeText(LoginActivity.this, "Failed", Toast.LENGTH_LONG).show();
                            // show error dialog
                        }
                    });


                } else {
                    Toast.makeText(LoginActivity.this, "You must fill all fields", Toast.LENGTH_SHORT).show();
                }

            }
        });


        et_email.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                et_email.setHint("");
                return false;
            }
        });
        et_email.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    et_email.setHint("Email");
                }
            }
        });

        et_pass.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                et_pass.setHint("");
                return false;
            }
        });
        et_pass.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    et_pass.setHint("Password");
                }
            }
        });


    }

}