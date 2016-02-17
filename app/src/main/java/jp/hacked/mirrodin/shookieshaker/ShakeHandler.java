package jp.hacked.mirrodin.shookieshaker;

import android.widget.TextView;

/**
 * Created by ANDY on 2/17/2016.
 */
public class ShakeHandler {

    private int shakeCount = 0;
    //private TextView shakeTextView = (TextView) findViewById(R.id.shakeTextView);

    public String handleShakeEvent(String count) {
        // add what to do with each shake event here
        return Integer.toString(shakeCount += Integer.valueOf(count));

    }
}
