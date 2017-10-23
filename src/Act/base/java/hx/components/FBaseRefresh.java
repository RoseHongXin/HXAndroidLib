package hx.components;

import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import hx.lib.R;

/**
 * Created by rose on 16-8-11.
 */

public abstract class FBaseRefresh extends FBase {

    ViewGroup mContainer;
        
    SwipeRefreshLayout _srl_;
    
    public abstract IRefreshCb sRegisterRefreshCb();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mContainer = (ViewGroup)inflater.inflate(R.layout.f_base_refresh, container, false);
        return mContainer;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        _srl_ = (SwipeRefreshLayout)view.findViewById(R.id._srl_);
        View layout = getActivity().getLayoutInflater().inflate(sGetLayoutRes(), mContainer, false);
        _srl_.addView(layout);
        mRefreshCb = sRegisterRefreshCb();
        _srl_.setOnRefreshListener(() -> mRefreshCb.onRefresh());
    }

    @Override
    public void onStop(){
        super.onStop();
        if(_srl_ != null && _srl_.isRefreshing()) sStopRefresh();
    }

    protected void sStopRefresh(){
        if(_srl_ != null) _srl_.setRefreshing(false);
    }
    protected void sRefresh(){
        _srl_.setProgressViewOffset(false, 0, 100);
        if(mRefreshCb != null){
            _srl_.setRefreshing(true);
            mRefreshCb.onRefresh();
        }

        /*getActivity().runOnUiThread(() -> {
            if(mRefreshCb != null){
                _srl_.setRefreshing(true);
                mRefreshCb.onRefresh();
            }
        });
        _srl_.post(() -> {
            if(mRefreshCb != null){
                _srl_.setRefreshing(true);
                mRefreshCb.onRefresh();
            }
        });*/
    }
}
