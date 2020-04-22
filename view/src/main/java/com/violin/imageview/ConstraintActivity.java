package com.violin.imageview;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

public class ConstraintActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_constraint);
    }

    public static void start(Context context) {
        Intent starter = new Intent(context, ConstraintActivity.class);
        context.startActivity(starter);
    }
}
