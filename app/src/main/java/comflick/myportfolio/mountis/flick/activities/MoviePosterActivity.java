package comflick.myportfolio.mountis.flick.activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ProgressBar;

import java.util.List;

import comflick.myportfolio.mountis.flick.R;
import comflick.myportfolio.mountis.flick.adapters.MovieAdapter;
import comflick.myportfolio.mountis.flick.model.Movie;
import comflick.myportfolio.mountis.flick.model.MovieListResponse;
import comflick.myportfolio.mountis.flick.network.MovieClient;
import comflick.myportfolio.mountis.flick.util.MovieFilter;
import rx.Observer;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class MoviePosterActivity extends AppCompatActivity {

    private GridView moviePosterView;
    private List<Movie> movies;
    private ProgressBar progressBar;
    private Subscription subscription;
    private MovieAdapter movieAdapter;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        moviePosterView = findViewById(R.id.movie_posters_view);
        progressBar = findViewById(R.id.progressBar);
        showMovies(MovieFilter.POPULAR);
        moviePosterView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Movie selectedMovie = movies.get(position);

                Intent intent = new Intent(MoviePosterActivity.this, MovieDetailActivity.class);
                intent.putExtra(getString(R.string.movie_intent_key), selectedMovie);

                startActivity(intent);
            }
        });


    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.activity_poster_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.sort_by_most_popular:
                showMovies(MovieFilter.POPULAR);
                return true;
            case R.id.sort_by_highest_rated:
                showMovies(MovieFilter.HIGH_RATED);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void showMovies() {
        movieAdapter = new MovieAdapter(this, movies);
        moviePosterView.setAdapter(movieAdapter);
    }

    private void showMovies(@MovieFilter.movieFilter int filter) {
        progressBar.setVisibility(View.VISIBLE);
        subscription = MovieClient.getInstance()
                .getMovies(filter)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<MovieListResponse>() {
                    @Override
                    public void onCompleted() {
                        progressBar.setVisibility(View.GONE);
                    }

                    @Override
                    public void onError(Throwable e) {
                        progressBar.setVisibility(View.GONE);
                    }

                    @Override
                    public void onNext(MovieListResponse movieListResponse) {
                        movies = movieListResponse.getMovies();
                        showMovies();
                    }
                });
    }


    @Override
    protected void onDestroy() {
        if (subscription != null && !subscription.isUnsubscribed()) {
            subscription.unsubscribe();
        }
        super.onDestroy();


    }
}

