package ca.judacribz.week4day2_broadcastreceivers_services;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.BroadcastReceiver;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.os.ResultReceiver;
import android.widget.Toast;

import java.util.ArrayList;

import static ca.judacribz.week4day2_broadcastreceivers_services.PersonService.EXTRA_PERSON;


public class MainActivity extends AppCompatActivity {
    BroadcastReceiver br;
    RecyclerView rvPersons;
    PersonReceiver personReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        personReceiver = new PersonReceiver(new Handler());
        Intent intent = new Intent(this, PersonService.class);
        intent.putExtra("receiverTag", personReceiver);
        startService(intent);

        rvPersons = findViewById(R.id.rvPersons);
        rvPersons.setLayoutManager(new LinearLayoutManager(this));

        br = new SystemReceiver();

        IntentFilter filter = new IntentFilter();
        filter.addAction(Intent.ACTION_AIRPLANE_MODE_CHANGED);
        filter.addAction(Intent.ACTION_BATTERY_CHANGED);
        filter.addAction(Intent.ACTION_SCREEN_ON);
        filter.addAction(Intent.ACTION_PACKAGE_REMOVED);
        filter.addAction(Intent.ACTION_SCREEN_OFF);

        registerReceiver(br, filter);

        Intent i = new Intent(this, ForegroundService.class);
        startService(i);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        unregisterReceiver(br);
    }

    @Override
    protected void onPause() {
        super.onPause();
    }


    class PersonReceiver extends ResultReceiver {

        public PersonReceiver(Handler handler) {
            super(handler);
        }

        @Override
        protected void onReceiveResult(int resultCode, Bundle resultData) {
            Toast.makeText(MainActivity.this, "sdfsdfsd", Toast.LENGTH_SHORT).show();
            if (resultCode == RESULT_OK) {
                ArrayList<Person> personArrayList = resultData.getParcelableArrayList(EXTRA_PERSON);
                rvPersons.setAdapter(new PersonAdapter(personArrayList));
            }
        }
    }
}
