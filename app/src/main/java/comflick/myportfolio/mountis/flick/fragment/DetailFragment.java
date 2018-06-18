package comflick.myportfolio.mountis.flick.fragment;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.ColorInt;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.CardView;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.trello.rxlifecycle.components.support.RxFragment;

import java.util.ArrayList;
import java.util.Locale;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import comflick.myportfolio.mountis.flick.App;
import comflick.myportfolio.mountis.flick.ItemOffsetDecoration;
import comflick.myportfolio.mountis.flick.R;
import comflick.myportfolio.mountis.flick.adapters.ReviewAdapter;
import comflick.myportfolio.mountis.flick.adapters.TrailerAdapter;
import comflick.myportfolio.mountis.flick.model.Movie;
import comflick.myportfolio.mountis.flick.model.Review;
import comflick.myportfolio.mountis.flick.model.ReviewsResponse;
import comflick.myportfolio.mountis.flick.model.Trailer;
import comflick.myportfolio.mountis.flick.model.TrailersResponse;
import comflick.myportfolio.mountis.flick.network.Api;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class DetailFragment extends RxFragment {

    private static final String POSTER_IMAGE_BASE_URL = "https://image.tmdb.org/t/p/";
    private static final String POSTER_IMAGE_SIZE = "w780";

    private static final String ARG_MOVIE = "ArgMovie";
    private static final String TRAILERS_KEY = "Trailers";
    private static final String REVIEWS_KEY = "Reviews";
    private static final String LOG_TAG = "DetailFragment";

    private static final double VOTE_PERFECT = 9.0;
    private static final double VOTE_GOOD = 7.0;
    private static final double VOTE_NORMAL = 5.0;

    @BindView(R.id.image_movie_detail_poster)
    ImageView movieImagePoster;
    @BindView(R.id.text_movie_original_title)
    TextView movieOriginalTitle;
    @BindView(R.id.text_movie_user_rating)
    TextView movieUserRating;
    @BindView(R.id.text_movie_release_date)
    TextView movieReleaseDate;
    @BindView(R.id.text_movie_overview)
    TextView movieOverview;
    @BindView(R.id.card_movie_detail)
    CardView cardDetail;
    @BindView(R.id.card_movie_overview)
    CardView cardOverview;
    @BindView(R.id.card_movie_trailers)
    CardView cardTrailers;
    @BindView(R.id.movie_trailers)
    RecyclerView recyclerViewTrailers;
    @BindView(R.id.card_movie_reviews)
    CardView cardReviews;
    @BindView(R.id.movie_reviews)
    RecyclerView recyclerViewReviews;

    @Inject
    Api api;

    private Movie movie;
    private TrailerAdapter trailerAdapter;
    private ReviewAdapter reviewsAdapter;

    public DetailFragment() {
    }

    public static DetailFragment create(Movie movie) {
        DetailFragment fragment = new DetailFragment();
        Bundle args = new Bundle();
        args.putParcelable( ARG_MOVIE, movie );
        fragment.setArguments( args );
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        if (getArguments() != null) {
            movie = getArguments().getParcelable( ARG_MOVIE );
        }

        ((App) getActivity().getApplication()).getNetworkComponent().inject( this );
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate( R.layout.fragment_movie_detail, container, false );
        ButterKnife.bind( this, rootView );
        initViews();
        initTrailerList();
        initReviewsList();
        setupCardsElevation();
        return rootView;
    }


    @Override
    public void onResume() {
        super.onResume();
        if (trailerAdapter.getItemCount() == 0) {
            loadTrailers();
        }
        if (reviewsAdapter.getItemCount() == 0) {
            loadReviews();
        }
        updateTrailersCard();
        updateReviewsCard();
    }


    private void setupCardsElevation() {
        setupCardElevation( cardDetail );
        setupCardElevation( cardTrailers );
        setupCardElevation( cardOverview );
        setupCardElevation( cardReviews );
    }

    private void updateTrailersCard() {
        if (trailerAdapter == null || trailerAdapter.getItemCount() == 0) {
            cardTrailers.setVisibility( View.GONE );
        } else {
            cardTrailers.setVisibility( View.VISIBLE );
        }
    }

    private void updateReviewsCard() {
        if (reviewsAdapter == null || reviewsAdapter.getItemCount() == 0) {
            cardReviews.setVisibility( View.GONE );
        } else {
            cardReviews.setVisibility( View.VISIBLE );
        }
    }

    private void setupCardElevation(View view) {
        ViewCompat.setElevation( view,
                convertDpToPixel( getResources().getInteger( R.integer.movie_detail_content_elevation_in_dp ) ) );
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState( outState );
        if (trailerAdapter.getItemCount() != 0) {
            outState.putParcelableArrayList( TRAILERS_KEY, trailerAdapter.getTrailers() );
        }
        if (reviewsAdapter.getItemCount() != 0) {
            outState.putParcelableArrayList( REVIEWS_KEY, reviewsAdapter.getReviews() );
        }
    }

    @Override
    public void onViewStateRestored(@Nullable Bundle savedInstanceState) {
        super.onViewStateRestored( savedInstanceState );
        if (savedInstanceState != null) {
            trailerAdapter.setTrailers( savedInstanceState.getParcelableArrayList( TRAILERS_KEY ) );
            reviewsAdapter.setReviews( savedInstanceState.getParcelableArrayList( REVIEWS_KEY ) );
        }
    }

    private void loadTrailers() {
        api.getTrailers( movie.getId() )
                .compose( bindToLifecycle() )
                .subscribeOn( Schedulers.newThread() )
                .map( TrailersResponse::getResults )
                .observeOn( AndroidSchedulers.mainThread() )
                .subscribe( new Subscriber<ArrayList<Trailer>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e( LOG_TAG, e.getMessage() );
                        updateTrailersCard();
                    }

                    @Override
                    public void onNext(ArrayList<Trailer> trailers) {
                        trailerAdapter.setTrailers( trailers );
                        updateTrailersCard();
                    }
                } );
    }

    private void loadReviews() {
        api.getReviews( movie.getId() )
                .compose( bindToLifecycle() )
                .subscribeOn( Schedulers.newThread() )
                .map( ReviewsResponse::getResults )
                .observeOn( AndroidSchedulers.mainThread() )
                .subscribe( new Subscriber<ArrayList<Review>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e( LOG_TAG, e.getMessage() );
                        updateReviewsCard();
                    }

                    @Override
                    public void onNext(ArrayList<Review> reviews) {
                        reviewsAdapter.setReviews( reviews );
                        updateReviewsCard();
                    }
                } );
    }

    private void initViews() {
        Picasso.with( getContext().getApplicationContext() )
                .load( POSTER_IMAGE_BASE_URL + POSTER_IMAGE_SIZE + movie.getPosterPath() )
                .fit()
                .centerInside()
                .into( movieImagePoster );
        movieOriginalTitle.setText( movie.getOriginalTitle() );
        movieUserRating.setText( String.format( Locale.US, "%.1f", movie.getAverageVote() ) );
        movieUserRating.setTextColor( getRatingColor( movie.getAverageVote() ) );
        String releaseDate = String.format( getString( R.string.release_date ),
                movie.getReleaseDate() );
        movieReleaseDate.setText( releaseDate );
        movieOverview.setText( movie.getOverview() );
    }

    @ColorInt
    private int getRatingColor(double averageVote) {
        if (averageVote >= VOTE_PERFECT) {
            return ContextCompat.getColor( getContext(), R.color.vote_perfect );
        } else if (averageVote >= VOTE_GOOD) {
            return ContextCompat.getColor( getContext(), R.color.vote_good );
        } else if (averageVote >= VOTE_NORMAL) {
            return ContextCompat.getColor( getContext(), R.color.vote_normal );
        } else {
            return ContextCompat.getColor( getContext(), R.color.vote_bad );
        }
    }

    private void initTrailerList() {
        trailerAdapter = new TrailerAdapter( getContext() );
        trailerAdapter.setOnItemClickListener( (itemView, position) -> onTrailerClicked( position ) );
        recyclerViewTrailers.setAdapter( trailerAdapter );
        recyclerViewTrailers.setItemAnimator( new DefaultItemAnimator() );
        recyclerViewTrailers.addItemDecoration( new ItemOffsetDecoration( getActivity(), R.dimen.movie_item_offset ) );
        LinearLayoutManager layoutManager = new LinearLayoutManager( getContext(),
                LinearLayoutManager.HORIZONTAL, false );
        recyclerViewTrailers.setLayoutManager( layoutManager );
    }

    private void initReviewsList() {
        reviewsAdapter = new ReviewAdapter();
        reviewsAdapter.setOnItemClickListener( (itemView, position) -> onReviewClicked( position ) );
        recyclerViewReviews.setAdapter( reviewsAdapter );
        recyclerViewReviews.setItemAnimator( new DefaultItemAnimator() );
        LinearLayoutManager layoutManager = new LinearLayoutManager( getContext() );
        recyclerViewReviews.setLayoutManager( layoutManager );
    }

    private void onReviewClicked(int position) {
        Review review = reviewsAdapter.getItem( position );
        if (review != null && review.getReviewUrl() != null) {
            Intent intent = new Intent( Intent.ACTION_VIEW, Uri.parse( review.getReviewUrl() ) );
            startActivity( intent );
        }
    }

    private void onTrailerClicked(int position) {
        Trailer video = trailerAdapter.getItem( position );
        if (video != null && video.isYoutubeVideo()) {
            Intent intent = new Intent( Intent.ACTION_VIEW,
                    Uri.parse( "http://www.youtube.com/watch?v=" + video.getKey() ) );
            startActivity( intent );
        }
    }

    public float convertDpToPixel(float dp) {
        DisplayMetrics metrics = getResources().getDisplayMetrics();
        return dp * ((float) metrics.densityDpi / DisplayMetrics.DENSITY_DEFAULT);
    }

}
