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
    //extends RecyclerView.Adapter<CommentAdapter.ViewHolder>

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
            //comment.setOnClickListener((View.OnClickListener) this);
        }


//        public void onClick(View commentView) {
//            TextView tripName = (TextView) tripView.findViewById(R.id.tripName);
//            TextView tripStartDate = (TextView) tripView.findViewById(R.id.tripStartDate);
//            TextView tripEndDate = (TextView) tripView.findViewById(R.id.tripEndDate);
////            Trip trip = new Trip(
////                    tripName.getText().toString(),
////                    tripStartDate.getText().toString(),
////                    tripEndDate.getText().toString()
////            );
//            Intent intent = new Intent(tripView.getContext(), TripLogActivity.class);
////            intent.putExtra("Trip", trip);
//            tripView.getContext().startActivity(intent);

        }
    }

//    @NonNull
//    @Override
//    public CommentAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//        return null;
//    }
//
//    @Override
//    public void onBindViewHolder(@NonNull CommentAdapter.ViewHolder holder, int position) {
//
//    }
//
//    @Override
//    public int getItemCount() {
//        return 0;
//    }

