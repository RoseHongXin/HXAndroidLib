package hx.components;

import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutCompat;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import hx.lib.R;

import static hx.components.IConstants.REFRESH_THRESHOLD;

/**
 * Created by rose on 16-8-12.
 */
public abstract class ABaseRefresh_ extends ABase {

    
    SwipeRefreshLayout _srl;
    IRefreshCb mRefreshCb;

    public abstract IRefreshCb sRegisterRefreshCb();

    public SwipeRefreshLayout sGetSwipeRefreshLayout(){
        return _srl;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(getLayoutRes());

        View layout = getLayoutInflater().inflate(sGetLayoutRes(), (ViewGroup) findViewById(android.R.id.content), false);
//        View layout = getLayoutInflater().inflate(sGetLayoutRes(), null);

        mRefreshCb = sRegisterRefreshCb();
        if(mRefreshCb != null){
            _srl = new SwipeRefreshLayout(getApplicationContext());
            _srl.setLayoutParams(new LinearLayoutCompat.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
            _srl.addView(layout);
            _srl.setOnRefreshListener(() -> mRefreshCb.onRefresh());
            layout = _srl;
        }

        View _tb = sRequireTb();
        if(_tb != null){
            //setSupportActionBar(_tb);
            LinearLayout content = new LinearLayout(getApplicationContext());
            content.setLayoutParams(new LinearLayoutCompat.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
            content.setOrientation(LinearLayout.VERTICAL);

            TypedValue typedValue = new TypedValue();
            getTheme().resolveAttribute(R.attr.colorPrimary, typedValue, true);
            int color = typedValue.data;
            _tb.setBackgroundColor(color);
            content.addView(_tb);
            content.addView(layout);
            setContentView(content);
        }else{
            setContentView(layout);
        }
    }

    @Override
    protected void onStop(){
        super.onStop();
        if(_srl != null && _srl.isRefreshing()) sStopRefresh();
    }

    protected void sDisableRefresh(){
        _srl.setEnabled(false);
        //_srl.setRefreshing(false);
    }
    protected void sRetoreRefresh(){
        _srl.setEnabled(true);
    }
    protected void sStopRefresh(){
        if(_srl != null) _srl.setRefreshing(false);
    }
    protected void sRefresh(){
        //if(_srl != null) _srl.setRefreshing(true);
        //_srl.setProgressViewOffset(false, 0, 100);
        //_srl.setRefreshing(true);
        if(_srl == null || mRefreshCb == null) return;
        _srl.postDelayed(() -> {
            _srl.setRefreshing(true);
            mRefreshCb.onRefresh();
        }, REFRESH_THRESHOLD);
    }

}
