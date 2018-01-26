package hx.components;

import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.TextView;

import butterknife.ButterKnife;

/**
 * 根Activity
 */
public class ABase2 extends AppCompatActivity{

    public Toolbar _tb_;
    public TextView _tv_title;

    @Override
    public void setContentView(int layoutResID) {
        super.setContentView(layoutResID);
        ButterKnife.bind(this);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                //主界面左上角的icon点击反应
                clickNavigationIcon(item);
                break;
            case android.R.id.closeButton:
                //主界面左上角的icon点击反应
                clickNavigationIcon(item);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    public void clickNavigationIcon(MenuItem item){
        finish();
    }


}
