package comflick.myportfolio.mountis.flick.adapters;

import android.database.Cursor;
import android.database.DataSetObserver;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;

public abstract class CursorAdapter<ViewHolder extends RecyclerView.ViewHolder> extends RecyclerView.Adapter<ViewHolder> {

    private Cursor cursor;

    private boolean dataValid;

    private int rowIdColumn;

    private DataSetObserver dataSetObserver;

    public CursorAdapter(Cursor cursor) {
        this.cursor = cursor;
        dataValid = cursor != null;
        rowIdColumn = dataValid ? this.cursor.getColumnIndex( "_id" ) : -1;
        dataSetObserver = new NotifyingDataSetObserver();
        if (this.cursor != null) {
            this.cursor.registerDataSetObserver( dataSetObserver );
        }
    }

    public Cursor getCursor() {
        return cursor;
    }

    @Override
    public int getItemCount() {
        if (dataValid && cursor != null) {
            return cursor.getCount();
        }
        return 0;
    }

    @Override
    public long getItemId(int position) {
        if (dataValid && cursor != null && cursor.moveToPosition( position )) {
            return cursor.getLong( rowIdColumn );
        }
        return 0;
    }

    @Override
    public void setHasStableIds(boolean hasStableIds) {
        super.setHasStableIds( true );
    }

    public abstract void onBindViewHolder(ViewHolder viewHolder, Cursor cursor);

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int position) {
        if (!dataValid) {
            throw new IllegalStateException( "this should only be called when the cursor is valid" );
        }
        if (!cursor.moveToPosition( position )) {
            throw new IllegalStateException( "couldn't move cursor to position " + position );
        }
        onBindViewHolder( viewHolder, cursor );
    }

    public void changeCursor(Cursor cursor) {
        Cursor old = swapCursor( cursor );
        if (old != null) {
            old.close();
        }
    }

    public Cursor swapCursor(Cursor newCursor) {
        if (newCursor == cursor) {
            return null;
        }
        final Cursor oldCursor = cursor;
        if (oldCursor != null && dataSetObserver != null) {
            oldCursor.unregisterDataSetObserver( dataSetObserver );
        }
        cursor = newCursor;
        if (cursor != null) {
            if (dataSetObserver != null) {
                cursor.registerDataSetObserver( dataSetObserver );
            }
            rowIdColumn = newCursor.getColumnIndexOrThrow( "_id" );
            dataValid = true;
            notifyDataSetChanged();
        } else {
            rowIdColumn = -1;
            dataValid = false;
            notifyDataSetChanged();
        }
        return oldCursor;
    }

    private class NotifyingDataSetObserver extends DataSetObserver {
        @Override
        public void onChanged() {
            super.onChanged();
            dataValid = true;
            notifyDataSetChanged();
        }

        @Override
        public void onInvalidated() {
            super.onInvalidated();
            dataValid = false;
            notifyDataSetChanged();
        }
    }
}
