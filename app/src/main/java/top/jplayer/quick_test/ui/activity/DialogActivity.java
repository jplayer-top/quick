package top.jplayer.quick_test.ui.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;

import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import io.reactivex.Observable;
import io.reactivex.disposables.Disposable;
import top.jplayer.baseprolibrary.mvp.contract.IContract;
import top.jplayer.baseprolibrary.mvp.model.bean.CartBean;
import top.jplayer.baseprolibrary.net.retrofit.IoMainSchedule;
import top.jplayer.baseprolibrary.net.tip.DialogShortNetTip;
import top.jplayer.baseprolibrary.ui.activity.CommonToolBarActivity;
import top.jplayer.baseprolibrary.ui.dialog.DialogEdit;
import top.jplayer.baseprolibrary.ui.dialog.DialogEditBottom;
import top.jplayer.baseprolibrary.ui.dialog.DialogFragmentFilter;
import top.jplayer.baseprolibrary.ui.dialog.DialogFragmentIncludeFragment;
import top.jplayer.baseprolibrary.ui.dialog.DialogFragmentOrder;
import top.jplayer.baseprolibrary.ui.dialog.DialogLogout;
import top.jplayer.baseprolibrary.ui.dialog.DialogNoviceGuide;
import top.jplayer.baseprolibrary.ui.dialog.DialogRedHb;
import top.jplayer.baseprolibrary.ui.dialog.DialogShare;
import top.jplayer.baseprolibrary.ui.dialog.DialogSign;
import top.jplayer.baseprolibrary.ui.dialog.DialogSubmitSure;
import top.jplayer.baseprolibrary.utils.ActivityUtils;
import top.jplayer.baseprolibrary.utils.LogUtil;
import top.jplayer.baseprolibrary.utils.PickerUtils;
import top.jplayer.baseprolibrary.utils.ToastUtils;
import top.jplayer.quick_test.R;
import top.jplayer.quick_test.mvp.presenter.DialogPresenter;

/**
 * Created by Obl on 2018/7/26.
 * top.jplayer.quick_test.ui.activity
 * call me : jplayer_top@163.com
 * github : https://github.com/oblivion0001
 */

public class DialogActivity extends CommonToolBarActivity implements IContract.IView {

    @BindView(R.id.btn_00)
    Button mBtn00;
    @BindView(R.id.btn_01)
    Button mBtn01;
    @BindView(R.id.btn_02)
    Button mBtn02;
    @BindView(R.id.btn_03)
    Button mBtn03;
    @BindView(R.id.btn_04)
    Button mBtn04;
    @BindView(R.id.btn_05)
    Button mBtn05;
    @BindView(R.id.btn_06)
    Button mBtn06;
    @BindView(R.id.btn_07)
    Button mBtn07;
    @BindView(R.id.btn_08)
    Button mBtn08;
    @BindView(R.id.btn_09)
    Button mBtn09;
    @BindView(R.id.btn_10)
    Button mBtn10;
    @BindView(R.id.btn_11)
    Button mBtn11;
    @BindView(R.id.btn_12)
    Button mBtn12;
    @BindView(R.id.btn_13)
    Button mBtn13;
    @BindView(R.id.btn_14)
    Button mBtn14;
    private Unbinder mUnbinder;
    private PickerUtils mPickerUtils;

    @Override
    public int initAddLayout() {
        return R.layout.activity_dialog;
    }

    @Override
    public void initAddView(FrameLayout rootView) {
        super.initAddView(rootView);
        mPickerUtils = new PickerUtils();
        mUnbinder = ButterKnife.bind(this, rootView);
        mIvToolRight.setVisibility(View.INVISIBLE);
        mBtn00.setOnClickListener(v -> {
            Observable.intervalRange(0, 3, 0, 2, TimeUnit.SECONDS)
                    .compose(new IoMainSchedule<>())
                    .subscribe(aLong -> {
                        if (aLong == 0) {
                            new DialogShortNetTip(this)
                                    .color(getResources().getColor(top.jplayer.baseprolibrary.R.color.tomato))
                                    .text("警告")
                                    .res(top.jplayer.baseprolibrary.R.drawable.dialog_warn)
                                    .show();
                        } else if (aLong == 1) {
                            new DialogShortNetTip(this)
                                    .color(getResources().getColor(top.jplayer.baseprolibrary.R.color.colorPrimary))
                                    .text("成功")
                                    .res(top.jplayer.baseprolibrary.R.drawable.dialog_success)
                                    .show();
                        } else {
                            new DialogShortNetTip(this).show();
                        }
                    });

        });
        mBtn01.setOnClickListener(v -> {
            DialogEdit edit = new DialogEdit(this);
            edit.setTitle("密码修改")
                    .show(R.id.btnSure, v1 -> {
                        EditText editText = (EditText) v1;
                        ToastUtils.init().showQuickToast(editText.getText().toString());
                        edit.cancel();
                    });
        });
        mBtn02.setOnClickListener(v -> new DialogEditBottom(this).show());
        mBtn03.setOnClickListener(v -> new DialogFragmentFilter().show(getSupportFragmentManager(), "1"));
        mBtn04.setOnClickListener(v -> new DialogPresenter(this).requestCart());
        mBtn05.setOnClickListener(v -> new DialogLogout(this).show());
        mBtn06.setOnClickListener(v -> new DialogNoviceGuide(this).show());
        mBtn07.setOnClickListener(v -> {
            DialogRedHb dialogRedHb = new DialogRedHb(this);
            dialogRedHb.show(R.id.ivOpen, v1 -> {
                Disposable disposable = dialogRedHb.looperAnim(v1);
                //模拟网络请求
                Observable.timer(2000, TimeUnit.MILLISECONDS).subscribe(aLong -> {
                }, throwable -> {
                }, () -> {
                    if (!disposable.isDisposed()) {
                        disposable.dispose();
                    }
                });
            });
        });
        mBtn08.setOnClickListener(v -> new DialogShare(this).show());
        mBtn09.setOnClickListener(v -> new DialogSign(this).show());
        mBtn10.setOnClickListener(v -> new DialogSubmitSure(this).show());
        mBtn13.setOnClickListener(v -> new DialogFragmentIncludeFragment().show(getSupportFragmentManager(), ""));
        mBtn12.setOnClickListener(v -> ActivityUtils.init().start(this, PopupActivity.class, "Popup"));
        mBtn11.setOnClickListener(v -> {
            //具体使用请查看：https://github.com/Bigkoo/Android-PickerView

            mPickerUtils.initTimePicker(this, (date, pattern) -> {
                ToastUtils.init().showQuickToast(pattern);
            });
            mPickerUtils.timeShow();
        });
        mBtn14.setOnClickListener(v -> {
        });
        mBtn14.setOnClickListener(v -> {
            mPickerUtils.initLocalPicker(this, LogUtil::str);
            mPickerUtils.localShow(this);
        });
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        mUnbinder.unbind();
    }

    public void responseShopOrder(CartBean cartBean) {
        DialogFragmentOrder dialogOrder = new DialogFragmentOrder();
        Bundle arguments = new Bundle();
        arguments.putParcelable("cart", cartBean);
        dialogOrder.setArguments(arguments);
        dialogOrder.show(getSupportFragmentManager(), "order");
    }
}
