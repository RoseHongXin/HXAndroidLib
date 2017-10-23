package hx.widget.adapterview.recyclerview;

import android.app.Activity;
import android.support.v7.widget.LinearLayoutManager;

import com.jcodecraeer.xrecyclerview.ArrowRefreshHeader;
import com.jcodecraeer.xrecyclerview.XRecyclerView;
import java.lang.reflect.Field;
import hx.widget.adapterview.IReq2;
import hx.widget.adapterview.VhBase;
import static hx.widget.adapterview.IConstants.*;

/**
 * Created by rose on 16-8-12.
 */

public class XRvLoader {

    private XRecyclerView _rv;
    private ApBase mAdapter;
    private Activity mAct;
    private XRecyclerView.LoadingListener mListener;
    private ArrowRefreshHeader mRefreshHeader;
    private boolean mLoadMoreEnabled = true;

    private XRvLoader(Activity act, XRecyclerView _rv){
        this._rv = _rv;
        this.mAct = act;
        LinearLayoutManager layoutManager = new LinearLayoutManager(mAct);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        _rv.setLayoutManager(layoutManager);
        _rv.setAdapter(mAdapter);
        refreshSet();
    }

    public static XRvLoader get(Activity act, XRecyclerView _rv){
        return new XRvLoader(act, _rv);
    }

    private void registerListener(XRecyclerView.LoadingListener listener){
        if(listener != null) {
            this.mListener = listener;
            _rv.setLoadingListener(mListener);
        }
    }
    
    private void refreshSet(){
        try {
            Field rh = _rv.getClass().getDeclaredField("mRefreshHeader");
            rh.setAccessible(true);
            mRefreshHeader = (ArrowRefreshHeader)rh.get(_rv);
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    public <Ap extends ApBase<Vh, T>, Vh extends VhBase<T> , T> XRvLoader create(Ap adapter, IReq2<T> reqApi){
        this.mAdapter = adapter;
        XRecyclerView.LoadingListener mListener = new XRecyclerView.LoadingListener() {
            volatile int curPage = 1;
            @Override
            public void onRefresh() {
                reqApi.get(1)
                        .doOnCompleted(() -> _rv.postDelayed(_rv::refreshComplete, REFRESH_THRESHOLD))
                        .subscribe(res -> {
                            if(res != null){
                                curPage = 1;
                                adapter.setData(res);
                            }
                        });
            }
            @Override
            public void onLoadMore() {
                reqApi.get(curPage + 1)
                        .doOnCompleted(() -> _rv.postDelayed(_rv::loadMoreComplete, REFRESH_THRESHOLD))
                        .subscribe(res -> {
                            if(res != null && !res.isEmpty()) {
                                ++curPage;
                                adapter.addData(res);
                            }
                        });
            }
        };
        registerListener(mListener);
        return this;
    }

    public void loadMoreEnable(boolean enable){
        this.mLoadMoreEnabled = enable;
        _rv.setLoadingMoreEnabled(enable);
    }

    public void doRefresh(){
        mRefreshHeader.setState(ArrowRefreshHeader.STATE_REFRESHING);
        mRefreshHeader.setVisiableHeight(mRefreshHeader.mMeasuredHeight);
        _rv.postDelayed(() -> {
            mListener.onRefresh();
        }, REFRESH_THRESHOLD);
    }
}
