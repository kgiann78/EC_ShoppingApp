package gr.uoa.ec.shopeeng.models;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;
import java.util.UUID;

public class Comment implements Serializable, Parcelable {
    private String userId;
    private String storeId;
    private String commentId;
    private String comment;
    private String date;

    public Comment() {
    }

    public Comment(String userId, String storeId, String commentId, String comment, String date) {
        this.userId = userId;
        this.storeId = storeId;
        this.commentId = commentId;
        this.comment = comment;
        this.date = date;
    }

    // Parcelling part
    public Comment(Parcel in) {
        String[] data = new String[3];
        in.readStringArray(data);
        this.userId = data[0];
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
        dest.writeStringArray(new String[]{this.userId, this.storeId, this.commentId, this.comment, this.date});
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
        return "Comment By " + userId + " on " + date + " :" + comment;

    }
}
