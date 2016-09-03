package gr.uoa.ec.shopeeng.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.RatingBar;
import android.widget.TextView;
import gr.uoa.ec.shopeeng.R;
import gr.uoa.ec.shopeeng.models.Review;

import java.util.List;

public class ReviewAdapter extends ArrayAdapter<Review> {

    private static class ReviewViewHolder {
        TextView userName;
        TextView userComment;
        RatingBar userRating;
    }

    public ReviewAdapter(Context context, @NonNull List<Review> objects) {
        super(context, android.R.layout.simple_list_item_1, objects);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ReviewViewHolder reviewViewHolder;

        if (convertView == null) {
            LayoutInflater layoutInflater = LayoutInflater.from(getContext());
            convertView = layoutInflater.inflate(R.layout.list_item_comment, parent, false);
            reviewViewHolder = new ReviewViewHolder();
            reviewViewHolder.userName = (TextView) convertView.findViewById(R.id.user_name);
            reviewViewHolder.userComment = (TextView) convertView.findViewById(R.id.user_comment);
            reviewViewHolder.userRating = (RatingBar) convertView.findViewById(R.id.user_rate);
            convertView.setTag(reviewViewHolder);
        } else {
            reviewViewHolder = (ReviewViewHolder) convertView.getTag();
        }
        Review review = getItem(position);

        if (review != null) {
            reviewViewHolder.userName.setText(review.getUserId());
            reviewViewHolder.userComment.setText(review.getComment());
            reviewViewHolder.userRating.setNumStars((int) (Double.valueOf(review.getRating()) * 5 / 10));
        }
        return convertView;
    }
}
