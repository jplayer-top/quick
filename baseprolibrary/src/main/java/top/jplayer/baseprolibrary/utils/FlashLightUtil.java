package top.jplayer.baseprolibrary.utils;

import android.app.Activity;
import android.content.Context;
import android.hardware.Camera;
import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraCharacteristics;
import android.hardware.camera2.CameraManager;
import android.os.Build;

import java.lang.ref.WeakReference;

/**
 * Created by Obl on 2018/7/31.
 * top.jplayer.baseprolibrary.utils
 * call me : jplayer_top@163.com
 * github : https://github.com/oblivion0001
 */

public class FlashLightUtil {
    private WeakReference<Activity> mWeakReference;
    private Camera mCamera;

    public FlashLightUtil(Activity activity) {
        mWeakReference = new WeakReference<>(activity);
    }

    private static FlashLightUtil flashLightUtil;

    public static FlashLightUtil init(Activity activity) {
        if (flashLightUtil == null) {
            flashLightUtil = new FlashLightUtil(activity);
        }
        return flashLightUtil;
    }

    public void flashLight(boolean openOrClose) {
        //判断API是否大于24（安卓7.0系统对应的API）
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            try {
                //获取CameraManager
                CameraManager mCameraManager = (CameraManager) mWeakReference.get().getSystemService(Context.CAMERA_SERVICE);
                //获取当前手机所有摄像头设备ID
                if (mCameraManager != null) {
                    String[] ids = mCameraManager.getCameraIdList();
                    for (String id : ids) {
                        CameraCharacteristics c = mCameraManager.getCameraCharacteristics(id);
                        //查询该摄像头组件是否包含闪光灯
                        Boolean flashAvailable = c.get(CameraCharacteristics.FLASH_INFO_AVAILABLE);
                        Integer lensFacing = c.get(CameraCharacteristics.LENS_FACING);
                        if (flashAvailable != null && flashAvailable
                                && lensFacing != null && lensFacing == CameraCharacteristics.LENS_FACING_BACK) {
                            mCameraManager.setTorchMode(id, openOrClose);
                        }
                    }
                }
            } catch (CameraAccessException e) {
                e.printStackTrace();
            }
        } else {
            mCamera = Camera.open();
            Camera.Parameters parameters = mCamera.getParameters();
            parameters.setFlashMode(openOrClose ? Camera.Parameters.FLASH_MODE_TORCH : Camera.Parameters.FLASH_MODE_OFF);//开启
            mCamera.setParameters(parameters);
            mCamera.release();
        }
    }

}
