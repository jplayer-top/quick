package top.jplayer.baseprolibrary.ui.dialog;

import android.app.Activity;
import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import top.jplayer.baseprolibrary.R;
import top.jplayer.baseprolibrary.utils.KeyboardUtils;
import top.jplayer.baseprolibrary.widgets.dialog.BaseCustomDialog;

/**
 * Created by Obl on 2018/3/15.
 * top.jplayer.baseprolibrary.widgets.dialog
 */

public class DialogEditBottom extends BaseCustomDialog {

    private EditText mEdInput;

    public DialogEditBottom(Context context) {
        super(context);
    }

    @Override
    protected void initView(View view) {
        mEdInput = view.findViewById(R.id.et_input);
    }

    @Override
    public int setSoftInputState() {
        return WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE |
                WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN;
    }

    @Override
    public void cancel() {
        KeyboardUtils.init().hideSoftInput((Activity) mWeakReference.get(), mContentView);
        Observable.interval(300, TimeUnit.MILLISECONDS).subscribe(aLong -> super.cancel());
    }

    @Override
    public void setSureListener(SureListener listener) {
        listener.onSure(mEdInput);
    }

    @Override
    public int setWidth(int i) {
        return super.setWidth(10);
    }

    @Override
    public int setAnim() {
        return R.style.AnimBottom;
    }

    @Override
    public int setGravity() {
        return Gravity.BOTTOM;
    }

    @Override
    public int initLayout() {
        return R.layout.dialog_input_text;
    }
}
