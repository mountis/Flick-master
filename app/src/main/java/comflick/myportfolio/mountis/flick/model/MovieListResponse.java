package comflick.myportfolio.mountis.flick.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;



public class MovieListResponse {

    @SerializedName("results")
    private List<Movie> movies;

    public List<Movie> getMovies() {
        return movies;
    }

    public void setMovies(List<Movie> movies) {
        this.movies = movies;
    }

}
