package com.violin.violindemo;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;

import com.violin.recyclerview.RecyclerFragment;
import com.violin.util.SubActivity;

/**
 * Created by wanghuilin on 2018/5/23.
 * <p>
 * email:wanghuilin@zshiliu.com
 */

public class JumpUtils {
    /**
     * 二级页面Intent
     */
    private static Intent getSubIntent(Context context, String title, Class<? extends Fragment> cls) {
        Intent intent = new Intent(context, SubActivity.class);
        intent.putExtra(SubActivity.EXTRA_FRAGMENT, cls.getName());
        intent.putExtra(SubActivity.EXTRA_TITLE, title);
        return intent;
    }

    /**
     * 二级页面Intent
     */
    private static Intent getSubIntent(Context context, Class<? extends Fragment> cls) {
        return getSubIntent(context, "", cls);
    }

    /**
     * recyclerview 界面
     *
     * @param context
     */
    public static void recyclerView(Context context) {
        Intent intent = getSubIntent(context, "RecyclerView", RecyclerFragment.class);
        context.startActivity(intent);
    }

}
