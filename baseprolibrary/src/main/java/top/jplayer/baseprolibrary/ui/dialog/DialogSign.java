package top.jplayer.baseprolibrary.ui.dialog;

import android.content.Context;
import android.view.Gravity;
import android.view.View;

import top.jplayer.baseprolibrary.R;
import top.jplayer.baseprolibrary.widgets.dialog.BaseCustomDialog;

/**
 * Created by Obl on 2018/3/15.
 * top.jplayer.baseprolibrary.widgets.dialog
 */

public class DialogSign extends BaseCustomDialog {

    public DialogSign(Context context) {
        super(context);
    }

    @Override
    protected void initView(View view) {
        view.findViewById(R.id.ivCancel).setOnClickListener(v -> cancel());
    }

    @Override
    public int setWidth(int i) {
        return super.setWidth(8);
    }

    @Override
    public int setAnim() {
        return R.style.AnimTop;
    }

    @Override
    public int setGravity() {
        return Gravity.CENTER;
    }

    @Override
    public float setAlpha() {
        return 0.1f;
    }

    @Override
    public int initLayout() {
        return R.layout.dialog_sign;
    }
}
