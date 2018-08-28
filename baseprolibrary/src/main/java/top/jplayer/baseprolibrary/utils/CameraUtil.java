package top.jplayer.baseprolibrary.utils;

import android.app.Activity;
import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.jaiky.imagespickers.ImageConfig;
import com.jaiky.imagespickers.ImageLoader;
import com.jaiky.imagespickers.ImageSelector;

import top.jplayer.baseprolibrary.R;
import top.jplayer.baseprolibrary.glide.GlideUtils;


/**
 * Created by PEO on 2017/3/13.
 * 打开相机的UTils
 */

public class CameraUtil {
    private static CameraUtil mCamere;

    public static CameraUtil getInstance() {
        if (mCamere == null) {
            mCamere = new CameraUtil();
        }
        return mCamere;
    }

    /**
     * 打开相册
     */
    public void openSingalCamer(Activity activity) {
        ImageConfig imageConfig
                = new ImageConfig.Builder(new GlideLoader())
                .steepToolBarColor(activity.getResources().getColor(R.color.colorPrimary))
                .titleBgColor(activity.getResources().getColor(R.color.colorPrimary))
                .titleSubmitTextColor(activity.getResources().getColor(R.color.white))
                .titleTextColor(activity.getResources().getColor(R.color.white))
                // 开启单选   （默认为多选）
                .singleSelect()
                .crop(1, 1, 200, 200)
                // 开启拍照功能 （默认关闭）
                .showCamera()
                // 拍照后存放的图片路径（默认 /temp/picture） （会自动创建）
                .filePath("/ImageSelector/Pictures")
                .requestCode(1)
                .build();
        ImageSelector.open(activity, imageConfig);   // 开启图片选择器
    }

    /**
     * 打开相册
     */
    public void openSingalCamerNoCrop(Activity activity) {
        openSingalCamerNoCrop(activity, 1);
    }

    /**
     * 打开相册
     */
    public void openSingalCamerNoCrop(Activity activity, int code) {
        ImageConfig imageConfig
                = new ImageConfig.Builder(new GlideLoader())
                .steepToolBarColor(activity.getResources().getColor(R.color.colorPrimary))
                .titleBgColor(activity.getResources().getColor(R.color.colorPrimary))
                .titleSubmitTextColor(activity.getResources().getColor(R.color.white))
                .titleTextColor(activity.getResources().getColor(R.color.white))
                // 开启单选   （默认为多选）
                .singleSelect()
                // 开启拍照功能 （默认关闭）
                .showCamera()
                // 拍照后存放的图片路径（默认 /temp/picture） （会自动创建）
                .filePath("/ImageSelector/Pictures")
                .requestCode(code)
                .build();
        ImageSelector.open(activity, imageConfig);   // 开启图片选择器
    }

    /**
     * 打开图库选择照片
     */
    public void openCamer(Activity activity) {
        ImageConfig imageConfig
                = new ImageConfig.Builder(new GlideLoader())
                .steepToolBarColor(activity.getResources().getColor(R.color.colorPrimary))
                .titleBgColor(activity.getResources().getColor(R.color.colorPrimary))
                .titleSubmitTextColor(activity.getResources().getColor(R.color.white))
                .titleTextColor(activity.getResources().getColor(R.color.white))
                .mutiSelect()
                .mutiSelectMaxSize(9)
                .showCamera()
                .filePath("/" + activity.getPackageName() + "/")
                .requestCode(1)
                .build();
        ImageSelector.open(activity, imageConfig);   // 开启图片选择器
    }

    /**
     * 打开图库选择照片
     */
    public void openNoSinCamer(Activity activity) {
        ImageConfig imageConfig
                = new ImageConfig.Builder(new GlideLoader())
                .steepToolBarColor(activity.getResources().getColor(R.color.colorPrimary))
                .titleBgColor(activity.getResources().getColor(R.color.colorPrimary))
                .titleSubmitTextColor(activity.getResources().getColor(R.color.white))
                .titleTextColor(activity.getResources().getColor(R.color.white))
                // 开启单选   （默认为多选）
                .singleSelect()
                // 拍照后存放的图片路径（默认 /temp/picture） （会自动创建）
                .filePath("/" + activity.getPackageName() + "/")
                .requestCode(1)
                .build();
        ImageSelector.open(activity, imageConfig);   // 开启图片选择器
    }

    private class GlideLoader implements ImageLoader {
        @Override
        public void displayImage(Context context, String path, ImageView imageView) {
            Glide.with(context)
                    .load(path)
                    .apply(GlideUtils.init().options(com.jaiky.imagespickers.R.drawable.global_img_default))
                    .into(imageView);
        }
    }
}
