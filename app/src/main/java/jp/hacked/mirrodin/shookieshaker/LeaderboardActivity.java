package jp.hacked.mirrodin.shookieshaker;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import com.firebase.client.Firebase;

public class LeaderboardActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Firebase.setAndroidContext(this);

        // run the app in full screen
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_leaderboard);
        //setContentView(R.layout.content_leaderboard);

        // Populate leaderboard
        FirebaseListAdapter get_Scores = new FirebaseListAdapter();
        get_Scores.getScores();

        ListView lv1 = (ListView) findViewById(R.id.listView);
        ListView lv2 = (ListView) findViewById(R.id.listView2);

        ArrayAdapter<String> arrayAdapter1 = new ArrayAdapter<>(
                this,
                R.layout.custom_textview,
                get_Scores.names);
        ArrayAdapter<String> arrayAdapter2 = new ArrayAdapter<>(
                this,
                R.layout.custom_textview,
                get_Scores.scores);

        lv1.setAdapter(arrayAdapter1);
        lv2.setAdapter(arrayAdapter2);

        // Back button and call home screen.
        Button backButton= (Button) findViewById(R.id.backButton);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LeaderboardActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });

        // Intent that call PlayActivity.class
        Button playBut= (Button) findViewById(R.id.newGameButton);
        playBut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(LeaderboardActivity.this, PlayActivity.class);
                startActivity(intent);
            }
        });


    }
}
