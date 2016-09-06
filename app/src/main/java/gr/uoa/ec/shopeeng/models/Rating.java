package gr.uoa.ec.shopeeng.models;


import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

public class Rating implements Serializable, Parcelable {
    private String rating;
    private String username;
    private String storeId;

    public Rating() {
    }

    ;

    public Rating(String rating, String username, String storeId) {
        this.rating = rating;
        this.username = username;
        this.storeId = storeId;
    }


// Parcelling part

    public Rating(Parcel in) {
        String[] data = new String[3];
        in.readStringArray(data);
        this.rating = data[0];
        this.username = data[1];
        this.storeId = data[2];

    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeStringArray(new String[]{this.rating,
                this.username,
                this.storeId});
    }

    public static final Parcelable.Creator CREATOR = new Parcelable.Creator() {
        public Product createFromParcel(Parcel in) {
            return new Product(in);
        }

        public Product[] newArray(int size) {
            return new Product[size];
        }
    };

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getStoreId() {
        return storeId;
    }

    public void setStoreId(String storeId) {
        this.storeId = storeId;
    }

    @Override
    public String toString() {
        return "Rating : " + rating + "/10 by " + username;
    }
}
