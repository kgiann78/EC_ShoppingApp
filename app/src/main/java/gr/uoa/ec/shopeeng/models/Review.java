package gr.uoa.ec.shopeeng.models;


import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

public class Review implements Parcelable, Serializable {
    String username;
    String rdate;
    String rating;
    String comment;
    String storeId;

    public Review() {
    }

    public Review(String username, String rdate, String rating, String comment, String storeId) {
        this.username = username;
        this.rdate = rdate;
        this.rating = rating;
        this.comment = comment;
        this.storeId = storeId;
    }

    public Review(Parcel in) {
        String[] data = new String[5];
        in.readStringArray(data);
        this.username = data[0];
        this.rating = data[1];
        this.comment = data[2];
        this.rdate = data[3];
        this.storeId = data[4];

    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeStringArray(new String[]{this.username, String.valueOf(this.rating), this.comment, this.rdate, this.storeId});
    }

    public static final Parcelable.Creator CREATOR = new Parcelable.Creator() {
        public Review createFromParcel(Parcel in) {
            return new Review(in);
        }

        public Review[] newArray(int size) {
            return new Review[size];
        }
    };

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getRdate() {
        return rdate;
    }

    public void setRdate(String rdate) {
        this.rdate = rdate;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getStoreId() {
        return storeId;
    }

    public void setStoreId(String storeId) {
        this.storeId = storeId;
    }


}
