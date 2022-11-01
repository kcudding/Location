package net.codebot.peerlearn;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.preference.PreferenceManager;
import android.text.Html;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

public class permit2 {
    private String track_PREFIX = "privacy_";
    private Activity mActivity;

    public permit2(Activity context) {
        mActivity = context;
    }

    ;

    private PackageInfo getPackageInfo() {
        PackageInfo pi = null;
        try {
            pi = mActivity.getPackageManager().getPackageInfo(mActivity.getPackageName(), PackageManager.GET_ACTIVITIES);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return pi;
    }

    public void show() {
        PackageInfo versionInfo = getPackageInfo();

        // the eulaKey changes every time you increment the version number in the AndroidManifest.xml
        final String trackKey = track_PREFIX + versionInfo.versionCode;
        final SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(mActivity);
        boolean hasBeenShown2 = prefs.getBoolean(trackKey, false);
        if (hasBeenShown2 == false) {

            // Show the Eula
            String title = mActivity.getString(R.string.app_name) + " v" + versionInfo.versionName;

            //Includes the updates as well so users know what changed.
            //String message = mActivity.getString(R.string.info);
            String message = mActivity.getString(R.string.privacy);

            final AlertDialog alertDialog = new AlertDialog.Builder(mActivity)
                    .setTitle(title)
                    .setMessage(Html.fromHtml(message))
                    .setPositiveButton("Information", null)
                    .setNegativeButton("CANCEL", null)
                    .setNeutralButton("Agree", null)
                    .show();

            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT); //create a new one
            layoutParams.weight = 10;
           // layoutParams.gravity = Gravity.LEFT; //this is layout_gravity


            Button positiveButton = alertDialog.getButton(AlertDialog.BUTTON_POSITIVE);
            positiveButton.setOnClickListener(v -> {

                String url = "https://multi-plier.ca/PeerLearn.html";

                Intent gto = new Intent(Intent.ACTION_VIEW);
                gto.setData(Uri.parse(url));

                mActivity.startActivity(gto);
            });

            Button neutralButton = alertDialog.getButton(AlertDialog.BUTTON_NEUTRAL);
            neutralButton.setOnClickListener(v -> {
            SharedPreferences.Editor editor = prefs.edit();
            editor.putBoolean(trackKey, true);
            editor.commit();
                alertDialog.dismiss();
            });
            Button negativeButton = alertDialog.getButton(AlertDialog.BUTTON_NEGATIVE);
            negativeButton.setOnClickListener(v -> {


                mActivity.finish();
            });

            layoutParams.gravity = Gravity.RIGHT;
            alertDialog.getButton(AlertDialog.BUTTON_NEGATIVE).setLayoutParams(layoutParams);
            layoutParams.gravity = Gravity.LEFT;
            alertDialog.getButton(AlertDialog.BUTTON_NEUTRAL).setLayoutParams(layoutParams);
            layoutParams.gravity = Gravity.CENTER;
            alertDialog.getButton(AlertDialog.BUTTON_POSITIVE).setLayoutParams(layoutParams);
        }
    }
}