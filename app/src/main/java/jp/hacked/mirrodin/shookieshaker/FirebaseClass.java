package jp.hacked.mirrodin.shookieshaker;

/**
 * Created by Chris on 2/27/16.
 */

import android.app.Activity;

import com.firebase.client.Firebase;

public class FirebaseClass extends Activity {

    public void Update_DB(int value ,String name) {
        try {
            Firebase myFirebaseRef = new Firebase("https://csc425shookie.firebaseio.com");

            myFirebaseRef.child("/scoreList").child(name).setValue(value);

        }
        catch(Exception e){
            System.err.println("Caught IOException: " + e.getMessage());
        }
    }
}

