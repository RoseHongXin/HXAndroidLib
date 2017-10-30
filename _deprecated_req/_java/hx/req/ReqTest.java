package hx.req;

import android.app.Activity;
import android.content.Context;

import hx.req.bean.RespBase;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Rose on 11/15/2016.
 */
public class ReqTest extends RetrofitBase {

    final String TAG = "--ReqTest--";

    public static final int PAGE_SIZE = 20;

    String _PAGE = "page";
    String _PAGESIZE = "pageSize";

    Context mCtx;
    volatile static String token = null;
    DReq dReq = null;


    public ReqTest(Context ctx){
        this.mCtx = ctx;
    }


    @Override
    protected <T> Observable<T> request(Observable<RespBase<T>> observable, Activity act, String note, IRespCallback cb) {
        if(act == null){
            if(dReq != null && dReq.isShowing()) dReq.dismiss();
            dReq = null;
        }
        else dReq = DReq.show(act, note);
        return observable
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .onErrorReturn(throwable -> {
                    return null;
                })
                .doOnError(throwable -> {
                })
                .doOnCompleted(() -> {
                    if(act != null && dReq != null) dReq.dismiss();
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
