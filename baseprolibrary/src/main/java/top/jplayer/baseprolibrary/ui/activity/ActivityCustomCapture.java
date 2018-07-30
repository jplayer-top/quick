package top.jplayer.baseprolibrary.ui.activity;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.View;

import com.uuzuche.lib_zxing.activity.CaptureFragment;
import com.uuzuche.lib_zxing.activity.CodeUtils;

import top.jplayer.baseprolibrary.R;
import top.jplayer.baseprolibrary.net.tip.DialogLoading;
import top.jplayer.baseprolibrary.utils.BitmapUtil;
import top.jplayer.baseprolibrary.utils.QRCodeDecoderUtils;
import top.jplayer.baseprolibrary.utils.StringUtils;
import top.jplayer.baseprolibrary.utils.ToastUtils;

/**
 * Created by Obl on 2018/3/27.
 * com.ilanchuang.xiaoi.ui.family
 * call me : jplayer_top@163.com
 * github : https://github.com/oblivion0001
 */

public class ActivityCustomCapture extends SuperBaseActivity {

    private AsyncTask<Bitmap, Void, String> asyncTask = null;

    @Override
    protected int initRootLayout() {
        return R.layout.custom_camera;
    }

    @Override
    public void initRootData(View view) {
        view.findViewById(R.id.tvCameraLocal).setOnClickListener(v -> {
            Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
            intent.addCategory(Intent.CATEGORY_OPENABLE);
            intent.setType("image/*");
            startActivityForResult(intent, 1);
        });
        view.findViewById(R.id.ivBack).setOnClickListener(v -> finish());
        refresh();
    }

    @Override
    protected void initImmersionBar() {
        super.initImmersionBar();
        mImmersionBar.titleBar(superRootView.findViewById(R.id.toolbar)).init();
    }

    /**
     * 二维码解析回调函数
     */
    CodeUtils.AnalyzeCallback analyzeCallback = new CodeUtils.AnalyzeCallback() {

        @Override
        public void onAnalyzeSuccess(Bitmap mBitmap, String result) {
            ToastUtils.init().showQuickToast(result);
        }

        @Override
        public void onAnalyzeFailed() {
            ToastUtils.init().showErrorToast(mActivity, "解析二维码失败");
            refresh();
        }
    };
    private MyAsyncTask mAsyncTask;


    private void refresh() {
        try {
            CaptureFragment captureFragment = new CaptureFragment();
            captureFragment.setAnalyzeCallback(analyzeCallback);
            getSupportFragmentManager().beginTransaction().replace(R.id.fl_zxing_container, captureFragment).commit();
        } catch (Exception e) {
            finish();
        }
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (asyncTask != null) {
            asyncTask.cancel(true);
            asyncTask = null;
        }
    }

    private class MyAsyncTask extends AsyncTask<Bitmap, Void, String> {

        private DialogLoading mDialogLoading;

        MyAsyncTask(Activity activity) {
            mDialogLoading = new DialogLoading(activity);
            mDialogLoading.show();

        }

        @Override
        protected String doInBackground(Bitmap... bitmaps) {
            return QRCodeDecoderUtils.syncDecodeQRCode(bitmaps[0]);
        }

        @Override
        protected void onPostExecute(String result) {
            mDialogLoading.dismiss();
            if (TextUtils.isEmpty(result)) {
                ToastUtils.init().showInfoToast(mActivity, "未发现二维码");
                refresh();
            } else {
                ToastUtils.init().showQuickToast(result);
            }
        }

        @Override
        protected void onCancelled() {
            super.onCancelled();
            mDialogLoading.dismiss();
            refresh();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {
            if (data != null) {
                Uri uri = data.getData();
                try {
                    asyncTask = new MyAsyncTask(this);
                    String path = StringUtils.getRealFilePath(mActivity, uri);
                    Bitmap bitmap = BitmapUtil.getDecodeAbleBitmap(path);
//                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), uri);
                    asyncTask.execute(bitmap);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
