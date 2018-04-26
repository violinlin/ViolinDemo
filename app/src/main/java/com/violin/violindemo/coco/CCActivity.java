package com.violin.violindemo.coco;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;


/**
 * Created by wanghuilin on 2018/3/30.
 * <p>
 * email:violinlin@yeah.net
 */

public class CCActivity  extends Activity {

    public static void start(Context context) {
        Intent starter = new Intent(context, CCActivity.class);
        starter.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(starter);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Workaround in https://stackoverflow.com/questions/16283079/re-launch-of-activity-on-home-button-but-only-the-first-time/16447508
        if (!isTaskRoot()) {
            // Android launched another instance of the root activity into an existing task
            //  so just quietly finish and go away, dropping the user back into the activity
            //  at the top of the stack (ie: the last state of this task)
            // Don't need to finish it again since it's finished in super.onCreate .
            return;
        }
    }

    @Override
    public void onBackPressed() {
//        super.onBackPressed();
        finish();
    }
}