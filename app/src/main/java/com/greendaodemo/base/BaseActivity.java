package com.greendaodemo.base;

import android.content.Context;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

/**
 * Created by jsion on 16/1/14.
 */
public abstract class BaseActivity extends AppCompatActivity implements View.OnClickListener {
    protected Context mContext;

    @Override
    public void onCreate(Bundle savedInstanceState, PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
        mContext = this;

    }

    /**
     * 初始化数据
     */
    protected abstract void initData();

    /**
     * 初始化控件
     */
    protected abstract void initView();

    /**
     * 初始化事件
     */
    protected abstract void initEvent();


}
