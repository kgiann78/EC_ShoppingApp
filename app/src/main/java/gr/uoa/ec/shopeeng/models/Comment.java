package gr.uoa.ec.shopeeng.models;

import android.os.Parcel;
import android.os.Parcelable;
import com.fasterxml.jackson.databind.ser.std.UUIDSerializer;

import java.io.Serializable;

public class Comment implements Serializable, Parcelable {
    private String username;
    private String storeId;
    private String commentId;
    private String comment;
    private String date;

    public Comment() {
    }

    public Comment(String username, String storeId, String commentId, String comment, String date) {
        this.username = username;
        this.storeId = storeId;
        this.commentId = commentId;
        this.comment = comment;
        this.date = date;
    }

    // Parcelling part
    public Comment(Parcel in) {
        String[] data = new String[3];
        in.readStringArray(data);
        this.username = data[0];
        this.storeId = data[1];
        this.commentId = data[2];
        this.comment = data[3];
        this.date = data[4];

    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeStringArray(new String[]{this.username, this.storeId, this.commentId, this.comment, this.date});
    }

    public static final Parcelable.Creator CREATOR = new Parcelable.Creator() {
        public Product createFromParcel(Parcel in) {
            return new Product(in);
        }

        public Product[] newArray(int size) {
            return new Product[size];
        }
    };

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
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

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getCommentId() {
        return commentId;
    }

    public void setCommentId(String commentId) {
        this.commentId = commentId;
    }

    @Override
    public String toString() {
        return "Comment By " + username + " on " + date + " :" + comment;

    }
}
