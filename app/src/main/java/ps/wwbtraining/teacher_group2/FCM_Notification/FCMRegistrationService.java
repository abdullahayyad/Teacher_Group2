package ps.wwbtraining.teacher_group2.FCM_Notification;

import android.app.IntentService;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.content.res.Configuration;
import android.util.Log;


import com.google.firebase.iid.FirebaseInstanceId;

import okhttp3.ResponseBody;
import ps.wwbtraining.teacher_group2.Models.Response_State;
import ps.wwbtraining.teacher_group2.WebService.ApiInterface;
import ps.wwbtraining.teacher_group2.WebService.ApiRetrofit;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * An {@link IntentService} subclass for handling asynchronous task requests in
 * a service on a separate handler thread.
 * <p>
 * TODO: Customize class - update intent actions and extra parameters.
 */

public class FCMRegistrationService extends IntentService {
    SharedPreferences preferences;

    public FCMRegistrationService() {
        super("FCMRegistrationService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        String token = FirebaseInstanceId.getInstance().getToken();
        preferences = PreferenceManager.getDefaultSharedPreferences(this);

        if (intent.getExtras() != null) {
            boolean refreshed = intent.getExtras().getBoolean("refreshed");
            if (refreshed) preferences.edit().putBoolean("token_sent", false).apply();
        }

        if (!preferences.getBoolean("token_sent", false)) {
             sendTokenToServer(token);
        }
    }

    private void sendTokenToServer(final String token) {

        String ADD_TOKEN_URL = "http://developerhendy.16mb.com/addnewtoken.php";
        ApiInterface apiService = ApiRetrofit.getRetrofitObject().create(ApiInterface.class);
        Call<Response_State> call = apiService.addFcmToken(token);
        call.enqueue(new Callback<Response_State>() {
            @Override
            public void onResponse(Call<Response_State> call, Response<Response_State> response) {
                if (response.body().getStatus().equals("true")) {
                    preferences.edit().putBoolean("token_sent", true).apply();
                    Log.e("Registration Service", "Response : Send Token Success");
                    stopSelf();

                } else {
                    preferences.edit().putBoolean("token_sent", false).apply();
                    Log.e("Registration Service", "Response : Send Token Failed");
                    stopSelf();

                }
            }

            @Override
            public void onFailure(Call<Response_State> call, Throwable t) {
                preferences.edit().putBoolean("token_sent", false).apply();
                Log.e("Registration Service", "Error :Send Token Failed");
                stopSelf();
            }
        });


    }

}
