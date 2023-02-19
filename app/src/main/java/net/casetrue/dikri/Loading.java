package net.casetrue.dikri;

import android.app.Activity;
import android.app.AlertDialog;
import android.view.LayoutInflater;

public class Loading {
    private Activity activity;
    private AlertDialog alertDialog;

    Loading(Activity myActivity) {
        activity = myActivity;
    }

    void startLoading() {
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);

        LayoutInflater layoutInflater = activity.getLayoutInflater();
        builder.setView(layoutInflater.inflate(R.layout.loading, null));
        builder.setCancelable(false );
        alertDialog = builder.create();
        alertDialog.show();
    }

    void dismissLoading() {
        alertDialog.dismiss();
    }
}
