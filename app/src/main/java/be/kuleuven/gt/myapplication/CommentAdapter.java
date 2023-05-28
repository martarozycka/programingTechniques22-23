package be.kuleuven.gt.myapplication;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import be.kuleuven.gt.model.Comment;
import be.kuleuven.gt.model.Trip;

public class CommentAdapter extends RecyclerView.Adapter<CommentAdapter.ViewHolder> {

    private List<Comment> commentList;

    public CommentAdapter(List<Comment> commentList) {
        this.commentList = commentList;
    }

    @Override
    public CommentAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View commentView = layoutInflater.inflate(R.layout.comment_view, parent, false);
        CommentAdapter.ViewHolder myViewHolder = new CommentAdapter.ViewHolder(commentView);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(CommentAdapter.ViewHolder holder, int position) {
        Comment comment = commentList.get(position);
        //name of the user posting
        ((TextView) holder.comment.findViewById(R.id.userName))
                .setText(comment.getUsername());
        //comment
        ((TextView) holder.comment.findViewById(R.id.comment))
                .setText(comment.getCommentTxt());

    }

    @Override
    public int getItemCount() {
        return commentList.size();
    }


    static class ViewHolder extends RecyclerView.ViewHolder {
        public View comment;
        public ViewHolder(View commentView) {
            super(commentView);
            comment = (View) commentView;
        }

    }
}

