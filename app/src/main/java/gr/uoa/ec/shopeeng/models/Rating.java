package gr.uoa.ec.shopeeng.models;


import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

public class Rating implements Serializable, Parcelable {
    private String rating;
    private String userId;
    private String storeId;

    public Rating() {
    }

    ;

    public Rating(String rating, String userId, String storeId) {
        this.rating = rating;
        this.userId = userId;
        this.storeId = storeId;
    }


// Parcelling part

    public Rating(Parcel in) {
        String[] data = new String[3];
        in.readStringArray(data);
        this.rating = data[0];
        this.userId = data[1];
        this.storeId = data[2];

    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeStringArray(new String[]{this.rating,
                this.userId,
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

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getStoreId() {
        return storeId;
    }

    public void setStoreId(String storeId) {
        this.storeId = storeId;
    }

    @Override
    public String toString() {
        return "Rating : " + rating + "/10 by " + userId;
    }
}
