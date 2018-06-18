package comflick.myportfolio.mountis.flick.adapters;

import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public abstract class ArrayAdapter<Movie, ViewHolder extends RecyclerView.ViewHolder> extends RecyclerView.Adapter<ViewHolder> {

    private List<Movie> items;

    public ArrayAdapter(@Nullable List<Movie> items) {
        if (items == null) {
            this.items = new ArrayList<>();
        } else {
            this.items = items;
        }
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public void clear() {
        items = new ArrayList<>();
    }

    public List<Movie> getItems() {
        return items;
    }

    public void setItems(List<Movie> items) {
        this.items = items;
    }

    @Nullable
    public Movie getItem(int position) {
        if (items == null) {
            return null;
        }
        if (position < 0 || position > items.size()) {
            return null;
        }
        return items.get( position );
    }
}
