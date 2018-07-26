package com.example.nspain.unoapp;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;

/**
 * Alert dialog to tell the user they have tried to pick up a card when they can still play cards
 * in their hand.
 */
public class IllegalPickupAlert extends  DialogFragment {

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage(R.string.illegal_pickup_message)
                .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        IllegalPickupAlert.this.getDialog().cancel();
                    }
                });
        return builder.create();
    }
}
