package jp.hacked.mirrodin.shookieshaker;

import android.animation.Animator;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.client.Firebase;


public class PlayActivity extends Activity {

    private TextView shakeTextView;
    private TextView animateTextView;
    private ShakeHandler shakeHandler = new ShakeHandler();
    // The following are used for the shake detection and handling
    private SensorManager mSensorManager;
    private Sensor mAccelerometer;
    private ShakeDetector mShakeDetector;
    private int score = 0;
    private String name="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // run the app in full screen
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_play);

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
                animateTextView.animate().cancel();
                animateTextView.animate().translationYBy(-50).setDuration(500).setListener(new Animator.AnimatorListener() {

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

        // Back button and call home screen.
        Button backButton= (Button) findViewById(R.id.backButton);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PlayActivity.this, MainActivity.class);
                startActivity(intent);
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

    public void alert(View view){
        final EditText userName= new EditText(this);
        System.out.println("SCORE: " + score);
        //System.out.format("SCORE: %d",score);
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Enter Your Name?");
        builder.setView(userName);
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        //System.out.format("SCORE: %d", score);
                        FirebaseClass DB_Update = new FirebaseClass();
                        try {
                            name= userName.getText().toString();
                            DB_Update.Update_DB(score, name);
                            Toast.makeText(getApplicationContext(), "High Score Successfully added",
                                    Toast.LENGTH_LONG).show();
                        } catch (Exception e) {

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
