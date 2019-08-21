package ca.judacribz.week4day2_broadcastreceivers_services.list;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.SystemClock;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import ca.judacribz.week4day2_broadcastreceivers_services.R;
import ca.judacribz.week4day2_broadcastreceivers_services.model.Person;
import ca.judacribz.week4day2_broadcastreceivers_services.service.ForegroundService;

public class PersonAdapter extends RecyclerView.Adapter<PersonAdapter.ViewHolder> {

    public static final String ACTION_SHOW_NOTIFICATION = "ACTION_SHOW_NOTIFICATION";
    public static final String PREF_FILE = "passing_file";
    public static final String EXTRA_PERSON_NAME = "ca.judacribz.week4day2_broadcastreceivers_services.EXTRA_PERSON_NAME";
    public static final String EXTRA_PERSON_FROM = "ca.judacribz.week4day2_broadcastreceivers_services.EXTRA_PERSON_FROM";
    private ArrayList<Person> personList;
    private AlarmManager alarmManager;

    public PersonAdapter(ArrayList<Person> personList, Context context) {
        this.personList = personList;
        alarmManager = (AlarmManager) context.getSystemService(
                Context.ALARM_SERVICE
        );
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(
                R.layout.item_person,
                parent,
                false
        ));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.setViews(personList.get(position));
    }

    @Override
    public int getItemCount() {
        return personList.size();
    }


    class ViewHolder extends RecyclerView.ViewHolder {
        TextView
                tvName,
                tvFrom;

        View view;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            view = itemView;
            tvName = itemView.findViewById(R.id.tvName);
            tvFrom = itemView.findViewById(R.id.tvFrom);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    SharedPreferences.Editor editor =
                            view.getContext().getSharedPreferences(
                                    PREF_FILE,
                                    Context.MODE_PRIVATE
                            ).edit();
                    Person person = personList.get(getAdapterPosition());
                    editor.putString(EXTRA_PERSON_NAME, person.getName());
                    editor.putString(EXTRA_PERSON_FROM, person.getFrom());
                    editor.apply();

                    Intent notificationIntent = new Intent(
                            view.getContext(),
                            ForegroundService.class
                    )
                            .setAction(ACTION_SHOW_NOTIFICATION);

                    PendingIntent pendingIntent = PendingIntent.getService(
                            view.getContext(),
                            0,
                            notificationIntent,
                            0
                    );

                    if (alarmManager != null) {
                        alarmManager.set(
                                AlarmManager.ELAPSED_REALTIME_WAKEUP,
                                SystemClock.elapsedRealtime() + 5000,
                                pendingIntent
                        );
                    }

                }
            });
        }

        void setViews(Person person) {
            tvName.setText(person.getName());
            tvFrom.setText(person.getFrom());
        }
    }
}
