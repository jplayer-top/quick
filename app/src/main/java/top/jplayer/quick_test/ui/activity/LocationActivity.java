package top.jplayer.quick_test.ui.activity;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.yanzhenjie.permission.AndPermission;
import com.yanzhenjie.permission.Permission;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import top.jplayer.baseprolibrary.ui.activity.CommonToolBarActivity;
import top.jplayer.baseprolibrary.utils.LogUtil;
import top.jplayer.baseprolibrary.utils.ToastUtils;
import top.jplayer.quick_test.R;

/**
 * Created by Obl on 2018/8/22.
 * top.jplayer.quick_test.ui.activity
 * call me : jplayer_top@163.com
 * github : https://github.com/oblivion0001
 */

public class LocationActivity extends CommonToolBarActivity {
    @BindView(R.id.btn_01)
    Button mBtn01;
    private Unbinder mUnbinder;
    private LocationManager mLocationManager;

    @Override
    public int initAddLayout() {
        return R.layout.activity_location;
    }

    @Override
    public void initAddView(FrameLayout rootView) {
        super.initAddView(rootView);
        mUnbinder = ButterKnife.bind(this);
        mBtn01.setOnClickListener(v -> {
            AndPermission.with(this)
                    .permission(Permission.ACCESS_FINE_LOCATION, Permission.ACCESS_COARSE_LOCATION)
                    .onGranted(permissions -> {
                        mLocationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
                        openGPSSettings(mLocationManager);
                        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                            ToastUtils.init().showQuickToast("无权限");
                            return;
                        }
                        if (mLocationManager != null) {
                            Criteria criteria = new Criteria();
                            criteria.setAccuracy(Criteria.ACCURACY_FINE);// 高精度
                            criteria.setAltitudeRequired(false);
                            criteria.setBearingRequired(false);
                            criteria.setCostAllowed(true);
                            criteria.setPowerRequirement(Criteria.POWER_LOW);// 低功耗
                            String provider = mLocationManager.getBestProvider(criteria, true); // 获取GPS信息
                            Location location = mLocationManager.getLastKnownLocation(provider);
                            if (null == location) {
                                if (mLocationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
                                    location = mLocationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                                } else if (mLocationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)) {
                                    location = mLocationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
                                } else if (mLocationManager.isProviderEnabled(LocationManager.PASSIVE_PROVIDER)) {
                                    location = mLocationManager.getLastKnownLocation(LocationManager.PASSIVE_PROVIDER);
                                }
                            }
                            if (location != null) {
                                showLocation(location);
                            } else {
                                LogUtil.e("获取定位失败!");

                            }
                        }

                    })
                    .onDenied(permissions -> AndPermission.hasAlwaysDeniedPermission(mActivity, permissions))
                    .start();
        });
    }

    private void openGPSSettings(LocationManager manager) {
        if (manager.isProviderEnabled(android.location.LocationManager.GPS_PROVIDER)) {
            return;
        }
        Toast.makeText(this, "请开启GPS！", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
        startActivityForResult(intent, 0); //此为设置完成后返回到获取界面
    }

    LocationListener mLocationListener = new LocationListener() {
        @Override
        public void onLocationChanged(Location location) {
            showLocation(location);
        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {
            LogUtil.e("onStatusChanged" + provider);
        }

        @Override
        public void onProviderEnabled(String provider) {
            LogUtil.e("onProviderEnabled" + provider);
        }

        @Override
        public void onProviderDisabled(String provider) {
            LogUtil.e("onProviderDisabled" + provider);

        }
    };

    private void showLocation(Location location) {
        double latitude = location.getLatitude();
        double longitude = location.getLongitude();
        ToastUtils.init().showQuickToast(latitude + "---" + longitude);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mUnbinder.unbind();
        if (mLocationManager != null) {
            // 关闭程序时将监听器移除
            mLocationManager.removeUpdates(mLocationListener);
        }
    }
}
