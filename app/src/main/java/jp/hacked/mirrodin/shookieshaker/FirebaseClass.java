package jp.hacked.mirrodin.shookieshaker;

/**
 * Created by Chris on 2/27/16.
 */

import android.app.Activity;
import android.os.Bundle;
import android.widget.Toast;

import com.firebase.client.Firebase;

import java.util.List;

public class FirebaseClass extends Activity {
    int LIST_SIZE = 5;
    int []htmlForPath = {};

    public void Update_DB(int value ,String name) {
        try {
            Firebase myFirebaseRef = new Firebase("https://csc425shookie.firebaseio.com");

            myFirebaseRef.child("/scoreList").child(name).child("/score").setValue(value);
            myFirebaseRef.child("/scoreList").child(name).child("/name").setValue(name);


        }
        catch(Exception e){
            System.err.println("Caught IOException: " + e.getMessage());
        }
    }

}

