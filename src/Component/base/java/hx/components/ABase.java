package hx.components;

import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutCompat;
import android.support.v7.widget.Toolbar;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import butterknife.ButterKnife;
import hx.lib.R;

/**
 * Created by rose on 16-8-12.
 */

@Deprecated
public abstract class ABase extends AppCompatActivity {

    private ViewGroup mLayout;

    @LayoutRes
    public abstract int sGetLayoutRes();
    public View sRequireTb(){
        return null;
    }

    public ViewGroup sGetLayout(){
        return mLayout;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(getLayoutRes());

        View layout = getLayoutInflater().inflate(sGetLayoutRes(), (ViewGroup) findViewById(android.R.id.content), false);
//        View layout = getLayoutInflater().inflate(sGetLayoutRes(), null);

        View _tb = sRequireTb();
        if(_tb != null){
            /*if(_tb instanceof AppBarLayout) {
                Toolbar toolbar = (Toolbar) ((AppBarLayout) _tb).getChildAt(0);
                setSupportActionBar(toolbar);
            }*/
            LinearLayout content = new LinearLayout(getApplicationContext());
            content.setLayoutParams(new LinearLayoutCompat.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
            content.setOrientation(LinearLayout.VERTICAL);

//            TypedValue typedValue = new TypedValue();
//            getTheme().resolveAttribute(R.attr.colorPrimary, typedValue, true);
//            int color = typedValue.mData;
//            _tb.setBackgroundColor(color);

            content.addView(_tb);
            content.addView(layout);
            setContentView(content);
            mLayout = content;
        }else{
            setContentView(layout);
            mLayout = (ViewGroup) layout;
        }
        ButterKnife.bind(this);
    }



}
