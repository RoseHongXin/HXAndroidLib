package hx.components;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import com.jcodecraeer.xrecyclerview.XRecyclerView;
import java.util.List;
import hx.lib.R;
import hx.view.swiperefresh.SwipeRefreshContainer;
import hx.widget.adapterview.VhBase;
import hx.widget.adapterview.recyclerview.ApBase;
import hx.widget.adapterview.recyclerview.XRvLoader;
import hx.widget.adapterview.swiperefresh.SwipeRefreshLoader;
import rx.Observable;

/**
 * Created by Rose on 3/1/2017.
 */

public class FRecyclerView extends FBase {

    protected XRecyclerView _rv_;

    protected XRvLoader mRvLoader;
    InitCallback mCb;

    public static FRecyclerView newInstance(InitCallback cb) {
        if(cb == null){
            throw new NullPointerException("Must specify a XRvLoader");
        }
        FRecyclerView fragment = new FRecyclerView();
        fragment.mCb = cb;
        return fragment;
    }


    @Override
    public int sGetLayoutRes() {
        return R.layout.f_base_rv_refresh;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        _rv_ = (XRecyclerView)view.findViewById(R.id._rv_);
        init();
    }
    private void init(){
        if(mCb == null) return;
        mRvLoader = mCb.getRecyclerViewLoader(_rv_);
        sRegisterRefreshCb(() -> mRvLoader.doRefresh());
    }

    protected void sSetInitCallback(InitCallback cb){
        if(cb == null){
            throw new NullPointerException("Must specify a XRvLoader");
        }
        this.mCb = cb;
        init();
    }

    public interface InitCallback{
        XRvLoader getRecyclerViewLoader(XRecyclerView _rv);
    }
}
