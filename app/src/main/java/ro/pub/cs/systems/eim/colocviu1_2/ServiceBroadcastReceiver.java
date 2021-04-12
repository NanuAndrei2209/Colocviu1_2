package ro.pub.cs.systems.eim.colocviu1_2;

import android.app.Application;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.TextView;
import android.widget.Toast;

public class ServiceBroadcastReceiver extends BroadcastReceiver {
    private Application app;
    public ServiceBroadcastReceiver( Application app) {
        this.app = app;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();
        String data = null;

        if ("myAction".equals(action)) {
            data = intent.getStringExtra("myKey");
            Toast.makeText(this.app, "From receiver: sum = " + data, Toast.LENGTH_LONG).show();
        }
    }
}
