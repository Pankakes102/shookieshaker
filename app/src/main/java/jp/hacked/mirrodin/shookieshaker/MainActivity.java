package jp.hacked.mirrodin.shookieshaker;


import android.animation.Animator;
import android.animation.Animator.AnimatorListener;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.client.Firebase;


public class MainActivity extends AppCompatActivity {

    private TextView shakeTextView;
    private TextView animateTextView;
    private ShakeHandler shakeHandler = new ShakeHandler();
    // The following are used for the shake detection and handling
    private SensorManager mSensorManager;
    private Sensor mAccelerometer;
    private ShakeDetector mShakeDetector;
    private int score = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        //setSupportActionBar(toolbar);

        //initialize firebase library
        Firebase.setAndroidContext(this);

        //FirebaseClass DB_Update = new FirebaseClass();
        //DB_Update.Update_DB(5);

        shakeTextView = (TextView) findViewById(R.id.shakeTextView);
        animateTextView = (TextView) findViewById(R.id.animateTextView);
        animateTextView.setTextSize(20);
        shakeTextView.setTextSize(40);
        shakeTextView.setText(shakeHandler.handleShakeEvent(Integer.toString(score))); // put a 0 on the screen if no score
        animateTextView.setVisibility(View.INVISIBLE); // hide until first shake

        // ShakeDetector initialization
        mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        mAccelerometer = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        mShakeDetector = new ShakeDetector();
        mShakeDetector.setOnShakeListener(new ShakeDetector.OnShakeListener() {

            @Override
            public void onShake(int count) {
                score = score + count;
                shakeTextView.setText(shakeHandler.handleShakeEvent(Integer.toString(count)));
                animateTextView.animate().translationYBy(-50).setDuration(500).setListener(new AnimatorListener() {

                    @Override
                    public void onAnimationStart(Animator animation) {
                        // gets called on every shake
                        animateTextView.clearAnimation();
                        animateTextView.setTranslationY(50);
                        animateTextView.setVisibility(View.VISIBLE);

                    }

                    @Override
                    public void onAnimationRepeat(Animator animation) {
                        // never gets called

                    }

                    @Override
                    public void onAnimationEnd(Animator animation) {
                        // after animation set visibility gone (removes from layout)
                        // only is called once the animation is done (no more shake events)
                        animateTextView.setTranslationY(50);
                        animateTextView.setVisibility(View.GONE);
                    }

                    @Override
                    public void onAnimationCancel(Animator animation) {
                        // no idea if this ever gets called
                        animateTextView.setVisibility(View.GONE);
                    }
                });
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        // register the Session Manager Listener onResume
        mSensorManager.registerListener(mShakeDetector, mAccelerometer,	SensorManager.SENSOR_DELAY_UI);
    }

    @Override
    public void onPause() {
        // unregister the Sensor Manager onPause
        mSensorManager.unregisterListener(mShakeDetector);
        super.onPause();
    }

    /*@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }*/

    public void alert(View view){
        System.out.println("SCORE: " + score);
        //System.out.format("SCORE: %d",score);
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Add to high score list?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        //System.out.format("SCORE: %d", score);
                        FirebaseClass DB_Update = new FirebaseClass();
                        try {
                            DB_Update.Update_DB(score);
                            Toast.makeText(getApplicationContext(), "High Score Successfully added",
                                    Toast.LENGTH_LONG).show();
                        }
                        catch(Exception e){

                            Toast.makeText(getApplicationContext(), "Could not add high score to database",
                                    Toast.LENGTH_LONG).show();

                        }



                       // DB_Update.Update_DB(score);
                        dialog.cancel();
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });
        AlertDialog alert = builder.create();
        alert.show();
    }
}

