package comflick.myportfolio.mountis.flick.network;

import comflick.myportfolio.mountis.flick.model.MovieListResponse;
import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;


public interface MovieService {

    @GET("movie/popular")
    Observable<MovieListResponse> getPopularMovies(@Query("api_key") String apiKey);

    @GET("movie/top_rated")
    Observable<MovieListResponse> getHighRatedMovies(@Query("api_key") String apiKey);

}
