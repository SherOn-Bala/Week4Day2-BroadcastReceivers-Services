package ca.judacribz.week4day2_broadcastreceivers_services;

import android.os.Parcel;
import android.os.Parcelable;

public class Person implements Parcelable {
    String
            firstName,
            lastName,
            country,
            state,
            city;

    public Person(String firstName, String lastName, String country, String state, String city) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.country = country;
        this.state = state;
        this.city = city;
    }

    protected Person(Parcel in) {
        firstName = in.readString();
        lastName = in.readString();
        country = in.readString();
        state = in.readString();
        city = in.readString();
    }

    public static final Creator<Person> CREATOR = new Creator<Person>() {
        @Override
        public Person createFromParcel(Parcel in) {
            return new Person(in);
        }

        @Override
        public Person[] newArray(int size) {
            return new Person[size];
        }
    };

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(firstName);
        parcel.writeString(lastName);
        parcel.writeString(country);
        parcel.writeString(state);
        parcel.writeString(city);
    }
}
