package comflick.myportfolio.mountis.flick.fragment;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AlertDialog;

import javax.inject.Inject;

import comflick.myportfolio.mountis.flick.App;
import comflick.myportfolio.mountis.flick.R;
import comflick.myportfolio.mountis.flick.utils.Sort;
import comflick.myportfolio.mountis.flick.utils.SortHelper;

public class SortingFragment extends DialogFragment {

    public static final String BROADCAST_SORT_PREFERENCE_CHANGED = "SortPreferenceChanged";

    public static final String TAG = "SortingFragment";

    @Inject
    SortHelper sortHelper;

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        ((App) getActivity().getApplication()).getNetworkComponent().inject( this );

        final AlertDialog.Builder builder = new AlertDialog.Builder( getContext(), R.style.DialogStyle );
        builder.setTitle( getString( R.string.sort_title ) );
        builder.setNegativeButton( getString( R.string.action_cancel ), null );
        builder.setSingleChoiceItems( R.array.sorting_labels,
                sortHelper.getSortByPreference().ordinal(),
                (dialogInterface, which) -> {
                    sortHelper.saveSortByPreference( Sort.values()[which] );
                    sendSortPreferenceChangedBroadcast();
                    dialogInterface.dismiss();
                } );

        return builder.create();
    }

    private void sendSortPreferenceChangedBroadcast() {
        Intent intent = new Intent( BROADCAST_SORT_PREFERENCE_CHANGED );
        LocalBroadcastManager.getInstance( getContext() ).sendBroadcast( intent );
    }
}
