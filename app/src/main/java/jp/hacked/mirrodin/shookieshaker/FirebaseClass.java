package jp.hacked.mirrodin.shookieshaker;

/**
 * Created by Chris on 2/27/16.
 */

import android.app.Activity;
import android.os.Bundle;
import android.widget.Toast;

import com.firebase.client.Firebase;
public class FirebaseClass extends Activity {

    public void Update_DB(int value) {
        try {
            Firebase myFirebaseRef = new Firebase("https://csc425shookie.firebaseio.com");
            myFirebaseRef.child("HigscoreList/Value1").setValue(value);
        }
        catch(Exception e){
            System.err.println("Caught IOException: " + e.getMessage());
        }
    }

}

