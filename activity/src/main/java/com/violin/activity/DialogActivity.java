package com.violin.activity;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

import java.util.ArrayList;
import java.util.List;

public class DialogActivity extends AppCompatActivity {

    private String mName;
    private List<String> mList;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dialog);

        Window window = getWindow();
        WindowManager.LayoutParams attributes = window.getAttributes();
        attributes.width = (int) (getResources().getDisplayMetrics().widthPixels * 0.8);

        window.setAttributes(attributes);

    }

    public static void start(Context context) {
        Intent starter = new Intent(context, DialogActivity.class);
        context.startActivity(starter);
    }
}
