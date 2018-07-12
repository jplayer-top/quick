package top.jplayer.baseprolibrary.net.tip;

import android.content.Context;
import android.support.annotation.ColorInt;
import android.support.annotation.DrawableRes;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import top.jplayer.baseprolibrary.R;
import top.jplayer.baseprolibrary.widgets.dialog.BaseCustomDialog;

/**
 * Created by Obl on 2018/7/12.
 * top.jplayer.baseprolibrary.net.progress
 * call me : jplayer_top@163.com
 * github : https://github.com/oblivion0001
 */

public class DialogShortNetTip extends BaseCustomDialog {

    private TextView mTvTip;
    private ImageView mIvTip;

    public DialogShortNetTip(Context context) {
        super(context);
    }

    @Override
    protected void initView(View view) {
        mTvTip = view.findViewById(R.id.tvTip);
        mIvTip = view.findViewById(R.id.ivTip);
    }

    public DialogShortNetTip text(String msg) {
        mTvTip.setText(msg);
        return this;
    }

    public DialogShortNetTip color(@ColorInt int color) {
        mTvTip.setTextColor(color);
        return this;
    }

    public DialogShortNetTip res(@DrawableRes int res) {
        mIvTip.setImageResource(res);
        return this;
    }

    @Override
    public int setWidth(int i) {
        return super.setWidth(5);
    }

    @Override
    public void show() {
        super.show();
        dismissDelay(1500);
    }

    private void dismissDelay(int seconds) {
        Observable.timer(seconds, TimeUnit.MILLISECONDS).subscribe(aLong -> {
            if (isShowing()) {
                dismiss();
            }
        });
    }

    public void show(int seconds) {
        super.show();
        dismissDelay(seconds);
    }

    @Override
    public int initLayout() {
        return R.layout.dialog_net_tip;
    }
}
