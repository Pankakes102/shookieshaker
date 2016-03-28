package jp.hacked.mirrodin.shookieshaker;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.content.Intent;
import android.widget.Button;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // run the app in full screen
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_main);

        // Intent that call PlayActivity.class
        Button playBut= (Button) findViewById(R.id.playButton);
        playBut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, PlayActivity.class);
                startActivity(intent);
            }
        });

        // Intent that call PlayActivity.class
        Button leaderboardBut= (Button) findViewById(R.id.leaderboardButton);
        leaderboardBut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, LeaderboardActivity.class);
                startActivity(intent);
            }
        });

    }


<<<<<<< Updated upstream
=======
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
>>>>>>> Stashed changes
}
