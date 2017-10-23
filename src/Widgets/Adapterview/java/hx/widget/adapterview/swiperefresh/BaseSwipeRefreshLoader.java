package hx.widget.adapterview.swiperefresh;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.List;

import hx.view.swiperefresh.SwipeRefreshContainer;

/**
 * Created by RoseHongXin on 2017/9/20 0020.
 */

public class BaseSwipeRefreshLoader {

    protected SwipeRefreshContainer _src;
//    private SwipeRefreshHeaderView _sr_header;
//    private SwipeLoadMoreFooterView _sr_footer;
    protected RecyclerView _sr_target;
    protected int mFistPageIdx = 1;
    protected int mCurPage = mFistPageIdx;
    protected boolean mLoadMoreEnable = true;

    protected BaseSwipeRefreshLoader(SwipeRefreshContainer _src, boolean loadMoreEnable){
        this._src = _src;
        this.mLoadMoreEnable = loadMoreEnable;
        _src.setLoadMoreEnabled(mLoadMoreEnable);
        _sr_target = (RecyclerView) _src.getTargetView();
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(_src.getContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        _sr_target.setLayoutManager(linearLayoutManager);
    }

    protected boolean dataIsReady(List datas){
        if(datas == null || datas.isEmpty()) {
            if(mLoadMoreEnable) _src.setLoadMoreEnabled(false);
            return false;
        }
        else {
            if(mLoadMoreEnable) _src.setLoadingMore(true);
        }
        return true;
    }

    public void refresh(){
        _src.setRefreshing(true);
    }
}
