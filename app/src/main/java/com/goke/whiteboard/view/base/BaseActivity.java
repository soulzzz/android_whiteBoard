package com.goke.whiteboard.view.base;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentActivity;


import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.ButterKnife;

/**
 * Created by gpy on 2016/2/17.
 */
public abstract class BaseActivity extends FragmentActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(this.getLayoutId());
        ButterKnife.inject(this);
        this.afterCreate(savedInstanceState);
        EventBus.getDefault().register(this);
    }

    protected abstract int getLayoutId();

    protected abstract void afterCreate(Bundle savedInstanceState);

    protected void showMessage(CharSequence msg) {
        Toast.makeText(this,msg,Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onResume() {
        super.onResume();

    }

    @Override
    protected void onPause() {
        super.onPause();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    public void navi2Page(final Class<?> page) {
        this.navi2Page(page, false);
    }

    public void navi2Page(final Class<?> page, final boolean shut) {
        this.navi2Page(page, null, shut);
    }

    public void navi2Page(final Class<?> page, final Bundle data, final boolean shut) {
        final Intent intent = new Intent(this, page);
        if (null != data) {
            intent.putExtras(data);
        }
        this.startActivity(intent);
        if (shut) {
            this.finish();
        }
    }
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessage(String event){
    }

}
