package uws.ah.bustrackergeo;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

public class MyGeofenceBroadcastReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        // TODO: This method is called when the BroadcastReceiver is receiving
        // an Intent broadcast.
        NotificationHelper notificationHelper = new NotificationHelper(context);
        Toast.makeText(context, "TRIGGERED...", Toast.LENGTH_SHORT).show();
        notificationHelper.sendHighPriorityNotification("Near bus stop", "", MapsActivity.class);
    }
}