package ca.judacribz.week4day2_broadcastreceivers_services;

import android.os.Parcelable;
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

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tvName);
            tvFrom = itemView.findViewById(R.id.tvFrom);
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
