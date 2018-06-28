package top.jplayer.baseprolibrary.utils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.annotation.DrawableRes;
import android.support.v7.graphics.Palette;
import android.view.View;

import top.jplayer.baseprolibrary.BaseInitApplication;

/**
 * Created by Obl on 2018/5/10.
 * top.jplayer.baseprolibrary.utils
 * call me : jplayer_top@163.com
 * github : https://github.com/oblivion0001
 */

public class PaletteUtil {
    public static void getColor(@DrawableRes int ids, View view) {
        Bitmap bitmap = BitmapFactory.decodeResource(BaseInitApplication.getContext().getResources(), ids);
        getColor(bitmap, view);
    }

    public static void getColor(Bitmap bitmap, View view) {
        // Palette的部分
        Palette.from(bitmap).generate(palette -> {
            //获取到充满活力的这种色调
            Palette.Swatch vibrant = palette.getVibrantSwatch();
            if (vibrant != null) {
                view.setBackgroundColor(vibrant.getRgb());
            }
        });
    }
}
