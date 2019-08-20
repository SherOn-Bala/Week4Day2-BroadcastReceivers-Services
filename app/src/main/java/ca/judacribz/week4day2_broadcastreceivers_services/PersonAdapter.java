package ca.judacribz.week4day2_broadcastreceivers_services;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Parcelable;
import android.os.SystemClock;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Locale;

class PersonAdapter extends RecyclerView.Adapter<PersonAdapter.ViewHolder> {

    private ArrayList<Person> personList;

    public PersonAdapter(ArrayList<Person> personList) {
        this.personList = personList;
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
                    Intent notificationIntent = new Intent(view.getContext(), ForegroundService.class);
                    PendingIntent pendingIntent = PendingIntent.getService(
                            view.getContext(),
                            0,
                            notificationIntent,
                            0
                    );
                    AlarmManager alarmManager = (AlarmManager)view.getContext().getSystemService(
                            Context.ALARM_SERVICE
                    );
                    alarmManager.set(
                            AlarmManager.ELAPSED_REALTIME_WAKEUP,
                            SystemClock.elapsedRealtime() + 5000,
                            pendingIntent
                    );

                }
            });
        }

        void setViews(Person person) {
            tvName.setText(String.format(
                    Locale.US,
                    "%s, %s",
                    person.getLastName(),
                    person.getFirstName()
            ));
            tvFrom.setText(String.format(
                    Locale.US,
                    "%s, %s, %s",
                    person.getCity(),
                    person.getState(),
                    person.getCountry()
            ));
        }
    }
}
