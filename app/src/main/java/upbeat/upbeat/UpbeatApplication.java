package upbeat.upbeat;

import android.app.Application;

import com.firebase.client.Firebase;

/**
 * Created by Chandler on 4/18/16.
 */
public class UpbeatApplication extends Application {
    private Firebase myMainReference;

    @Override
    public void onCreate() {
        super.onCreate();

        Firebase.setAndroidContext(this);
        myMainReference = new Firebase("https://dazzling-fire-2122.firebaseio.com");
    }

    public Firebase getMyMainReference() {
        return myMainReference;
    }
}