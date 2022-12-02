package net.codebot.peerlearn;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.provider.Settings;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import androidx.annotation.RequiresApi;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.work.OneTimeWorkRequest;
import androidx.work.WorkManager;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.animation.ArgbEvaluator;
import android.animation.ObjectAnimator;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;

import java.time.LocalDate;

@RequiresApi(api = Build.VERSION_CODES.Q)
public class MainActivity extends Activity {
    // used for permission checks
    private static final int REQUEST_CODE_PERMISSION = 2;
    //String mPermission = Manifest.permission.ACCESS_FINE_LOCATION;
    String[] mPermissions = new String[]{
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.ACCESS_BACKGROUND_LOCATION
    };

    // tag for debugging
    public static final String TAG = "GPSTRACKER";

    private WebView webView1;
    LocalDate endtrial = LocalDate.parse("2022-12-08");
    LocalDate starttrial = LocalDate.parse("2022-11-06");
    LocalDate today = LocalDate.now();
    Boolean containsToday = ( ! today.isBefore( starttrial ) ) && ( today.isBefore( endtrial ) ) ;

    // data to fetch from the location service

    @SuppressLint("HardwareIds")
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final String id = Settings.Secure.getString(getApplicationContext().getContentResolver(),
                Settings.Secure.ANDROID_ID);

        new permit2(this).show();
        new TermsConditions(this).show();
        new SimpleEula(this).show();

        // make scrollable
        final TextView text = (TextView) findViewById(R.id.editText);

        text.setMovementMethod(new ScrollingMovementMethod());
        text.setText(R.string.prestatus);
       // adding the color to be shown
        ObjectAnimator animator = ObjectAnimator.ofInt(text, "TextColor",
                Color.RED);
// duration of one color
        animator.setDuration(500);
        animator.setEvaluator(new ArgbEvaluator());
// color will be show in reverse manner
        animator.setRepeatCount(Animation.REVERSE);
// It will be repeated up to infinite time
        animator.setRepeatCount(Animation.INFINITE);
        animator.start();

        // check date range

        // make scrollable
        final TextView textinfo = (TextView) findViewById(R.id.textView);
        textinfo.setMovementMethod(new ScrollingMovementMethod());
       // text.setText(R.string.prestatus);
        // check date range


        if(!containsToday ){
            text.setTextColor(Color.parseColor("#D81B60"));
            text.setText(R.string.statusendtrial);


                new CountDownTimer(15000, 1000) {

                    public void onTick(long millisUntilFinished) {

                    }

                    public void onFinish() {
                        finishAffinity();
                        System.exit(0);
                    }
                }.start();


            }

       // final Button proj = (Button) findViewById(R.id.proj);
        //proj.setOnClickListener(new View.OnClickListener() {

        Button imageLogo = (Button)findViewById(R.id.proj);
        imageLogo.setOnClickListener(new View.OnClickListener() {

            @Override
           public void onClick(View v) {
                // TODO Auto-generated method stub
               String url = "https://multi-plier.ca/PeerLearn.html";

               Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(url));
                startActivity(i);
            }
        });
        Button imageLogo2 = (Button)findViewById(R.id.user);
        imageLogo2.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                String url = "https://multi-plier.ca/EULA.html";

                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(url));
                startActivity(i);
            }
        });

        Button priv = (Button)findViewById(R.id.priv);
        priv.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                String url = "https://multi-plier.ca/privacy.html";

                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(url));
                startActivity(i);
            }
        });


        final Button enableButton = (Button) findViewById(R.id.app_settings);
        enableButton.setOnClickListener(event -> {
            Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
                    Uri.fromParts("package", getPackageName(), null));
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        });

        // exit program
        final Button stop = (Button) findViewById(R.id.stop);
        stop.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("SetTextI18n")

             @Override
             public void onClick(View v) {
                finishAffinity();
                System.exit(0);
             }

        });

        // start tracking handler
        final Button start = (Button) findViewById(R.id.start);
        start.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onClick(View arg0) {
                // create a work request to fetch location data
                // first request runs immediately and schedules the next one
                boolean perms = true;
                // check permissions
                try {

                    for (String s : mPermissions) {
                        perms = perms && (ActivityCompat.checkSelfPermission(MainActivity.this, s) == PackageManager.PERMISSION_GRANTED);
                        if (!perms) {
                            ActivityCompat.requestPermissions(MainActivity.this, mPermissions, REQUEST_CODE_PERMISSION);
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
               // boolean perms2=perms;
               // if (!perms2) {
                //    ActivityCompat.requestPermissions(MainActivity.this, mPermissions, REQUEST_CODE_PERMISSION);
                 //   for (String s : mPermissions) {
                  //      perms2 = perms && (ActivityCompat.checkSelfPermission(MainActivity.this, s) == PackageManager.PERMISSION_GRANTED);
                   // }
               // }
                    //    if (!perms) {
                   //         text.setTextColor(Color.parseColor("#D81B60"));
                     //       text.setText(R.string.deniedstatus);


                       //     new CountDownTimer(12000, 1000) {
//
  //                              public void onTick(long millisUntilFinished) {
//
  //                              }

    //                            public void onFinish() {
      //                              finishAffinity();
        //                            System.exit(0);
          //                      }
            //                }.start();


                    //    }
                    if (perms==true) {
                        // get data from tracking service
                        animator.cancel();
                        animator.end();
                        animator.removeAllListeners();
                        start.setTextColor(Color.LTGRAY);
                        GPSTracker.Builder().setContext(MainActivity.this.getApplicationContext());

                        OneTimeWorkRequest locationRequest = new OneTimeWorkRequest.Builder(LocationWorker.class).build();
                        WorkManager.getInstance(getApplicationContext()).enqueue(locationRequest);
                        text.setTextColor(Color.parseColor("#D81B60"));
                       // text.setText(R.string.status+);
                        text.setText("Tracking\n"+id);



                    }

            }
        });




    }




}
