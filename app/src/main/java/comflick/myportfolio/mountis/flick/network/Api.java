package comflick.myportfolio.mountis.flick.network;

import comflick.myportfolio.mountis.flick.model.Movie;
import comflick.myportfolio.mountis.flick.model.MovieResponse;
import comflick.myportfolio.mountis.flick.model.ReviewsResponse;
import comflick.myportfolio.mountis.flick.model.TrailersResponse;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;
import rx.Observable;

public interface Api {

    @GET("movie/{id}/videos")
    Observable<TrailersResponse> getTrailers(@Path("id") long movieId);

    @GET("movie/{id}/reviews")
    Observable<ReviewsResponse> getReviews(@Path("id") long movieId);

    @GET("movie/popular")
    Observable<MovieResponse<Movie>> getPopularMovies(@Query("sort_by") String sortBy,
                                                      @Query("page") Integer page);

    @GET("movie/top_rated")
    Observable<MovieResponse<Movie>> getTopRatedMovies(@Query("sort_by") String query,
                                                       @Query("page") Integer page);

}
