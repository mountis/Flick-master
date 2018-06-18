package comflick.myportfolio.mountis.flick.views;

import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import butterknife.BindView;
import butterknife.ButterKnife;
import comflick.myportfolio.mountis.flick.R;
import comflick.myportfolio.mountis.flick.utils.OnItemClickListener;

public class TrailerViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    @BindView(R.id.movie_video_thumbnail)
    public
    ImageView trailerThumbnail;

    @Nullable
    private OnItemClickListener onItemClickListener;

    public TrailerViewHolder(View itemView, @Nullable OnItemClickListener onItemClickListener) {
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
