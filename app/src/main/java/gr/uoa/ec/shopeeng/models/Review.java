package gr.uoa.ec.shopeeng.models;


import android.os.Parcel;
import android.os.Parcelable;

public class Review implements Parcelable {
    String user;
    String date;
    int rating;
    String comment;


    // Parcelling part
    public Review(Parcel in) {
        String[] data = new String[4];
        in.readStringArray(data);
        this.user = data[0];
        this.rating = Integer.parseInt(data[1]);
        this.comment = data[2];
        this.date = data[3];

    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeStringArray(new String[]{this.user, String.valueOf(this.rating), this.comment, this.date});
    }

    public static final Parcelable.Creator CREATOR = new Parcelable.Creator() {
        public Product createFromParcel(Parcel in) {
            return new Product(in);
        }

        public Product[] newArray(int size) {
            return new Product[size];
        }
    };

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}
