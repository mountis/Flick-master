package comflick.myportfolio.mountis.flick.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class TrailersResponse {
    @SerializedName("id")
    private long movieId;

    @SerializedName("results")
    private ArrayList<Trailer> results;

    public TrailersResponse(long movieId, ArrayList<Trailer> results) {
        this.movieId = movieId;
        this.results = results;
    }

    public long getMovieId() {
        return movieId;
    }

    public ArrayList<Trailer> getResults() {
        return results;
    }
}
