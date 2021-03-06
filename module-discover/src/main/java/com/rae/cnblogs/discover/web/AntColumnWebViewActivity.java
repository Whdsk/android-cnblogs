package com.rae.cnblogs.discover.web;

import android.content.res.ColorStateList;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.qmuiteam.qmui.util.QMUIDisplayHelper;
import com.rae.cnblogs.AppRoute;
import com.rae.cnblogs.UICompat;
import com.rae.cnblogs.activity.WebActivity;
import com.rae.cnblogs.discover.R;
import com.rae.cnblogs.web.client.JavaScriptConfig;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

@Route(path = AppRoute.PATH_DISCOVER_COLUMN_WEB)
public class AntColumnWebViewActivity extends WebActivity {
    private boolean mBackIsHistory;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_ant_web;
    }

    @Override
    protected boolean canDragBack() {
        return true;
    }

    @Override
    protected int backViewInitOffset() {
        return QMUIDisplayHelper.dp2px(this, 100);
    }

    @Override
    protected int getHomeAsUpIndicator() {
        return 0;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            mShareView.setImageTintList(ColorStateList.valueOf(ContextCompat.getColor(this, R.color.black)));
        }

        EventBus.getDefault().register(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(JavaScriptConfig config) {
        mBackIsHistory = config.backIsHistory;
        UICompat.setVisibility(mShareView, config.enableShare);
    }


    @Override
    protected void doOnBackPressed() {
        if (mWebViewFragment != null && mBackIsHistory && mWebViewFragment.doOnBackPressed()) {
            return;
        }
        super.doOnBackPressed();
    }
}
