package hx.components;

import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.ButterKnife;

import static hx.components.IConstants.*;


/**
 * Created by rose on 16-8-11.
 */

public abstract class FBase extends Fragment {

    int mExpire;
    boolean mIsVisibleToUser = false;
    int mPageVisibleCount = 1;
    long mPageLastValidTimeMillis = 0;

    protected IRefreshCb mRefreshCb;

    @LayoutRes public abstract int sGetLayoutRes();

    public void sRegisterRefreshCb(IRefreshCb cb){
        this.mRefreshCb = cb;
        this.mExpire = PAGE_DEFAULT_EXPIRE;
    }
    public void sRegisterRefreshCb(IRefreshCb cb, int expire){
        this.mRefreshCb = cb;
        if(mExpire <= 0) this.mExpire = PAGE_DEFAULT_EXPIRE;
        else this.mExpire = expire;
    }
    public void sRegisterLoadCb(IRefreshCb cb){
        this.mPageLastValidTimeMillis = PAGE_NO_EXPIRE;
        this.mRefreshCb = cb;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        return inflater.inflate(sGetLayoutRes(), container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);
    }

    protected boolean ifNeedRefreshPage(){
        //解决fragment嵌套,父fragment的visible属性不能下放到子fragment的情况.
        boolean curFraVisible = getUserVisibleHint();
        boolean parentFraVisible = getParentFragment() == null || getParentFragment().getUserVisibleHint();
        mIsVisibleToUser = curFraVisible && parentFraVisible;
        if(mIsVisibleToUser && mRefreshCb != null) {
            if(mPageLastValidTimeMillis == PAGE_NO_EXPIRE) return true;
            else if (mPageVisibleCount == 1) {
                mPageLastValidTimeMillis = System.currentTimeMillis();
                ++mPageVisibleCount;
                return true;
            } else {
                long cur = System.currentTimeMillis();
                if((cur - mPageLastValidTimeMillis) > mExpire){
                    mPageVisibleCount = 0;
                    mPageLastValidTimeMillis = cur;
                    return true;
                }
                ++mPageVisibleCount;
            }
        }
        return false;
    }

    @Override
    public void onResume() {
        super.onResume();
        if(ifNeedRefreshPage()) mRefreshCb.onRefresh();
    }

    //called by viewpager if it is the container of those fragments, and called every time page switch
    //but if a fragment inside a fragment, it won't be called. ?????
    @Override
    public void setUserVisibleHint(boolean mIsVisibleToUser) {
        super.setUserVisibleHint(mIsVisibleToUser);
        this.mIsVisibleToUser = mIsVisibleToUser;
        if(ifNeedRefreshPage()) mRefreshCb.onRefresh();
    }
}
