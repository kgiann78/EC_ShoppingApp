package gr.uoa.ec.shopeeng.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import gr.uoa.ec.shopeeng.R;
import gr.uoa.ec.shopeeng.models.Comment;

import java.util.List;

public class CommentAdapter extends ArrayAdapter<Comment> {

    private static class CommentViewHolder {
        TextView userName;
        TextView userComment;
    }

    public CommentAdapter(Context context, @NonNull List<Comment> objects) {
        super(context, android.R.layout.simple_list_item_1, objects);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        CommentViewHolder commentViewHolder;

        if (convertView == null) {
            LayoutInflater layoutInflater = LayoutInflater.from(getContext());
            convertView = layoutInflater.inflate(R.layout.list_item_comment, parent, false);

            commentViewHolder = new CommentViewHolder();
            commentViewHolder.userName = (TextView) convertView.findViewById(R.id.user_name);
            commentViewHolder.userComment = (TextView) convertView.findViewById(R.id.user_comment);

            convertView.setTag(commentViewHolder);
        } else {
            commentViewHolder = (CommentViewHolder) convertView.getTag();
        }

        Comment comment = getItem(position);

        if (comment != null) {
            commentViewHolder.userName.setText(comment.getUserId());
            commentViewHolder.userComment.setText(comment.getComment());
        }

        return convertView;
    }
}
