package com.example.p_kontrol.UI;

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

public class MainMenuCloseFragment extends DialogFragment {
    Activity caller;

    public MainMenuCloseFragment(Activity caller) {
        this.caller = caller;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(new ContextThemeWrapper(caller, 0));

        builder.setTitle(R.string.close_frag_title)
                /*.setMessage("")*/;

        builder.setPositiveButton(R.string.close_frag_confirm, ((dialog, which) -> {
            caller.finish();
            System.exit(0);
        }));

        builder.setNegativeButton(R.string.close_frag_deny, ((dialog, which) -> {
            dialog.cancel();
        }));

        return builder.create();
    }


}
