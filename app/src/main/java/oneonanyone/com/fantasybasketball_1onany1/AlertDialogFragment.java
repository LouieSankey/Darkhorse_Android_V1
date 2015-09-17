package oneonanyone.com.fantasybasketball_1onany1;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;

/**
 * Created by lsankey on 7/24/15.
 */
public class AlertDialogFragment extends DialogFragment {

    public static AlertDialogFragment newInstance() {
        AlertDialogFragment f = new AlertDialogFragment();
        return f;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
       Context context = getActivity();
        AlertDialog.Builder builder = new AlertDialog.Builder(context)
                .setTitle("Draft Player")
                .setMessage("Are you sure you would like to draft this player?")
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // continue with delete
                    }
                })
                .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // do nothing
                    }
                });

        AlertDialog dialog = builder.create();
        return dialog;
    }
}
