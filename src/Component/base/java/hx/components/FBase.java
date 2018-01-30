package hx.components;

import android.os.Bundle;
import android.support.annotation.ArrayRes;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import butterknife.ButterKnife;

import static hx.components.IConstants.*;


/**
 * Created by rose on 16-8-11.
 */

public abstract class FBase extends Fragment {

    int mExpireThreshold;
    int mPageVisibleCount = 1;
    long mPageLastVisibleTime = 0;

    protected IRefreshCb mRefreshCb;

    @LayoutRes public abstract int sGetLayoutRes();

    public void sRegisterRefreshCb(IRefreshCb cb){
        this.mRefreshCb = cb;
        this.mExpireThreshold = PAGE_DEFAULT_EXPIRE;
    }
    public void sRegisterRefreshCb(IRefreshCb cb, int expire){
        this.mRefreshCb = cb;
        if(mExpireThreshold <= 0) this.mExpireThreshold = PAGE_DEFAULT_EXPIRE;
        else this.mExpireThreshold = expire;
    }
    public void sRegisterLoadCb(IRefreshCb cb){
        this.mPageLastVisibleTime = PAGE_NO_EXPIRE;
        this.mRefreshCb = cb;
    }

    public void sSetText(TextView _tv, @StringRes int strRes, Object ... params){
        _tv.setText(sGetText(strRes, params));
    }
    public String sGetText(@StringRes int strRes, Object ... params){
        if(!isAdded() || getContext() == null) return "";
        if(params == null || params.length == 0){
            return getString(strRes);
        }else{
            return String.format(getString(strRes), params);
        }
    }
    public String[] sGetStrArray(@ArrayRes int arrayRes){
        if(!isAdded() || getContext() == null) return new String[]{"", "", "", "", "", "", "", "", "", "", "", "", "", "", "", ""}; //length = 16, 保证调用不会出现下标越界.
        return getContext().getResources().getStringArray(arrayRes);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(sGetLayoutRes(), container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);
    }

    @Override
    public void onResume() {
        super.onResume();
        checkIfNeedRefreshPage();
    }

    @Override
    public void setUserVisibleHint(boolean mIsVisibleToUser) {
        super.setUserVisibleHint(mIsVisibleToUser);
        checkIfNeedRefreshPage();
    }

    protected void checkIfNeedRefreshPage(){
        //解决fragment嵌套,父fragment的visible属性不能下放到子fragment的情况.
        boolean curFraVisible = getUserVisibleHint();
        boolean parentFraVisible = getParentFragment() == null || getParentFragment().getUserVisibleHint();
        boolean refresh = false;
        if(curFraVisible && parentFraVisible) {
            if(mPageLastVisibleTime == PAGE_NO_EXPIRE) refresh = true;
            else if (mPageVisibleCount == 1) {
                mPageLastVisibleTime = System.currentTimeMillis();
                ++mPageVisibleCount;
                refresh = true;
            } else {
                long cur = System.currentTimeMillis();
                if((cur - mPageLastVisibleTime) > mExpireThreshold){
                    mPageVisibleCount = 0;
                    mPageLastVisibleTime = cur;
                    refresh = true;
                }
                ++mPageVisibleCount;
            }
        }
        if(refresh && mRefreshCb != null) mRefreshCb.onRefresh();
    }
}
