package comflick.myportfolio.mountis.flick.activities;

import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import comflick.myportfolio.mountis.flick.R;
import comflick.myportfolio.mountis.flick.model.Movie;

public class MovieDetailActivity extends AppCompatActivity {

    private ImageView posterThumbnail;
    private TextView releaseDate;
    private TextView movieTitle;
    private TextView overview;
    private TextView voteAverage;
    private CollapsingToolbarLayout collapsingToolbarLayout;
    private NestedScrollView scrollView;
    private Movie movie;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);
        movie = getIntent().getExtras().getParcelable(getString(R.string.movie_intent_key));

        initViews();
        enableActionBar();

        loadDataIntoViews(movie);
    }

    private void enableActionBar() {

        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {

            getSupportActionBar().setHomeButtonEnabled(true);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            collapsingToolbarLayout.setTitle(" ");

        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int selectedItemId = item.getItemId();
        if (selectedItemId == android.R.id.home) {
            onBackPressed();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void loadDataIntoViews(Movie movie) {
        double voteAverage = movie.getVoteAverage();
        movieTitle.setText(movie.getOriginalTitle());
        releaseDate.setText(movie.getReleaseDate().substring(0, 4));
        overview.setText(movie.getOverview());
        Picasso.with(this).load(getString(R.string.movie_image_base_url) + movie.getPosterPath()).into(posterThumbnail);

    }

    private void initViews() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        releaseDate = (TextView) findViewById(R.id.release_date);
        movieTitle = (TextView) findViewById(R.id.movie_title_TV);
        overview = (TextView) findViewById(R.id.overview);
        voteAverage = (TextView) findViewById(R.id.vote_average);
        collapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
        posterThumbnail = (ImageView) findViewById(R.id.movie_poster_thumbnail);
    }
}
