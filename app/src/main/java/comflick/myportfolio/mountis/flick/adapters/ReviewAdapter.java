package comflick.myportfolio.mountis.flick.adapters;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import comflick.myportfolio.mountis.flick.R;
import comflick.myportfolio.mountis.flick.model.Review;
import comflick.myportfolio.mountis.flick.utils.OnItemClickListener;
import comflick.myportfolio.mountis.flick.views.ReviewViewHolder;

public class ReviewAdapter extends RecyclerView.Adapter<ReviewViewHolder> {

    @Nullable
    private ArrayList<Review> reviews;
    @Nullable
    private OnItemClickListener onItemClickListener;

    public ReviewAdapter() {
        reviews = new ArrayList<>();
    }

    public void setOnItemClickListener(@Nullable OnItemClickListener listener) {
        this.onItemClickListener = listener;
    }

    @Nullable
    public ArrayList<Review> getReviews() {
        return reviews;
    }

    public void setReviews(@Nullable ArrayList<Review> reviews) {
        this.reviews = reviews;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ReviewViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from( parent.getContext() )
                .inflate( R.layout.list_item_movie_review, parent, false );
        return new ReviewViewHolder( itemView, onItemClickListener );
    }

    @Override
    public void onBindViewHolder(@NonNull ReviewViewHolder holder, int position) {
        if (reviews == null) {
            return;
        }
        Review review = reviews.get( position );
        holder.content.setText( review.getContent() );
        holder.author.setText( review.getAuthor() );
    }

    @Override
    public int getItemCount() {
        if (reviews == null) {
            return 0;
        }
        return reviews.size();
    }

    public Review getItem(int position) {
        if (reviews == null || position < 0 || position > reviews.size()) {
            return null;
        }
        return reviews.get( position );
    }
}
