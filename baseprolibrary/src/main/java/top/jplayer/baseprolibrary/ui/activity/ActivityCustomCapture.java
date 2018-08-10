package top.jplayer.baseprolibrary.ui.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.text.TextUtils;
import android.view.View;
import android.widget.FrameLayout;

import com.uuzuche.lib_zxing.activity.CaptureFragment;
import com.uuzuche.lib_zxing.activity.CodeUtils;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import top.jplayer.baseprolibrary.R;
import top.jplayer.baseprolibrary.net.tip.DialogLoading;
import top.jplayer.baseprolibrary.utils.BitmapUtil;
import top.jplayer.baseprolibrary.utils.QRCodeDecoderUtils;
import top.jplayer.baseprolibrary.utils.StringUtils;
import top.jplayer.baseprolibrary.utils.ToastUtils;

/**
 * Created by Obl on 2018/3/27.
 * call me : jplayer_top@163.com
 * github : https://github.com/oblivion0001
 */

public class ActivityCustomCapture extends CommonToolBarActivity {

    @Override
    public int initAddLayout() {
        return R.layout.activity_custom_camera;
    }

    @Override
    public void initAddView(FrameLayout rootView) {
        super.initAddView(rootView);
        toolRightVisible(mTvToolRight, "相册");
//        mRootView.setBackgroundColor(color(R.color.trans));
        tooColor(true, R.color.trans);
        Observable.timer(100, TimeUnit.MILLISECONDS).subscribe(aLong -> refresh());
    }

    @Override
    public void toolRightBtn(View v) {
        super.toolRightBtn(v);
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        intent.setType("image/*");
        startActivityForResult(intent, 1);
    }

    @Override
    public int toolMarginTop() {
        return 0;
    }

    /**
     * 二维码解析回调函数
     */
    CodeUtils.AnalyzeCallback analyzeCallback = new CodeUtils.AnalyzeCallback() {

        @Override
        public void onAnalyzeSuccess(Bitmap mBitmap, String result) {
            ToastUtils.init().showQuickToast(result);
            StringUtils.init().copyString(mActivity, result);
            refresh();
        }

        @Override
        public void onAnalyzeFailed() {
            ToastUtils.init().showErrorToast(mActivity, "解析二维码失败");
            refresh();
        }
    };

    private void refresh() {
        try {
            CaptureFragment captureFragment = new CaptureFragment();
            captureFragment.setAnalyzeCallback(analyzeCallback);
            getSupportFragmentManager().beginTransaction().replace(R.id.fl_zxing_container, captureFragment).commit();
        } catch (Exception e) {
            finish();
        }
    }


    private DialogLoading mDialogLoading;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {
            if (data != null) {
                Uri uri = data.getData();
                try {
                    String path = StringUtils.getRealFilePath(mActivity, uri);
                    Bitmap bitmap = BitmapUtil.getDecodeAbleBitmap(path);
                    if (bitmap != null) {
                        Observable.just(bitmap)
                                .subscribeOn(Schedulers.io())
                                .map(QRCodeDecoderUtils::syncDecodeQRCode)
                                .observeOn(AndroidSchedulers.mainThread())
                                .subscribe(new Observer<String>() {
                                    @Override
                                    public void onSubscribe(Disposable d) {
                                        if (!d.isDisposed()) {
                                            mDialogLoading = new DialogLoading(mActivity);
                                            mDialogLoading.show();
                                        }
                                    }

                                    @Override
                                    public void onNext(String result) {
                                        if (mDialogLoading != null) {
                                            mDialogLoading.dismiss();
                                        }
                                        if (TextUtils.isEmpty(result)) {
                                            ToastUtils.init().showInfoToast(mActivity, "未发现二维码");
                                        } else {
                                            StringUtils.init().copyString(mActivity, result);
                                            ToastUtils.init().showQuickToast(result);
                                        }
                                    }

                                    @Override
                                    public void onError(Throwable e) {
                                        if (mDialogLoading != null) {
                                            mDialogLoading.dismiss();
                                        }
                                    }

                                    @Override
                                    public void onComplete() {
                                        refresh();
                                    }
                                });
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
