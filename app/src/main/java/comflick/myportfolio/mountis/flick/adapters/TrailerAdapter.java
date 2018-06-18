package comflick.myportfolio.mountis.flick.adapters;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import comflick.myportfolio.mountis.flick.R;
import comflick.myportfolio.mountis.flick.model.Trailer;
import comflick.myportfolio.mountis.flick.utils.OnItemClickListener;
import comflick.myportfolio.mountis.flick.views.TrailerViewHolder;

public class TrailerAdapter extends RecyclerView.Adapter<TrailerViewHolder> {

    private static final String YOUTUBE_THUMBNAIL = "https://img.youtube.com/vi/%s/mqdefault.jpg";
    private final Context context;

    @Nullable
    private ArrayList<Trailer> trailers;
    @Nullable
    private OnItemClickListener onItemClickListener;

    public TrailerAdapter(Context context) {
        this.context = context;
        trailers = new ArrayList<>();
    }

    public void setOnItemClickListener(@Nullable OnItemClickListener listener) {
        this.onItemClickListener = listener;
    }

    @Nullable
    public ArrayList<Trailer> getTrailers() {
        return trailers;
    }

    public void setTrailers(@Nullable ArrayList<Trailer> trailers) {
        this.trailers = trailers;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public TrailerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from( parent.getContext() ).inflate( R.layout.list_item_movie_trailer, parent, false );
        return new TrailerViewHolder( itemView, onItemClickListener );
    }

    @Override
    public void onBindViewHolder(@NonNull TrailerViewHolder holder, int position) {
        if (trailers == null) {
            return;
        }
        Trailer video = trailers.get( position );
        if (video.isYoutubeVideo()) {
            Picasso.with( context )
                    .load( String.format( YOUTUBE_THUMBNAIL, video.getKey() ) )
                    .placeholder( new ColorDrawable( context.getResources().getColor( R.color.accent_material_light ) ) )
                    .centerInside()
                    .fit()
                    .into( holder.trailerThumbnail );
        }
    }

    @Override
    public int getItemCount() {
        if (trailers == null) {
            return 0;
        }
        return trailers.size();
    }

    public Trailer getItem(int position) {
        if (trailers == null || position < 0 || position > trailers.size()) {
            return null;
        }
        return trailers.get( position );
    }
}
