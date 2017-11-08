package com.powerbee.gprs.bizz.request;

import android.app.Activity;
import android.app.Application;
import android.app.Dialog;
import android.text.TextUtils;
import android.widget.Toast;

import com.alibaba.fastjson.JSONObject;
import com.powerbee.ALogin;
import com.powerbee.R;
import com.powerbee.gprs.bizz.DataPool;
import com.powerbee.util.RespHelper;
import com.sdk.powerbee.utils.Log4Android;

import java.net.SocketTimeoutException;

import hx.widget.dialog.DWaiting;
import rx.Observable;
import rx.Scheduler;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by Rose on 3/2/2017.
 *
 * 通过是否传入activity判定，是否需要显示请求等待的dialog
 *
 */

class Request {

    private Dialog mDialog;
    private Application mApp;
    protected Observable<JSONObject> mLoginObservable;
    private Observable<JSONObject> mLastObservable;

    public Request(Application app){
        this.mApp = app;
    }

    protected Observable<JSONObject> _getJson(Observable<JSONObject> observable, Activity act){
        if(act != null) mDialog = DWaiting.show(act);
        mLastObservable = observable;
        Scheduler scheduler = Schedulers.newThread();
        return observable
                .subscribeOn(scheduler)
                .observeOn(AndroidSchedulers.mainThread())
                .onErrorReturn(throwable -> {
                    if (throwable instanceof SocketTimeoutException) Toast.makeText(mApp, R.string.socket_timeout, Toast.LENGTH_SHORT).show();
                    throwable.printStackTrace();
                    return null;
                })
                .flatMap(json -> {
                    if (isVerificationFailure(json)) {
                        return mLoginObservable
                                .subscribeOn(scheduler)
                                .takeWhile(this::isSuccess)
                                .map(jsonObject -> {
                                    DataPool.refreshToken(RespHelper.getToken(jsonObject));
                                    return jsonObject;
                                })
                                .flatMap(jsonObject -> mLastObservable)
                                .subscribeOn(scheduler);
                    } else return Observable.just(json);
                })
                .observeOn(AndroidSchedulers.mainThread())
                .doOnTerminate(() -> dismissDialog(act))
                .takeWhile(this::isSuccess);

    }

    protected Observable<JSONObject> _getOriginalJson(Observable<JSONObject> observable) {
        mLastObservable = observable;
        return observable
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .onErrorReturn(throwable -> {
                    if (throwable instanceof SocketTimeoutException) Toast.makeText(mApp, R.string.socket_timeout, Toast.LENGTH_SHORT).show();
                    throwable.printStackTrace();
                    return null;
                });
    }

    private boolean isVerificationFailure(JSONObject json){
        return json != null && json.getIntValue(Resp.Code) == Resp.VerificationFailure;
        /*if(code == Resp.VerificationFailure){
            Toast.makeText(mApp, R.string.login_expire, Toast.LENGTH_SHORT).init();
            ALogin.start(mApp.getApplicationContext());
            return false;
        }*/
//        Toast.makeText(mApp, "Verificatioin .....", Toast.LENGTH_SHORT).init();
//        return true;
    }

    private boolean isSuccess(JSONObject json){
        if(json != null && json.getIntValue(Resp.Code) == Resp.Success){
            DataPool.refreshToken();
            return true;
        }
        String msg = json == null ? null : json.getString(Resp.Message);
        if(!TextUtils.isEmpty(msg)){
            Toast.makeText(mApp, msg, Toast.LENGTH_SHORT).show();
        }
        return false;
    }
    private void dismissDialog(Activity act){
        if(act != null && mDialog != null){ mDialog.dismiss(); }
    }

}
