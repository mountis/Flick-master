package comflick.myportfolio.mountis.flick.views;

import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import comflick.myportfolio.mountis.flick.R;
import comflick.myportfolio.mountis.flick.utils.OnItemClickListener;

public class ReviewViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    @BindView(R.id.text_movie_review_content)
    public
    TextView content;
    @BindView(R.id.text_movie_review_author)
    public
    TextView author;

    @Nullable
    private OnItemClickListener onItemClickListener;

    public ReviewViewHolder(View itemView, @Nullable OnItemClickListener onItemClickListener) {
        super( itemView );
        ButterKnife.bind( this, itemView );
        this.onItemClickListener = onItemClickListener;
        itemView.setOnClickListener( this );
    }

    @Override
    public void onClick(View view) {
        if (onItemClickListener != null) {
            onItemClickListener.onItemClick( view, getAdapterPosition() );
        }
    }
}
