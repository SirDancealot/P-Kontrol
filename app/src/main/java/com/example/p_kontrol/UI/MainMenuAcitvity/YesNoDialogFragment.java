package com.example.p_kontrol.UI.MainMenuAcitvity;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.ContextThemeWrapper;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.example.p_kontrol.R;
/**
 * @responsibilty to Close MainMenu and being used by the backstack, only in the mainAcitivty.
 *
 * */
public class YesNoDialogFragment extends DialogFragment {

    Activity caller;
    int dialogType;

    public YesNoDialogFragment(Activity caller, int dialogType) {
        this.caller = caller;
        this.dialogType = dialogType;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(new ContextThemeWrapper(caller, 0));

        if( dialogType == 0) { // type Close app

            builder.setTitle(R.string.close_frag_title0);
            builder.setPositiveButton(R.string.close_frag_confirm, ((dialog, which) -> {
                caller.finish();
                System.exit(0);
            }));
            builder.setNegativeButton(R.string.close_frag_deny, ((dialog, which) -> {
                dialog.cancel();
            }));

        } else if (dialogType == 1){ // delete all personal data

            builder.setTitle(R.string.close_frag_title1);
            builder.setPositiveButton(R.string.close_frag_confirm, ((dialog, which) -> {

                caller.finish();
                System.exit(0);
            }));
            builder.setNegativeButton(R.string.close_frag_deny, ((dialog, which) -> {
                dialog.cancel();
            }));

        } else { // facebook feedback, as requested by PO.

            String facebookPageId = "109226947290458";
            builder.setTitle(R.string.close_frag_title2);
            builder.setPositiveButton(R.string.close_frag_confirm, ((dialog, which) -> {
                try {
                    Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse("fb://page/" + facebookPageId));
                    startActivity(i);
                } catch (ActivityNotFoundException e) {
                    Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.facebook.com/" + facebookPageId));
                    startActivity(i);
                }
            }));
            builder.setNegativeButton(R.string.close_frag_deny, ((dialog, which) -> {
                dialog.cancel();
            }));

        }

        return builder.create();
    }
}
