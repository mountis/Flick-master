package comflick.myportfolio.mountis.flick.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class ReviewsResponse {
    @SerializedName("id")
    private long movieId;

    @SerializedName("page")
    private int page;

    @SerializedName("results")
    private ArrayList<Review> results;

    @SerializedName("total_pages")
    private int totalPages;

    public ReviewsResponse(long movieId, int page, ArrayList<Review> results, int totalPages) {
        this.movieId = movieId;
        this.page = page;
        this.results = results;
        this.totalPages = totalPages;
    }

    public long getMovieId() {
        return movieId;
    }

    public int getPage() {
        return page;
    }

    public ArrayList<Review> getResults() {
        return results;
    }

    public int getTotalPages() {
        return totalPages;
    }
}
