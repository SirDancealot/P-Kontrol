package com.example.p_kontrol.UI.MainMenuAcitvity;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
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

        if( dialogType == 0) {

            builder.setTitle(R.string.close_frag_title0)
            /*.setMessage("")*/;

            builder.setPositiveButton(R.string.close_frag_confirm, ((dialog, which) -> {
                caller.finish();
                System.exit(0);
            }));

            builder.setNegativeButton(R.string.close_frag_deny, ((dialog, which) -> {
                dialog.cancel();
            }));
        } else if (dialogType == 1){

            builder.setTitle(R.string.close_frag_title1)
            /*.setMessage("")*/;

            builder.setPositiveButton(R.string.close_frag_confirm, ((dialog, which) -> {
                // todo remove user data
                caller.finish();
                System.exit(0);
            }));

            builder.setNegativeButton(R.string.close_frag_deny, ((dialog, which) -> {
                dialog.cancel();
            }));
        }

        return builder.create();
    }
}
