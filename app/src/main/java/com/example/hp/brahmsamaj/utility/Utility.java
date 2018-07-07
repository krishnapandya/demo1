package com.example.hp.brahmsamaj.utility;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.text.format.DateUtils;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.hp.brahmsamaj.R;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;

/**
 * Created by yugtia-pm on 3/14/2018.
 */

public class Utility {

    public static void showToast(Context context, String text) {
        Toast.makeText(context, text, Toast.LENGTH_SHORT).show();
    }

    public static boolean isConnectivityAvailable(Context context) {
        NetworkInfo activeNetwork = null;
        try {
            ConnectivityManager connectivityManager = (ConnectivityManager)
                    context.getSystemService(Context.CONNECTIVITY_SERVICE);

            activeNetwork = connectivityManager.getActiveNetworkInfo();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return activeNetwork != null && activeNetwork.isConnectedOrConnecting();
    }

    public static void showToast(Activity activity, String msg) {
        Toast.makeText(activity, msg, Toast.LENGTH_SHORT).show();
    }

    public static void redirectToActivity(Activity yourActivity, Class SecondActivity, boolean isFinish, String bundleKey, Bundle b, int flags, Intent putExtras) {
        Intent intent = new Intent(yourActivity, SecondActivity);
        if (putExtras != null) {
            intent.putExtras(putExtras);
        }
        if (b != null) {
            intent.putExtra(bundleKey, b);
        }
        if (flags != 0) {
            intent.addFlags(flags);
        }
        yourActivity.startActivity(intent);
        yourActivity.overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
        if (isFinish) {
            yourActivity.finish();
        }
    }

    public static void redirectToActivityForResult(Activity yourActivity, Class SecondActivity, String bundleKey, Bundle b, int flags, Intent putExtras, int activityRequestCode) {
        Intent intent = new Intent(yourActivity, SecondActivity);
        if (putExtras != null) {
            intent.putExtras(putExtras);
        }
        if (b != null) {
            intent.putExtra(bundleKey, b);
        }
        if (flags != 0) {
            intent.addFlags(flags);
        }
        yourActivity.startActivityForResult(intent, activityRequestCode);
        yourActivity.overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
    }

    public static void HideKeyboard(Context ctx, View view) {
        InputMethodManager imm = (InputMethodManager) ctx.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    public static void loadImageFromPicaso(String imageUrl, ImageView iv, Context ctx) {
        Picasso.with(ctx).load(imageUrl).placeholder(R.mipmap.ic_launcher_round).error(R.mipmap.ic_launcher_round).into(iv);
    }

    public static void loadImageFromPicasoProfile(String imageUrl, ImageView iv, Context ctx) {
        Picasso.with(ctx).load(imageUrl).placeholder(R.mipmap.ic_launcher).error(R.mipmap.ic_launcher).into(iv);
    }

    public static String convertTimestampToDate(long timestamp) {
        Timestamp tStamp = new Timestamp(timestamp);
        SimpleDateFormat simpleDateFormat;
        if (DateUtils.isToday(timestamp)) {
            simpleDateFormat = new SimpleDateFormat("hh:mm a");
            return "Today " + simpleDateFormat.format(tStamp);
        } else {
            simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy hh:mm a");
            return simpleDateFormat.format(tStamp);
        }
    }

    public static long correctTimestamp(long timestampInMessage) {
        long correctedTimestamp = timestampInMessage;

        if (String.valueOf(timestampInMessage).length() < 13) {
            int difference = 13 - String.valueOf(timestampInMessage).length(), i;
            String differenceValue = "1";
            for (i = 0; i < difference; i++) {
                differenceValue += "0";
            }
            correctedTimestamp = (timestampInMessage * Integer.parseInt(differenceValue))
                    + (System.currentTimeMillis() % (Integer.parseInt(differenceValue)));
        }
        return correctedTimestamp;
    }

    public static void createDirectory(String filePath) {
        if (!new File(filePath).exists()) {
            new File(filePath).mkdirs();
        }
    }

    public static String generateHashKey(Context context, String packageName) {
        String hashKey = "";
        try {
            PackageInfo info = context.getPackageManager().getPackageInfo(packageName, PackageManager.GET_SIGNATURES);
            for (Signature signature : info.signatures) {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                hashKey = Base64.encodeToString(md.digest(), Base64.DEFAULT);
                Log.d("KeyHash:", Base64.encodeToString(md.digest(), Base64.DEFAULT));
            }
        } catch (PackageManager.NameNotFoundException e) {

        } catch (NoSuchAlgorithmException e) {

        }
        return hashKey;
    }

    public static ProgressDialog getProgressDialog(Context context, String dialogMessage, boolean isCancelable) {

        final ProgressDialog progressDialog = new ProgressDialog(context);
        progressDialog.setCancelable(isCancelable);
        progressDialog.setMessage(dialogMessage);
        return progressDialog;
    }
}
