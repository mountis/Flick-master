package comflick.myportfolio.mountis.flick.activities;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ScrollView;

import java.util.Locale;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Optional;
import comflick.myportfolio.mountis.flick.App;
import comflick.myportfolio.mountis.flick.R;
import comflick.myportfolio.mountis.flick.fragment.DetailFragment;
import comflick.myportfolio.mountis.flick.fragment.GridFragment;
import comflick.myportfolio.mountis.flick.fragment.SortingFragment;
import comflick.myportfolio.mountis.flick.model.Movie;
import comflick.myportfolio.mountis.flick.network.FavoritesService;
import comflick.myportfolio.mountis.flick.utils.OnItemSelectedListener;
import comflick.myportfolio.mountis.flick.utils.SortHelper;

public class MainActivity extends AppCompatActivity implements OnItemSelectedListener {

    private static final String SELECTED_MOVIE_KEY = "MovieSelected";
    private static final String SELECTED_NAVIGATION_ITEM_KEY = "SelectedNavigationItem";

    @BindView(R.id.coordinator_layout)
    CoordinatorLayout coordinatorLayout;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @Nullable
    @BindView(R.id.movie_detail_container)
    ScrollView movieDetailContainer;
    @Nullable
    @BindView(R.id.fab)
    FloatingActionButton fab;

    @Inject
    SortHelper sortHelper;
    @Inject
    FavoritesService favoritesService;

    private boolean twoPaneMode;
    private Movie selectedMovie = null;
    private int selectedNavigationItem;

    private BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (action.equals( SortingFragment.BROADCAST_SORT_PREFERENCE_CHANGED )) {
                hideMovieDetailContainer();
                updateTitle();
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind( this );

        ((App) getApplication()).getNetworkComponent().inject( this );

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace( R.id.movies_grid_container, GridFragment.create() )
                    .commit();
        }
        twoPaneMode = movieDetailContainer != null;
        if (twoPaneMode && selectedMovie == null) {
            movieDetailContainer.setVisibility( View.GONE );
        }
        setupToolbar();
        setupFab();
    }

    @Override
    protected void onResume() {
        super.onResume();
        IntentFilter intentFilter = new IntentFilter( SortingFragment.BROADCAST_SORT_PREFERENCE_CHANGED );
        LocalBroadcastManager.getInstance( this ).registerReceiver( broadcastReceiver, intentFilter );
        updateTitle();
    }

    @Override
    protected void onPause() {
        super.onPause();
        LocalBroadcastManager.getInstance( this ).unregisterReceiver( broadcastReceiver );
    }

    private void updateTitle() {
        if (selectedNavigationItem == 0) {
            String[] sortTitles = getResources().getStringArray( R.array.sorting_labels );
            int currentSortIndex = sortHelper.getSortByPreference().ordinal();
            String title = Character.toString( sortTitles[currentSortIndex].charAt( 0 ) ).toUpperCase( Locale.US ) +
                    sortTitles[currentSortIndex].substring( 1 );
            setTitle( title );
        } else if (selectedNavigationItem == 1) {
            setTitle( getResources().getString( R.string.favorites_title ) );
        }
    }

    private void setupFab() {
        if (fab != null) {
            if (twoPaneMode && selectedMovie != null) {
                if (favoritesService.isFavorite( selectedMovie )) {
                    fab.setImageResource( R.drawable.ic_favorite_white );
                } else {
                    fab.setImageResource( R.drawable.ic_favorite_white_border );
                }
                fab.show();
            } else {
                fab.hide();
            }
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState( outState );
        outState.putParcelable( SELECTED_MOVIE_KEY, selectedMovie );
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState( savedInstanceState );
        if (savedInstanceState != null) {
            selectedMovie = savedInstanceState.getParcelable( SELECTED_MOVIE_KEY );
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        return super.onOptionsItemSelected( item );
    }

    @Override
    public void onItemSelected(Movie movie) {
        if (twoPaneMode && movieDetailContainer != null) {
            movieDetailContainer.setVisibility( View.VISIBLE );
            selectedMovie = movie;
            getSupportFragmentManager().beginTransaction()
                    .replace( R.id.movie_detail_container, DetailFragment.create( movie ) )
                    .commit();
            setupFab();
        } else {
            DetailActivity.start( this, movie );
        }
    }

    @Optional
    @OnClick(R.id.fab)
    void onFabClicked() {
        if (favoritesService.isFavorite( selectedMovie )) {
            favoritesService.removeFromFavorites( selectedMovie );
            showSnackbar( R.string.notification_removed_from_favorites );
            if (selectedNavigationItem == 1) {
                hideMovieDetailContainer();
            }
        } else {
            favoritesService.addToFavorites( selectedMovie );
            showSnackbar( R.string.notification_added_to_favorites );
        }
        setupFab();
    }

    private void showSnackbar(String message) {
        Snackbar.make( coordinatorLayout, message, Snackbar.LENGTH_LONG ).show();
    }

    private void showSnackbar(@StringRes int messageResourceId) {
        showSnackbar( getString( messageResourceId ) );
    }

    private void hideMovieDetailContainer() {
        selectedMovie = null;
        setupFab();
        if (twoPaneMode && movieDetailContainer != null) {
            movieDetailContainer.setVisibility( View.GONE );
        }
    }

    private void setupToolbar() {
        setSupportActionBar( toolbar );
    }

}
