package com.hdw.hdutils.ui.fragment;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hdw.hdutils.api.RequestParams;
import com.hdw.hdutils.util.NetUtils;
import com.hdw.hdutils.util.ToastUtil;

import java.text.SimpleDateFormat;

/**
 * Created by yezihdw on 2017/6/19.
 */

public abstract class BaseFragment extends Fragment
        implements View.OnClickListener{

    private static final String STATE_SAVE_IS_HIDDEN = "STATE_SAVE_IS_HIDDEN";

    public Activity activity;
    private ViewGroup rootView;
    protected SimpleDateFormat dateFormat;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (savedInstanceState != null) {
            boolean isSupportHidden = savedInstanceState.getBoolean(STATE_SAVE_IS_HIDDEN);

            FragmentTransaction ft = getFragmentManager().beginTransaction();
            if (isSupportHidden) {
                ft.hide(this);
            } else {
                ft.show(this);
            }
            ft.commit();
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBoolean(STATE_SAVE_IS_HIDDEN, isHidden());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = (ViewGroup) inflater.inflate(getContentVew(), null);
        return rootView;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initViews(rootView);
        dateFormat = new SimpleDateFormat("yyyy年MM月dd日 HH:mm:ss");
        setData();
    }

    /**
     * 返回fragment的layout
     */
    protected abstract int getContentVew();

    /**
     * 初始化控件
     */
    protected abstract void initViews(View view);

    /**
     * 初始化数据
     */
    protected abstract void setData();

    /**
     * 网络请求错误回调
     */
    protected abstract void onFailure(int requestCode, String errorMsg);

    /**
     * 网络请求成功回调
     */
    protected abstract void onSuccess(int requestCode, String response);

    /**
     * 不带参数的Get请求，不设置requestCode的请求
     */
    protected void doGet(String url) {
        doGet(-1, url);
    }

    /**
     * 不带参数的Get请求，不设置requestCode的请求
     */
    protected void doGet(String url, RequestParams params) {
        doGet(-1, url, params);
    }

    /**
     * 不带参数带请求code的Get请求
     */
    protected void doGet(int requestCode, String url) {
        doGet(requestCode, url, null);
    }

    /**
     * 带参数带请求code的Get请求
     */
    protected void doGet(int requestCode, String url, RequestParams params) {
        if (TextUtils.isEmpty(url)) {
            throw new RuntimeException("host或请求的url为空！");
        }

        if(NetUtils.isNetworkConnected(activity)||NetUtils.isWifiConnected(activity)){
//            RequestManager.get(requestCode, url, params, this);
        }else {
            ToastUtil.show("没有网络，请检查网络！");
        }
    }

    /**
     * 带参数不带请求code的Post请求
     */
    protected void doPost(String url, RequestParams params) {
        doPost(-1, url, params);
    }

    /**
     * 带参数带请求code的Post请求
     */
    protected void doPost(int requestCode, String url, RequestParams params) {
        if (TextUtils.isEmpty(url)) {
            throw new RuntimeException("host或请求的url为空！");
        }
        if(NetUtils.isNetworkConnected(activity)||NetUtils.isWifiConnected(activity)){
//            RequestManager.post(requestCode, url, params, this);
        }else {
            ToastUtil.show("没有网络，请检查网络！");
        }
    }

    /**
     * 带参数带请求code的Post请求
     */
    protected void saveUserInfo(int requestCode, String url, RequestParams params) {
        if (TextUtils.isEmpty(url)) {
            throw new RuntimeException("host或请求的url为空！");
        }
        if(NetUtils.isNetworkConnected(activity)||NetUtils.isWifiConnected(activity)){
//            RequestManager.post(requestCode, url, params, this);
        }else {
            ToastUtil.show("没有网络，请检查网络！");
        }
    }


    /**
     * 带参数带请求code的DELETE请求
     */
    protected void doDelete(int requestCode, String url, RequestParams params) {
        if (TextUtils.isEmpty(url)) {
            throw new RuntimeException("host或请求的url为空！");
        }

        if(NetUtils.isNetworkConnected(activity)||NetUtils.isWifiConnected(activity)){
//            RequestManager.delete(requestCode, url, params, this);
        }else {
            ToastUtil.show("没有网络，请检查网络！");
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.activity = (Activity) context;
    }

}
