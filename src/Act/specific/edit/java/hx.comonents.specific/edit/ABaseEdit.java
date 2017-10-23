package hx.comonents.specific.edit;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import hx.components.ABase;
import hx.lib.R;
import hx.kit.async.RxBus;
import rx.android.widget.WidgetObservable;


public abstract class ABaseEdit extends ABase {

    protected static final String TITLE = "title";
    protected static final String TXT = "txt";
    protected static final String HINT = "hint";
    protected static final String INPUTTYPE = "inputType";
    protected static final String REQ_CODE = "reqCode";

    TextView _tb_tv_title;
    TextView _tv_save;
    EditText _et;
    ImageView _iv_delete;

    boolean hasModify  = false;
    String title;
    String txt;
    String hint;
    int inputType;
    int reqCode;

    public abstract RxBus getRxBus();
    //public static abstract void start(Activity mAct, String title, String txt, String hint, int inputType, int reqCode);
    /*{
        Intent intent = new Intent(mAct, AEdit.class);
        intent.putExtra(TITLE, title);
        intent.putExtra(TXT, txt);
        intent.putExtra(HINT, hint);
        intent.putExtra(INPUTTYPE, inputType);
        intent.putExtra(REQ_CODE, reqCode);
        mAct.startActivity(intent);
    }*/

    private void retreive(Intent intent){
        title = intent.getStringExtra(TITLE);
        txt = intent.getStringExtra(TXT);
        hint = intent.getStringExtra(HINT);
        inputType = intent.getIntExtra(INPUTTYPE, 0);
        reqCode = intent.getIntExtra(REQ_CODE, 0);
        if(TextUtils.isEmpty(txt)) _et.setHint(hint);
        else _et.setText(txt);
        _et.setInputType(inputType);
        _tb_tv_title.setText(title);
    }

    @Override
    public int sGetLayoutRes() {
        return R.layout.a_edit;
    }

    @Override
    public View sRequireTb() {
        View _tb = getLayoutInflater().inflate(R.layout.tb_edit, null);
        _tb.findViewById(R.id._iv_back).setOnClickListener(view -> finish());
        _tb_tv_title = (TextView)_tb.findViewById(R.id._tb_tv_title);
        _tv_save = (TextView)_tb.findViewById(R.id._tv_save);
        return _tb;
//        return Tb.init(this, "", R.mipmap.i_edit_back, view -> finish(), "", v -> {});
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loadViews();
        retreive(getIntent());
        _tv_save.setVisibility(View.GONE);
        WidgetObservable.text(_et)
                .subscribe(onTextChangeEvent -> {
                    hasModify = true;
                    _tv_save.setVisibility(View.VISIBLE);
                });
    }

    private void loadViews(){
        //_tb_tv_title = (TextView)findViewById(R.id._tb_tv_title);
        //_tv_save = (TextView)findViewById(R.id._tv_save);
        _et = (EditText)findViewById(R.id._et);
         _iv_delete = (ImageView)findViewById(R.id._iv_delete);

        _tv_save.setOnClickListener(view->{
            RxBus rxBus = getRxBus();
            if(rxBus == null) return;
            RbEdit edit = new RbEdit();
            edit.reqCode = reqCode;
            edit.txt = _et.getText().toString();
            rxBus.post(edit);
            finish();
        });
        _iv_delete.setOnClickListener(view-> _et.setText(""));
    }
}
