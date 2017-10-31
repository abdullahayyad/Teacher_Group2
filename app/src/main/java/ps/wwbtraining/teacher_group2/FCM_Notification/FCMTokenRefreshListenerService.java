package ps.wwbtraining.teacher_group2.FCM_Notification;

import android.content.Intent;

import com.google.firebase.iid.FirebaseInstanceIdService;


/**
 * Created by Eman on 8/29/2017.
 */

public class FCMTokenRefreshListenerService  extends FirebaseInstanceIdService {
        @Override
        public void onTokenRefresh() {

            Intent intent = new Intent(this, FCMRegistrationService.class);
            intent.putExtra("refreshed", true);
            startService(intent);
        }

}
