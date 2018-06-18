package comflick.myportfolio.mountis.flick.views;

import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import butterknife.BindView;
import butterknife.ButterKnife;
import comflick.myportfolio.mountis.flick.R;
import comflick.myportfolio.mountis.flick.utils.OnItemClickListener;

public class GridItemViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    @BindView(R.id.image_movie_poster)
    public
    ImageView moviePoster;

    private OnItemClickListener onItemClickListener;

    public GridItemViewHolder(View itemView, @Nullable OnItemClickListener onItemClickListener) {
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
