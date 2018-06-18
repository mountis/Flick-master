package comflick.myportfolio.mountis.flick.adapters;

import android.content.Context;
import android.database.Cursor;
import android.graphics.drawable.ColorDrawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.squareup.picasso.Picasso;

import comflick.myportfolio.mountis.flick.R;
import comflick.myportfolio.mountis.flick.model.Movie;
import comflick.myportfolio.mountis.flick.utils.OnItemClickListener;
import comflick.myportfolio.mountis.flick.views.GridItemViewHolder;

public class MoviesAdapter extends CursorAdapter<GridItemViewHolder> {

    private static final String POSTER_IMAGE_BASE_URL = "https://image.tmdb.org/t/p/";
    private static final String POSTER_IMAGE_SIZE = "w780";
    private final Context context;
    private OnItemClickListener onItemClickListener;

    public MoviesAdapter(Context context, Cursor cursor) {
        super( cursor );
        this.context = context;
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.onItemClickListener = listener;
    }

    @Override
    public void onBindViewHolder(GridItemViewHolder viewHolder, Cursor cursor) {
        if (cursor != null) {
            Movie movie = Movie.fromCursor( cursor );
            viewHolder.moviePoster.setContentDescription( movie.getTitle() );
            Picasso.with( context )
                    .load( POSTER_IMAGE_BASE_URL + POSTER_IMAGE_SIZE + movie.getPosterPath() )
                    .placeholder( new ColorDrawable( context.getResources().getColor( R.color.accent_material_light ) ) )
                    .fit()
                    .centerInside()
                    .into( viewHolder.moviePoster );
        }

    }

    @NonNull
    @Override
    public GridItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from( parent.getContext() ).inflate( R.layout.grid_item_movie, parent, false );
        return new GridItemViewHolder( itemView, onItemClickListener );
    }

    @Nullable
    public Movie getItem(int position) {
        Cursor cursor = getCursor();
        if (cursor == null) {
            return null;
        }
        if (position < 0 || position > cursor.getCount()) {
            return null;
        }
        cursor.moveToFirst();
        for (int i = 0; i < position; i++) {
            cursor.moveToNext();
        }
        return Movie.fromCursor( cursor );
    }

}
