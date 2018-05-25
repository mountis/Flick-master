package comflick.myportfolio.mountis.flick.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import comflick.myportfolio.mountis.flick.R;
import comflick.myportfolio.mountis.flick.model.Movie;

public class DetailActivity extends AppCompatActivity {

    private ImageView posterThumbnail;
    private TextView releaseDate;
    private TextView movieTitle;
    private TextView overview;
    private TextView voteAverage;
    private ProgressBar progressBarDetail;
    private Movie movie;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);

//        get movie details
        movie = getIntent().getExtras().getParcelable(getString(R.string.movie_intent_key));
        progressBarDetail = findViewById(R.id.progressBarDetail);

        initViews();
        loadDataIntoViews(movie);
    }

    // Allow for back navigation with arrow on App bar
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int selectedItemId = item.getItemId();
        if (selectedItemId == android.R.id.home) {
            onBackPressed();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    //    load the JSONdata received into its respective view
    private void loadDataIntoViews(Movie movie) {
        double userRating = movie.getVoteAverage();
        voteAverage.setText(userRating + "");
        overview.setText(movie.getOverview());
        movieTitle.setText(movie.getOriginalTitle());
        releaseDate.setText(movie.getReleaseDate().substring(0, 4));
        Picasso.with(this)
                .load(getString(R.string.movie_image_base_url) + movie.getPosterPath())
                .placeholder(R.drawable.movie_placeholder)
                .into(posterThumbnail);
    }

    //    Initialize the views to help load the data into the right view
    private void initViews() {
        releaseDate = findViewById(R.id.release_date);
        overview = findViewById(R.id.overview);
        movieTitle = findViewById(R.id.movie_title);
        voteAverage = findViewById(R.id.vote_average);
        posterThumbnail = findViewById(R.id.movie_poster_thumbnail);
    }
}
