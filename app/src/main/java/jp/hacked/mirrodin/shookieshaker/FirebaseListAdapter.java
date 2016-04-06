package jp.hacked.mirrodin.shookieshaker;

import android.app.Activity;
import android.util.Log;

import com.firebase.client.ChildEventListener;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.Query;

import java.util.List;
import java.util.ArrayList;

/**
 * Created by ANDY on 4/4/2016.
 */
public class FirebaseListAdapter extends Activity {

    public List<String> names;
    public List<String> scores;

    public void getScores() {
        Firebase ref = new Firebase("https://csc425shookie.firebaseio.com/scoreList");
        Query queryRef = ref.orderByValue().limitToLast(10); // Sort by ascending value, show 10

        names = new ArrayList<>();
        scores = new ArrayList<>();

        queryRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot snapshot, String previousChildKey) {

                String key = snapshot.getKey();
                long value = (long) snapshot.getValue();

                if (previousChildKey == null) {
                    names.add(0, key);
                    scores.add(0, Long.toString(value));
                } else {
                    int previousIndex = scores.indexOf(previousChildKey);
                    int nextIndex = previousIndex + 1;
                    if (nextIndex == names.size()) {
                        names.add(key);
                        scores.add(Long.toString(value));
                    } else {
                        names.add(nextIndex, key);
                        scores.add(nextIndex, Long.toString(value));
                    }
                }

                //System.out.println("Name: " + snapshot.getKey());
                //System.out.println("Score: " + snapshot.getValue());
                //System.out.println("Name: " + key);
                //System.out.println("Score: " + value);
            }

            @Override
            public void onChildChanged(DataSnapshot snapshot, String previousChildKey) {
                System.out.println("The updated name is " + snapshot.getKey());
            }

            @Override
            public void onChildMoved(DataSnapshot snapshot, String previousChildKey) {
            }

            @Override
            public void onChildRemoved(DataSnapshot snapshot) {
                System.out.println("Name: " + snapshot.getKey() + " has been deleted");
            }

            @Override
            public void onCancelled(FirebaseError error) {
                Log.e("FirebaseListAdapter", "Listen was cancelled, no more updates will occur");
            }
        });
    }
}
