package ca.judacribz.week4day2_broadcastreceivers_services;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.BatteryManager;
import android.widget.Toast;

import static android.content.Intent.EXTRA_UID;

public class SystemReceiver extends BroadcastReceiver {

    private static String str = "";

    @Override
    public void onReceive(Context context, Intent intent) {
        String intentAction = intent.getAction();
        StringBuilder sb = new StringBuilder();

        if (intentAction != null) {
            switch (intentAction) {
                case Intent.ACTION_BATTERY_CHANGED:
                    switch (intent.getIntExtra(BatteryManager.EXTRA_STATUS, -1)) {
                        case BatteryManager.BATTERY_STATUS_CHARGING:
                            sb.append(context.getString(R.string.battery_charging));
                            break;
                        case BatteryManager.BATTERY_STATUS_DISCHARGING:
                            sb.append(context.getString(R.string.battery_discharging));
                            break;
                        case BatteryManager.BATTERY_STATUS_FULL:
                            sb.append(context.getString(R.string.battery_full));
                            break;
                    }

                case Intent.ACTION_AIRPLANE_MODE_CHANGED:
                    if (intent.getBooleanExtra("state", false)) {
                        sb.append("\n").append(context.getString(R.string.airplane_mode));
                    }
                case Intent.ACTION_SCREEN_ON:
                    sb.append("\n").append(context.getString(R.string.screen_on));
                case Intent.ACTION_SCREEN_OFF:
                    sb.append("\n").append(context.getString(R.string.screen_off));

                case Intent.ACTION_PACKAGE_REMOVED:
                    int uid = intent.getIntExtra(EXTRA_UID, -1);
                    if (uid != -1) {
                        sb
                                .append("\n")
                                .append(context.getString(R.string.app_uninstalled))
                                .append(": ")
                                .append(uid);
                    }
            }

            if (!str.equals(sb.toString())) {
                str = sb.toString();
                Toast.makeText(context, sb.toString().trim(), Toast.LENGTH_LONG).show();
            }
        }
    }
}
