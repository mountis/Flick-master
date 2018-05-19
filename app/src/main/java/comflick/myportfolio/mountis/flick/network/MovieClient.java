package comflick.myportfolio.mountis.flick.network;


import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import comflick.myportfolio.mountis.flick.App;
import comflick.myportfolio.mountis.flick.R;
import comflick.myportfolio.mountis.flick.model.MovieListResponse;
import comflick.myportfolio.mountis.flick.util.MovieFilter;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Observable;

public class MovieClient {

    private static MovieClient instance;
    private MovieService movieService;
    private String apiKey;

    private MovieClient() {
        final Gson gson =
                new GsonBuilder().setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES).create();
        final Retrofit retrofit = new Retrofit.Builder().baseUrl(App.getContext().getString(R.string.base_url))
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
        movieService = retrofit.create(MovieService.class);
        apiKey = App.getContext().getString(R.string.api_key);
    }

    public static MovieClient getInstance() {
        if (instance == null) {
            instance = new MovieClient();
        }
        return instance;
    }

    public Observable<MovieListResponse> getMovies(@MovieFilter.movieFilter int movieFilter) {
        if(movieFilter == MovieFilter.HIGH_RATED){

            return movieService.getHighRatedMovies(apiKey);
        }
        return movieService.getPopularMovies(apiKey);
    }
}
