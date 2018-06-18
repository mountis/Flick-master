package comflick.myportfolio.mountis.flick.utils;

import android.content.SharedPreferences;
import android.net.Uri;

import javax.inject.Inject;

import comflick.myportfolio.mountis.flick.data.MoviesContract;

public final class SortHelper {

    private static final String PREF_SORT_BY_KEY = "sortBy";
    private static final String PREF_SORT_BY_DEFAULT_VALUE = "popularity.desc";

    private SharedPreferences sharedPreferences;

    @Inject
    public SortHelper(SharedPreferences sharedPreferences) {
        this.sharedPreferences = sharedPreferences;
    }

    public Sort getSortByPreference() {
        String sort = sharedPreferences.getString(
                PREF_SORT_BY_KEY,
                PREF_SORT_BY_DEFAULT_VALUE
        );
        return Sort.fromString( sort );
    }

    public Uri getSortedMoviesUri() {
        Sort sort = getSortByPreference();
        switch (sort) {
            case MOST_POPULAR:
                return MoviesContract.MostPopularMovies.CONTENT_URI;
            case HIGHEST_RATED:
                return MoviesContract.HighestRatedMovies.CONTENT_URI;
            case FAVORITES:
                return MoviesContract.Favorites.CONTENT_URI;
            default:
                throw new IllegalStateException( "Unknown sort." );
        }
    }

    public boolean saveSortByPreference(Sort sort) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(
                PREF_SORT_BY_KEY,
                sort.toString()
        );
        return editor.commit();
    }
}
