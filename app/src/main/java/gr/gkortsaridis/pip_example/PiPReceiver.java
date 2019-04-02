package gr.gkortsaridis.pip_example;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

public class PiPReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {

        //Toast.makeText(context, intent.getAction(), Toast.LENGTH_SHORT).show();

        Log.i("PictureInPicture", intent.getAction());

    }

}
