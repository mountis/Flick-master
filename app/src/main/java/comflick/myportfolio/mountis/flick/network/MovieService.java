package comflick.myportfolio.mountis.flick.network;

import comflick.myportfolio.mountis.flick.model.MovieListResults;
import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;


public interface MovieService {


    //    use RXJAVA observable to make multiple API calls with same observable
    @GET("movie/popular")
    Observable<MovieListResults> getPopularMovies(@Query("api_key") String apiKey);

    @GET("movie/top_rated")
    Observable<MovieListResults> getHighRatedMovies(@Query("api_key") String apiKey);

}
