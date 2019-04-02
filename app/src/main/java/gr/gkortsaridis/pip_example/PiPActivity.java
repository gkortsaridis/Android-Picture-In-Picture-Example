package gr.gkortsaridis.pip_example;

import android.app.AppOpsManager;
import android.app.PendingIntent;
import android.app.PictureInPictureParams;
import android.app.RemoteAction;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.drawable.Icon;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.util.Rational;

import java.nio.channels.Pipe;
import java.util.ArrayList;

public class PiPActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pi_p);


        if (supportsPiPMode()) {
            PictureInPictureParams params = new PictureInPictureParams.Builder()
                    .setAspectRatio(getPipRatio())
                    .setActions(getPIPActions()).build();
            enterPictureInPictureMode(params);
        }
    }

    @Override
    public void onUserLeaveHint () {
        if (supportsPiPMode()) {
            PictureInPictureParams params = new PictureInPictureParams.Builder()
                    .setAspectRatio(getPipRatio())
                    .setActions(getPIPActions()).build();
            enterPictureInPictureMode(params);
        }
    }

    @Override
    public void onPictureInPictureModeChanged (boolean isInPictureInPictureMode, Configuration newConfig) {
        if (isInPictureInPictureMode) {
            Log.i("PictureInPicture", "Entered in PiP");
        } else {
            Log.i("PictureInPicture", "Back in full Screen");
            //this.finish();
        }
    }


    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public Rational getPipRatio() {
        int width = getWindow().getDecorView().getWidth();
        int height = getWindow().getDecorView().getHeight();
        return new Rational(2, 1);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private ArrayList<RemoteAction> getPIPActions() {

        ArrayList<RemoteAction> actions = new ArrayList<>();

        RemoteAction action1 = createAction(PiPActivity.this, PiPReceiver.class, "ACTION1", "key", "val1", R.drawable.ic_launcher_foreground);
        RemoteAction action2 = createAction(PiPActivity.this, PiPReceiver.class, "ACTION2", "key", "val1", R.drawable.ic_launcher_foreground);
        RemoteAction action3 = createAction(PiPActivity.this, PiPReceiver.class, "ACTION3", "key", "val1", R.drawable.ic_launcher_foreground);

        actions.add(action1);
        actions.add(action2);
        actions.add(action3);

        return actions;
    }


    @RequiresApi(api = Build.VERSION_CODES.O)
    private RemoteAction createAction(Context context, Class<?> receiver, String action, String key, String value, int drawable){

        Intent intent = new Intent(context, receiver);
        intent.setAction(action);
        intent.putExtra(key, value);

        int requestCode = 0;
        PendingIntent pendingIntent = PendingIntent.getBroadcast(PiPActivity.this, requestCode, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        Icon icon = Icon.createWithResource(PiPActivity.this, drawable);

        return new RemoteAction(icon, getString(R.string.pip_action_mute), getString(R.string.pip_action_mute_description), pendingIntent);
    }


    public boolean supportsPiPMode() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.O;
    }
}
