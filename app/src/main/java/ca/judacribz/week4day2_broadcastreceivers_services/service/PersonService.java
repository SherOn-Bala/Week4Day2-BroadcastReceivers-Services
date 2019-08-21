package ca.judacribz.week4day2_broadcastreceivers_services.service;

import android.app.IntentService;
import android.content.Intent;
import android.os.Bundle;
import android.os.ResultReceiver;

import java.util.ArrayList;

import ca.judacribz.week4day2_broadcastreceivers_services.model.Person;

import static android.app.Activity.RESULT_OK;
import static ca.judacribz.week4day2_broadcastreceivers_services.MainActivity.EXTRA_RECEIVER;

public class PersonService extends IntentService {
    public static final String EXTRA_PERSON =
            "ca.judacribz.week4day2_broadcastreceivers_services.EXTRA_PERSON";
    private static final String TAG_SERVICE_NAME = "PERSON SERVICE";

    public PersonService() {
        super(TAG_SERVICE_NAME);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        ResultReceiver rec = intent.getParcelableExtra(EXTRA_RECEIVER);
        if (rec != null) {
            ArrayList<Person> personList = new ArrayList<>();

            personList.add(new Person("Sheron", "Balasingam", "USA", "Georgia", "Atlanta"));
            personList.add(new Person("Don", "Brown", "Canada", "Ontario", "Toronto"));
            personList.add(new Person("Apple", "Bottom", "Korea", "North", "Pyong"));
            personList.add(new Person("Bill", "Nye", "USA", "Idaho", "Yolo"));
            personList.add(new Person("Drake", "Aubrey", "Canada", "Ontario", "Rosedale"));
            personList.add(new Person("Oakley", "Barns", "USA", "California", "San Diego"));
            personList.add(new Person("Mountain", "Everest", "Nepal", "Himalayas", "Mountain Area"));

            Bundle bundle = new Bundle();
            bundle.putParcelableArrayList(EXTRA_PERSON, personList);
            rec.send(RESULT_OK, bundle);
        }
    }
}
