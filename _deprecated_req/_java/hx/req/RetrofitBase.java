package hx.req;

import android.app.Activity;
import hx.req.bean.RespBase;
import rx.Observable;

/**
 * Created by Rose on 11/14/2016.
 */

@Deprecated
public abstract class RetrofitBase {

    protected <T> Observable<T> request(Observable<RespBase<T>> observable){
        return request(observable, null, "", null);
    }
    protected <T> Observable<T> request(Observable<RespBase<T>> observable, Activity act, String note){
        return request(observable, act, note, null);
    }
    protected abstract <T> Observable<T> request(Observable<RespBase<T>> observable, Activity act, String note, IRespCallback cb);

}
