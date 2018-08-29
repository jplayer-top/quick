package top.jplayer.baseprolibrary.utils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
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

    //颜色混合
    public static int blendColor(int fg, int bg) {
        int scr = Color.red(fg);
        int scg = Color.green(fg);
        int scb = Color.blue(fg);
        int sa = fg >>> 24;
        int dcr = Color.red(bg);
        int dcg = Color.green(bg);
        int dcb = Color.blue(bg);
        int color_r = dcr * (0xff - sa) / 0xff + scr * sa / 0xff;
        int color_g = dcg * (0xff - sa) / 0xff + scg * sa / 0xff;
        int color_b = dcb * (0xff - sa) / 0xff + scb * sa / 0xff;
        return ((color_r << 16) + (color_g << 8) + color_b) | (0xff000000);
    }
}
