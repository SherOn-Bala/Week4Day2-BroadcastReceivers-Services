package ca.judacribz.week4day2_broadcastreceivers_services;

import android.app.IntentService;
import android.content.Intent;
import android.os.Bundle;
import android.os.ResultReceiver;

import java.util.ArrayList;

import ca.judacribz.week4day2_broadcastreceivers_services.MainActivity.*;

import static android.app.Activity.RESULT_OK;

public class PersonService extends IntentService {
    public static final String EXTRA_PERSON =
            "ca.judacribz.week4day2_broadcastreceivers_services.EXTRA_PERSON";




    public PersonService() {
        super("PERSON SERVICE");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        ResultReceiver rec = intent.getParcelableExtra("receiverTag");

        ArrayList<Person> personList = new ArrayList<>();

        personList.add(new Person("Sheron", "Balasingam", "USA", "Georgia", "Atlanta"));
        personList.add(new Person("Sheron", "Balasingam", "USA", "Georgia", "Atlanta"));
        personList.add(new Person("Sheron", "Balasingam", "USA", "Georgia", "Atlanta"));
        personList.add(new Person("Sheron", "Balasingam", "USA", "Georgia", "Atlanta"));
        personList.add(new Person("Sheron", "Balasingam", "USA", "Georgia", "Atlanta"));
        personList.add(new Person("Sheron", "Balasingam", "USA", "Georgia", "Atlanta"));
        personList.add(new Person("Sheron", "Balasingam", "USA", "Georgia", "Atlanta"));
        personList.add(new Person("Sheron", "Balasingam", "USA", "Georgia", "Atlanta"));
        personList.add(new Person("Sheron", "Balasingam", "USA", "Georgia", "Atlanta"));

        Bundle b = new Bundle();
        b.putParcelableArrayList(EXTRA_PERSON, personList);
        rec.send(RESULT_OK, b);
    }
}
