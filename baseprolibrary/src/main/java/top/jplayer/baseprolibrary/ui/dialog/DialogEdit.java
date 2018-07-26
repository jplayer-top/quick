package top.jplayer.baseprolibrary.ui.dialog;

import android.app.Activity;
import android.content.Context;
import android.text.InputType;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageButton;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import top.jplayer.baseprolibrary.R;
import top.jplayer.baseprolibrary.utils.KeyboardUtils;
import top.jplayer.baseprolibrary.widgets.dialog.BaseCustomDialog;

/**
 * Created by Obl on 2018/3/15.
 * top.jplayer.baseprolibrary.widgets.dialog
 */

public class DialogEdit extends BaseCustomDialog {

    private EditText mEditPassword;

    @Override
    public int initLayout() {
        return R.layout.dialog_edit;
    }

    public DialogEdit(Context context) {
        super(context);
    }

    @Override
    protected void initView(View view) {
        view.findViewById(R.id.btnCancel).setOnClickListener(v -> cancel());
        mEditPassword = view.findViewById(R.id.editPassword);
        view.findViewById(R.id.ivCancel).setOnClickListener(v -> mEditPassword.setText(""));
        ImageButton ivShow = view.findViewById(R.id.ivShow);
        ivShow.setOnClickListener(v -> {
            ivShow.setSelected(!ivShow.isSelected());
            mEditPassword.setInputType(ivShow.isSelected() ? InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD : InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
            mEditPassword.setSelection(mEditPassword.getText().length());
        });
    }

    public DialogEdit setTitle(String title) {
        bindText(title, R.id.tvTitle);
        return this;
    }

    @Override
    public void cancel() {
        KeyboardUtils.init().hideSoftInput((Activity) mWeakReference.get(), mContentView);
        Observable.interval(300, TimeUnit.MILLISECONDS).subscribe(aLong -> super.cancel());
    }

    public DialogEdit setSubTitle(String title) {
        bindText(title, R.id.tvSubTitle);
        return this;
    }

    @Override
    public int setSoftInputState() {
        return WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE |
                WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN;
    }

    @Override
    public void setSureListener(SureListener listener) {
       listener.onSure(mEditPassword);
    }

    @Override
    public int setWidth(int i) {
        return super.setWidth(8);
    }

    @Override
    public int setAnim() {
        return R.style.AnimFade;
    }

}
