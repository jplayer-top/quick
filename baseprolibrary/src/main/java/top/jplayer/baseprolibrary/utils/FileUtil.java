package top.jplayer.baseprolibrary.utils;

import android.os.Environment;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import top.jplayer.baseprolibrary.BaseInitApplication;

/**
 * Created by Obl on 2018/8/7.
 * top.jplayer.baseprolibrary.utils
 * call me : jplayer_top@163.com
 * github : https://github.com/oblivion0001
 */

public class FileUtil {
    public static File saveFile(InputStream is, String fileName) {
        try {
            File file = new File(BaseInitApplication.getContext().getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS), fileName);
            FileOutputStream fos = new FileOutputStream(file);
            BufferedInputStream bis = new BufferedInputStream(is);
            byte[] buffer = new byte[1024];
            int len;
            while ((len = bis.read(buffer)) != -1) {
                fos.write(buffer, 0, len);
                fos.flush();
            }
            fos.close();
            bis.close();
            is.close();
            return file;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
