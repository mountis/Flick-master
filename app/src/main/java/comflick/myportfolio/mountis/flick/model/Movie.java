package comflick.myportfolio.mountis.flick.model;

import android.content.ContentValues;
import android.database.Cursor;
import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import comflick.myportfolio.mountis.flick.data.MoviesContract;

public class Movie implements Parcelable {

    public static final Parcelable.Creator<Movie> CREATOR = new Parcelable.Creator<Movie>() {
        @Override
        public Movie createFromParcel(Parcel source) {
            return new Movie( source );
        }

        @Override
        public Movie[] newArray(int size) {
            return new Movie[size];
        }
    };

    @SerializedName("id")
    private long id;

    @SerializedName("original_title")
    private String originalTitle;

    @SerializedName("overview")
    private String overview;

    @SerializedName("release_date")
    private String releaseDate;

    @SerializedName("poster_path")
    private String posterPath;

    @SerializedName("title")
    private String title;

    @SerializedName("vote_average")
    private double averageVote;

    @SerializedName("backdrop_path")
    private String backdropPath;

    public Movie(long id, String title) {
        this.id = id;
        this.title = title;
    }

    protected Movie(Parcel in) {
        this.id = in.readLong();
        this.originalTitle = in.readString();
        this.overview = in.readString();
        this.releaseDate = in.readString();
        this.posterPath = in.readString();
        this.title = in.readString();
        this.averageVote = in.readDouble();
        this.backdropPath = in.readString();
    }

    public static Movie fromCursor(Cursor cursor) {
        long id = cursor.getLong( cursor.getColumnIndex( MoviesContract.MovieEntry._ID ) );
        String title = cursor.getString( cursor.getColumnIndex( MoviesContract.MovieEntry.COLUMN_TITLE ) );
        Movie movie = new Movie( id, title );
        movie.setOriginalTitle(
                cursor.getString( cursor.getColumnIndex( MoviesContract.MovieEntry.COLUMN_ORIGINAL_TITLE ) ) );
        movie.setOverview(
                cursor.getString( cursor.getColumnIndex( MoviesContract.MovieEntry.COLUMN_OVERVIEW ) ) );
        movie.setReleaseDate(
                cursor.getString( cursor.getColumnIndex( MoviesContract.MovieEntry.COLUMN_RELEASE_DATE ) ) );
        movie.setPosterPath(
                cursor.getString( cursor.getColumnIndex( MoviesContract.MovieEntry.COLUMN_POSTER_PATH ) ) );
        movie.setAverageVote(
                cursor.getDouble( cursor.getColumnIndex( MoviesContract.MovieEntry.COLUMN_AVERAGE_VOTE ) ) );
        movie.setBackdropPath(
                cursor.getString( cursor.getColumnIndex( MoviesContract.MovieEntry.COLUMN_BACKDROP_PATH ) ) );
        return movie;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getOriginalTitle() {
        return originalTitle;
    }

    public void setOriginalTitle(String originalTitle) {
        this.originalTitle = originalTitle;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    public String getPosterPath() {
        return posterPath;
    }

    public void setPosterPath(String posterPath) {
        this.posterPath = posterPath;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public double getAverageVote() {
        return averageVote;
    }

    public void setAverageVote(double averageVote) {
        this.averageVote = averageVote;
    }

    public String getBackdropPath() {
        return backdropPath;
    }

    public void setBackdropPath(String backdropPath) {
        this.backdropPath = backdropPath;
    }

    @Override
    public String toString() {
        return "[" + this.id + ", " + this.title + "]";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Movie movie = (Movie) o;

        if (id != movie.id) return false;
        if (Double.compare( movie.averageVote, averageVote ) != 0) return false;
        if (originalTitle != null ? !originalTitle.equals( movie.originalTitle ) : movie.originalTitle != null)
            return false;
        if (overview != null ? !overview.equals( movie.overview ) : movie.overview != null)
            return false;
        if (releaseDate != null ? !releaseDate.equals( movie.releaseDate ) : movie.releaseDate != null)
            return false;
        if (posterPath != null ? !posterPath.equals( movie.posterPath ) : movie.posterPath != null)
            return false;
        if (title != null ? !title.equals( movie.title ) : movie.title != null) return false;
        return backdropPath != null ? backdropPath.equals( movie.backdropPath ) : movie.backdropPath == null;

    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        result = (int) (id ^ (id >>> 32));
        result = 31 * result + (originalTitle != null ? originalTitle.hashCode() : 0);
        result = 31 * result + (overview != null ? overview.hashCode() : 0);
        result = 31 * result + (releaseDate != null ? releaseDate.hashCode() : 0);
        result = 31 * result + (posterPath != null ? posterPath.hashCode() : 0);
        result = 31 * result + (title != null ? title.hashCode() : 0);
        temp = Double.doubleToLongBits( averageVote );
        result = 31 * result + (backdropPath != null ? backdropPath.hashCode() : 0);
        return result;
    }

    public ContentValues toContentValues() {
        ContentValues values = new ContentValues();
        values.put( MoviesContract.MovieEntry._ID, id );
        values.put( MoviesContract.MovieEntry.COLUMN_ORIGINAL_TITLE, originalTitle );
        values.put( MoviesContract.MovieEntry.COLUMN_OVERVIEW, overview );
        values.put( MoviesContract.MovieEntry.COLUMN_RELEASE_DATE, releaseDate );
        values.put( MoviesContract.MovieEntry.COLUMN_POSTER_PATH, posterPath );
        values.put( MoviesContract.MovieEntry.COLUMN_TITLE, title );
        values.put( MoviesContract.MovieEntry.COLUMN_AVERAGE_VOTE, averageVote );
        values.put( MoviesContract.MovieEntry.COLUMN_BACKDROP_PATH, backdropPath );
        return values;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong( this.id );
        dest.writeString( this.originalTitle );
        dest.writeString( this.overview );
        dest.writeString( this.releaseDate );
        dest.writeString( this.posterPath );
        dest.writeString( this.title );
        dest.writeDouble( this.averageVote );
        dest.writeString( this.backdropPath );
    }
}
