package hx.req;

import android.app.Activity;
import android.content.Context;

import com.orhanobut.dialogplus.DialogPlus;

import hx.req.bean.RespBase;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Rose on 11/15/2016.
 */
public class ReqTest2 extends RetrofitBase2 {

    final String TAG = "--ReqTest--";

    public static final int PAGE_SIZE = 20;

    String _PAGE = "page";
    String _PAGESIZE = "pageSize";

    Context mCtx;
    volatile static String token = null;


    public ReqTest2(Context ctx){
        this.mCtx = ctx;
    }


    @Override
    protected <T> Observable<T> request(Observable<RespBase<T>> observable, DialogPlus dialog, IRespCallback cb) {
        if(dialog != null) dialog.show();
        return observable
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .onErrorReturn(throwable -> {
                    return null;
                })
                .doOnError(throwable -> {
                })
                .doOnCompleted(() -> {
                    if(dialog != null) dialog.dismiss();
                })
                .takeWhile(res -> {
                    return true;
                })
                .flatMap(res -> {
                    if(cb != null) cb.onResp(res.getCode());
                    T data = res.getData();
                    if(data == null) res.wrapMsgIfDataNull();
                    return Observable.just(data);
                });
    }
}
