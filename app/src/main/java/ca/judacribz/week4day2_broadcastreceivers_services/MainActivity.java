package ca.judacribz.week4day2_broadcastreceivers_services;

import android.content.BroadcastReceiver;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.os.ResultReceiver;
import android.view.Menu;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import ca.judacribz.week4day2_broadcastreceivers_services.broadcast_receiver.SystemReceiver;
import ca.judacribz.week4day2_broadcastreceivers_services.list.PersonAdapter;
import ca.judacribz.week4day2_broadcastreceivers_services.model.Person;
import ca.judacribz.week4day2_broadcastreceivers_services.service.ForegroundService;
import ca.judacribz.week4day2_broadcastreceivers_services.service.PersonService;

import static ca.judacribz.week4day2_broadcastreceivers_services.service.PersonService.EXTRA_PERSON;


public class MainActivity extends AppCompatActivity {

    public static final String ACTION_PLAY_MUSIC = "ACTION_PLAY_MUSIC";
    public static final String EXTRA_RECEIVER =
            "ca.judacribz.week4day2_broadcastreceivers_services.EXTRA_RECEIVER";

    BroadcastReceiver br;
    RecyclerView rvPersons;
    PersonReceiver personReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        rvPersons = findViewById(R.id.rvPersons);
        rvPersons.setLayoutManager(new LinearLayoutManager(this));


        personReceiver = new PersonReceiver(new Handler());
        Intent intent = new Intent(this, PersonService.class);
        intent.putExtra(EXTRA_RECEIVER, personReceiver);
        startService(intent);


        IntentFilter filter = new IntentFilter();
        filter.addAction(Intent.ACTION_AIRPLANE_MODE_CHANGED);
        filter.addAction(Intent.ACTION_BATTERY_CHANGED);
        filter.addAction(Intent.ACTION_SCREEN_ON);
        filter.addAction(Intent.ACTION_PACKAGE_REMOVED);
        filter.addAction(Intent.ACTION_SCREEN_OFF);
        br = new SystemReceiver();
        registerReceiver(br, filter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.actPlayMusic:
                startService(new Intent(this, ForegroundService.class).setAction(ACTION_PLAY_MUSIC));
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        unregisterReceiver(br);
    }

    class PersonReceiver extends ResultReceiver {

        public PersonReceiver(Handler handler) {
            super(handler);
        }

        @Override
        protected void onReceiveResult(int resultCode, Bundle resultData) {
            if (resultCode == RESULT_OK) {
                ArrayList<Person> personArrayList = resultData.getParcelableArrayList(EXTRA_PERSON);
                rvPersons.setAdapter(new PersonAdapter(personArrayList, MainActivity.this));
            }
        }
    }
}
